// create the module and name it angularPlaypenDirectivesModule
var angularPlaypenDirectivesModule = angular.module('angularPlaypenDirectivesModule', []);

angularPlaypenDirectivesModule.directive('arrayToTable', function() {
//	return 	{
//		restrict: 'EA',
//		scope: '=someParam',
//		template: '<p>i am a directive with {{someParam}} parameter</p>'
//	};
	scope: {'=someParam'},
	link: function (scope, iElm, iAttrs) {
		restrict: 'EA',
		scope: '=someParam',
		template: '<p>i am a directive with {{someParam}} parameter</p>'
	};
}
);