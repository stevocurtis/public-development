#!/bin/bash

cd ~/cloudify/blueprints
poc_blueprint_folder="/vagrant_data/poc-module/blueprints"
echo "Uploadng the blueprint and create a deployment"
cfy blueprints upload -b basicsinglenode -p ${poc_blueprint_folder}/basic-single-node-blueprint.yaml

#cfy deployments create -b basicsinglenode -d basicsinglenode --inputs ${poc_blueprint_folder}/inputs/basic-single-node-blueprint.json
cfy deployments create -b basicsinglenode -d basicsinglenode

echo "Installing the deployment"
cfy executions start -w install -d basicsinglenode -f