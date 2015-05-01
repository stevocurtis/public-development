#!/bin/bash

if [ ! -z "$1" ]; then
    dep=$1
fi

echo cfy executions list -d $dep | grep start
exID=`cfy executions list -d $dep | grep start | awk -F\| '{print $2}' | sed 's/\s*//g'`
echo cfy events list -e $exID -l -v
echo cfy executions cancel -e $exID
echo cfy deployments delete -d $dep -f
echo cfy blueprints delete -b $bp