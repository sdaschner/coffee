#!/bin/bash
set -euo pipefail

cd ${0%/*}/coffee-shop

trap cleanup EXIT

function cleanup() {
  docker stop coffee-shop || true
  rm -Rf /tmp/wad-dropins/*
}

docker build -t coffee-shop .
docker stop coffee-shop || true

mkdir -p /tmp/wad-dropins/

docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -p 9080:9080 \
  -p 9443:9443 \
  -v /tmp/wad-dropins/:/opt/wlp/usr/servers/defaultServer/dropins/ \
  coffee-shop

wad.sh
