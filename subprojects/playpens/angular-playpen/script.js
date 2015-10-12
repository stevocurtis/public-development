	// create the module and name it angularPlaypenApp
	var angularPlaypenApp = angular.module('angularPlaypenApp', ['ngRoute']);
	
	// configure our routes
	angularPlaypenApp.config(function($routeProvider) {

		$routeProvider
		
			// route for the home page
			.when('/', {
				templateUrl : 'partials/home.html',
				controller  : 'mainController'
			})

			// route for the about page
			.when('/about', {
				templateUrl : 'partials/about.html',
				controller  : 'aboutController'
			})

			// route for the contact page
			.when('/contact', {
				templateUrl : 'partials/contact.html',
				controller  : 'contactController'
			})
			;
	});

	// create the controller and inject Angular's $scope
	angularPlaypenApp.controller('mainController', function($scope, TemplateContentService) {
		$scope.message = TemplateContentService.content("Home");
	});

	angularPlaypenApp.controller('aboutController', function($scope, TemplateContentService) {
		$scope.message = TemplateContentService.content("About");

	});

	angularPlaypenApp.controller('contactController', function($scope, TemplateContentService) {
		$scope.message = TemplateContentService.content("Contact");
	});
	
	// Create service
	angularPlaypenApp.factory("TemplateContentService", function() {
	
		var data = {Home:    "Home page ...",
					About:   "About page ...",
					Contact: "Contact page ..."};
		
		return {
			content: function(key) {
				return data[key];
			}
		};
	});