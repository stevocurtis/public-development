#!/bin/bash

timestamp=`date +"%Y%m%d_%H%M%S_%Z"`

# default blueprint file to simple
bpFile=basic-simple-blueprint
if [ ! -z "$1" ]; then
    bpFile=$1
fi

export bp="bp-${bpFile}-${timestamp}"
export dep="dep-${bpFile}-${timestamp}"

cfy blueprints upload -p poc-module/blueprints/${bpFile}.yaml -b ${bp}
echo upload upload status is $?

cfy deployments create -d $dep -b $bp -i poc-module/blueprints/inputs/${bpFile}.json
echo deployments create status is $?