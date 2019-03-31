#!/bin/bash
set -euo pipefail

fortio load -c 500 -qps 200 -n 1000 -loglevel Warning http://localhost:9080/coffee-shop/resources/orders
