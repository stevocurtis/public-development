// create the module and name it angularPlaypenDirectivesModule
var angularPlaypenDirectivesModule = angular.module('angularPlaypenDirectivesModule', []);

angularPlaypenDirectivesModule.directive('arrayToTable', function() {
	return 	{
		restrict: 'EA',
		link: link,
		template: '<p>i am a directive with {{someParam}} parameter</p>'
	};

	function link( scope, element, attributes ) {
		
		console.log("arrayToTable link function called");
		
		if (typeof attributes.someparam !== undefined) { 
			scope.someParam = attributes.someparam;
		}

	}

}

);