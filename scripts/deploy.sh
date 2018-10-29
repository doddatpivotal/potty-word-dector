#!/usr/bin/env bash

riff service delete potty-word-dectector

localpath=${1:-.}

riff function create java potty-word-detector \
    --local-path $localpath \
    --image $DOCKER_ID/potty-word-detector \
    --verbose

