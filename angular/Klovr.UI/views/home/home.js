'use strict';

angular.module('klovr.ui.home', ['ngRoute', 'klovr.ui.authentication'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/home/home.html',
        controller: 'HomeController'
    })
}])

.controller('HomeController', ['$scope', 'authentication', function ($scope, authentication) {
    $scope.show = authentication.redirectToLogInIfNotLogged();
}]);
