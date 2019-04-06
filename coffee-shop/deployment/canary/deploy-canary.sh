#!/bin/bash
cd ${0%/*}

if [[ "$1" == "" || "$2" == "" || "$3" == "" ]]; then
  echo "Usage: ${0##*/} <old-version> <new-version> <new-image>"; exit 2
fi
oldVersion=$1
newVersion=$2
newImage=$3

set -euo pipefail

function render-deployment() {
  # renders subset $1 and image $2 to deployment YAML file $3
  kontemplate template --var newVersion=$1 --var image=$2 -i deployment .kontemplate.yaml 1> $3 2> /dev/null
}

function route-percentage() {
  # routes $1 % of traffic to new version, $2 % of traffic to old version
  echo routing: new version: ${1}%, old version: ${2}%

  render-routing ${newVersion},${oldVersion} ${newVersion}:$1,${oldVersion}:$2 routing.yaml
  rollout routing.yaml

  echo waiting for 5 seconds
  sleep 5
}

function route-full() {
  render-routing ${newVersion},${oldVersion} ${newVersion}:100 routing.yaml
  rollout routing.yaml
  sleep 2

  render-routing ${newVersion} ${newVersion}:100 routing.yaml
  rollout routing.yaml

  echo deployment done, waiting for 5 seconds
  sleep 5
}

function render-routing() {
  # renders virtual service routing, defines subsets $1, and destination distribution $1 to routing YAML file $3
  kontemplate template --var subsets=$1 --var destinations=$2 -i routing .kontemplate.yaml 1> $3 2> /dev/null
}

function rollout() {
  kubectl apply -f $1 1> /dev/null
}

function rollout-deployment() {
  name=$(kubectl apply -f $1 -o name)
  kubectl rollout status $name
}

function remove() {
  kubectl delete -f $1
}


rollout gateway.yaml

render-deployment $newVersion $newImage deployment-new.yaml
rollout-deployment deployment-new.yaml


route-percentage 5 95

route-percentage 10 90

route-percentage 25 75

route-percentage 50 50

route-percentage 90 10

route-percentage 100 0

route-full

render-deployment $oldVersion 'ignored' deployment-old.yaml
remove deployment-old.yaml
