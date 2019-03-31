#!/bin/bash
cd ${0%/*}
set -euo pipefail

kontemplate template -i deployment .kontemplate.yaml > deployment.yaml
kubectl apply -f deployment.yaml

kontemplate template -i routing .kontemplate.yaml > routing.yaml
kubectl apply -f routing.yaml

kubectl apply -f gateway.yaml
kubectl apply -f service.yaml
