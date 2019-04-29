#!/bin/bash
set -euo pipefail

cd ${0%/*}/coffee-shop

trap cleanup EXIT

function cleanup() {
  rm -Rf /tmp/wad-dropins/*
  [[ ${pid} ]] && kill -15 ${pid} &> /dev/null || true
}

docker build -t coffee-shop .

mkdir -p /tmp/wad-dropins/

telepresence --swap-deployment coffee-shop \
  --docker-run --rm \
  -p 9080:9080 \
  -p 9443:9443 \
  -v /tmp/wad-dropins/:/opt/wlp/usr/servers/defaultServer/dropins/ \
  coffee-shop &

pid=$!

wad.sh
