#!/bin/bash
set -euo pipefail

docker stop coffee-shop || true

docker run --rm -d \
  -p 9080:9080 \
  -p 9443:9443 \
  --name coffee-shop \
  --network dkrnet \
  sdaschner/coffee-shop:bulletproof-ee-2
