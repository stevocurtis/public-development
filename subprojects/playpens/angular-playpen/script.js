	// create the module and name it angularPlaypenApp
	var angularPlaypenApp = angular.module('angularPlaypenApp', ['ngRoute']);
	
	// configure our routes
	angularPlaypenApp.config(function($routeProvider) {

		$routeProvider
		
			// route for the home page
			.when('/', {
				templateUrl : 'pages/home.html',
				controller  : 'mainController'
			})

			// route for the about page
			.when('/about', {
				templateUrl : 'pages/about.html',
				controller  : 'aboutController'
			})

			// route for the contact page
			.when('/contact', {
				templateUrl : 'pages/contact.html',
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
		$scope.theRoute = "bollox";
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