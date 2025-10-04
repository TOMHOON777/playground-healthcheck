#!/bin/bash

set -e;

echo "🥗 Start Build"
./gradlew build x -test

echo "🥗 stop container"
docker stop health-container

echo "🥗 remove container"
docker rm health-container

echo "🥗 remove image"
docker rmi health-image

echo "🥗 make image"
docker build -t health-image .

echo "🥗 make container"
docker run -d -p 7890:8080 --name health-container health-image