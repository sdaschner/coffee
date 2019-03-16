#!/bin/bash
set -euo pipefail
cd ${0%/*}

pushd coffee-shop/
  mvn clean package
  docker build -t sdaschner/coffee-shop:bulletproof-ee-1 .
popd

./docker-run-coffee-shop.sh
