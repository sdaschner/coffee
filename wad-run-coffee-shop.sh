#!/bin/bash
set -euo pipefail

trap cleanup EXIT

function cleanup() {
  docker stop coffee-shop || true
  rm -Rf /tmp/wad-dropins/*
}


docker build -t coffee-shop:tmp .
docker stop coffee-shop || true

docker run --rm -d \
  --name coffee-shop \
  -p 9080:9080 \
  -p 9443:9443 \
  --network dkrnet \
  -v /tmp/wad-dropins/:/opt/wlp/usr/servers/defaultServer/dropins/ \
  coffee-shop:tmp

wad.sh
