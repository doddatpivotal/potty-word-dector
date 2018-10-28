# Potty Word detector
This sample requires riff v0.1.3 or later.

>To push to GCR set $DOCKER_ID to gcr.io/<project_id>

#### create locally
```sh
riff function create java potty-word-detector \
  --local-path . \
  --image $DOCKER_ID/potty-word-detector \
  --verbose \
  --wait
```

#### create from git repo, pushing image to DockerHub
```sh
riff function create java potty-word-detector \
    --git-repo https://github.com/doddatpivotal/potty-word-detector.git \
    --image $DOCKER_ID/potty-word-detector \
    --verbose
```
To set `$DOCKER_ID` do `export DOCKER_ID=your-docker-id`

#### invoke
```
riff service invoke potty-word-detector --json -- -d '{"eventBody": { "message": "This is a potty word"} }'
```
