#!/bin/bash

export bp="bp-$1"
export dep="dep-$1"

cfy blueprints upload -p poc-module/blueprints/$1.yaml -b ${bp}
echo upload upload status is $?

cfy deployments create -d $dep -b $bp -i poc-module-new/blueprints/inputs/$1.json
echo deployments create status is $?

echo cfy executions list -d $dep | grep start
exID=`cfy executions list -d $dep | grep start | awk -F\| '{print $2}' | sed 's/\s*//g'`
echo cfy events list -e $exID -l -v
echo cfy executions cancel -e $exID
echo cfy deployments delete -d $dep -f
echo cfy blueprints delete -b $bp