'use strict';

angular.module('klovr.ui.wizard', ['ngRoute', 'klovr.ui.authentication'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/wizard', {
        templateUrl: 'views/wizard/wizard.html',
        controller: 'WizardController'
    })
}])

.controller('WizardController', ['$scope', 'authentication', function ($scope, authentication) {
    $scope.show = authentication.redirectToLogInIfNotLogged();
}]);
