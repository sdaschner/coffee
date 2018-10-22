#!/bin/bash
set -euo pipefail

docker stop coffee-shop || true

docker run --rm -d \
  -p 9080:9080 \
  -p 9443:9443 \
  --name coffee-shop \
  --network dkrnet \
  docker.sebastian-daschner.com/coffee-shop:bulletproof-build-1
