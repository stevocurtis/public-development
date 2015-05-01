#!/bin/bash

export bp="bp-$1"
export dep="dep-$1"

cfy blueprints upload -p poc-module/blueprints/$1.yaml -b ${bp}
echo upload upload status is $?

cfy deployments create -d $dep -b $bp -i poc-module/blueprints/inputs/$1.json
echo deployments create status is $?