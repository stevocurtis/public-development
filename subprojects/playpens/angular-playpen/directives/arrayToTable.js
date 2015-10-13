// create the module and name it angularPlaypenDirectivesModule
var angularPlaypenDirectivesModule = angular.module('angularPlaypenDirectivesModule', []);

angularPlaypenDirectivesModule.directive('arrayToTable', function() {
	return 	{
		template: '<p>i am a directive</p>'
	};
}
);