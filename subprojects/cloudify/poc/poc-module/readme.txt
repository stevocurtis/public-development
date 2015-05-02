Run the following from the parent of poc-module:

1. To deploy blueprint and deployment (note the parameter is optional, if empty assumes basic-simple-blueprint)
poc-module/bin/deployBlueprint.sh my-blueprint

2. To undeploy blueprint and deployment (note the parameter is optional, assumes the $bp and $dp variables may be reused from previous deploy, if passed remove the preceding bp- string)
poc-module/bin/undeployBlueprint.sh