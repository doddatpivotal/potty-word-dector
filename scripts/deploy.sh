#!/usr/bin/env bash

riff service delete potty-word-dectector

riff function create java potty-word-detector \
    --git-repo https://github.com/doddatpivotal/potty-word-detector.git \
    --image $DOCKER_ID/potty-word-detector \
    --verbose

