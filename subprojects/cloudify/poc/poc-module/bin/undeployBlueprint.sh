#!/bin/bash

if [ ! -z "$1" ]; then
    bp="bp-$1"
    dep="dep-$1"
fi

if [ ! -z "$2" ]; then
    dep=$1
fi

echo cfy executions list -d $dep | grep start
exID=`cfy executions list -d $dep | grep start | awk -F\| '{print $2}' | sed 's/\s*//g'`
echo cfy events list -e $exID -l -v
echo cfy executions cancel -e $exID
echo cfy deployments delete -d $dep -f
echo cfy blueprints delete -b $bp