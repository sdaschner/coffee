#!/bin/bash
set -euo pipefail
cd ${0%/*}/coffee-shop

trap cleanup EXIT

function cleanup() {
  echo stopping Docker container...
  docker stop coffee-shop &> /dev/null || true
  rm -Rf /tmp/wad-dropins/*
}

echo building project...
docker build -t coffee-shop:tmp .
docker stop coffee-shop &> /dev/null || true

echo starting container...
docker run --rm -d \
  --name coffee-shop \
  -p 9080:9080 \
  -p 9443:9443 \
  --network dkrnet \
  -v /tmp/wad-dropins/:/opt/wlp/usr/servers/defaultServer/dropins/ \
  coffee-shop:tmp

wad.sh
