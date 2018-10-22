#!/bin/bash
set -euo pipefail

docker stop barista || true

docker run --rm -d \
  --name barista \
  --network dkrnet \
  docker.sebastian-daschner.com/barista:1
