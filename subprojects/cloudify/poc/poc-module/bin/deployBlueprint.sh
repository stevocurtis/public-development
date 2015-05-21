#!/bin/bash -e

bin_folder=`dirname $0`
echo "bin_folder set to ${bin_folder}"

blueprints_folder="${bin_folder}/../blueprints"
echo "blueprints_folder set to ${blueprints_folder}"

inputs_folder="${blueprints_folder}/inputs"
echo "inputs_folder set to ${inputs_folder}"

timestamp=`date +"%Y%m%d_%H%M%S_%Z"`

# default blueprint file to simple
bpFile=basic-simple-blueprint
if [ ! -z "$1" ]; then
    bpFile=$1
fi

export bp="bp-${bpFile}-${timestamp}"
export dep="dep-${bpFile}-${timestamp}"

cfy blueprints upload -p ${blueprints_folder}/${bpFile}.yaml -b ${bp}
echo upload upload status is $?

cfy deployments create -d $dep -b $bp -i ${inputs_folder}/${bpFile}.json
echo "input set to ${inputs_folder}/${bpFile}.json"
echo deployments create status is $?