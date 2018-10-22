#!/bin/bash
set -euo pipefail

fortio load -c 50 -qps 500 -t 30s http://localhost:9080/coffee-shop/resources/orders
