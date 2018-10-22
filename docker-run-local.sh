#!/bin/bash
set -euo pipefail
cd ${0%/*}

./docker-run-coffee-shop.sh
./docker-run-barista.sh
./docker-run-monitoring.sh
