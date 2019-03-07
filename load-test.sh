#!/bin/bash
set -euo pipefail

fortio load -c 250 -qps 100 -n 500 -loglevel Warning http://localhost:9080/coffee-shop/resources/orders
