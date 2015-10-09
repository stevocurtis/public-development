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
	angularPlaypenApp.controller('mainController', function($scope) {
		// create a message to display in our view
		$scope.message = 'Home page ...';
	});

	angularPlaypenApp.controller('aboutController', function($scope) {
		$scope.message = 'About page ...';
	});

	angularPlaypenApp.controller('contactController', function($scope) {
		$scope.message = 'Contact page ...';
	});