#!/bin/bash

set -e;

echo "🥗 source pulling..."
git pull

echo "🥗 Start Build"
./gradlew build -x test | true

echo "🥗 stop container"
docker stop health-container | true

echo "🥗 remove container"
docker rm health-container | true

echo "🥗 remove image"
docker rmi health-image | true

echo "🥗 make image"
docker build -t health-image . | true

echo "🥗 make container"
docker run -d -p 7890:8080 --name health-container health-image | true