package io.pivotal.pottyworddetector;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@SpringBootApplication
@Log
public class PottyWordDetectorApplication implements Function<HashMap<String, Object>, HashMap<String, Object>> {

	public static void main(String[] args) {
		SpringApplication.run(PottyWordDetectorApplication.class, args);
	}

	@Value("${io.pivotal.pottyworddetector.searchPath:$.eventBody.message}")
	private String searchPath;

	@Value("${io.pivotal.pottyworddetector.potty-words:potty}")
	private String pottywords;

	@Override
	public HashMap<String, Object> apply(HashMap<String, Object> jsonMessage) {

		Gson gson = new Gson();
		String json = gson.toJson(jsonMessage);

		log.info("Received the following: " + json);

		String textToEvaluate = JsonPath.read(json, searchPath);

		log.info("Evaluating for potty word: " + textToEvaluate);

		AtomicBoolean pottyWordFound = new AtomicBoolean(false);

		log.info("Searching for any of following potty words: " + pottywords);
		Arrays.stream(pottywords.split(",")).forEach(pottyWord -> {
			if(textToEvaluate.contains(pottywords)){
				log.info(pottyWord + " was found");
				pottyWordFound.set(true);
			}
		});
		if(pottyWordFound.get()) {
			log.info("Potty word(s) found");

			JsonElement je = gson.fromJson(json, JsonElement.class);
			JsonObject jo = je.getAsJsonObject();
			jo.addProperty("hasPottyWord", true);
			Type stringStringMap = new TypeToken<HashMap<String, Object>>(){}.getType();
			HashMap<String,Object> map = gson.fromJson(json, stringStringMap);
			return map;
		} else {
			log.info("All clean, no potty word(s) found");
		}

		return jsonMessage;

	}

}
