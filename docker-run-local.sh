#!/bin/bash
set -euo pipefail
cd ${0%/*}

docker stop barista coffee-shop grafana prometheus || true

docker run --rm -d \
  --name barista \
  --network dkrnet \
  docker.sebastian-daschner.com/barista:1

docker run --rm -d \
  -p 9080:9080 \
  -p 9443:9443 \
  --name coffee-shop \
  --network dkrnet \
  docker.sebastian-daschner.com/coffee-shop:bulletproof-build-2

docker run --rm -d \
  -p 9090:9090 \
  --name prometheus \
  --network dkrnet \
  -v $(pwd)/coffee-shop/deployment/monitoring/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus:v2.4.0

docker run --rm -d \
  -p 3000:3000 \
  --name grafana \
  --network dkrnet \
  grafana/grafana:5.2.4
