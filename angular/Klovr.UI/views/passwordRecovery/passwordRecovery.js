'use strict';

angular.module('klovr.ui.passwordRecovery', ['ngRoute', 'klovr.ui.authentication'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery', {
        templateUrl: 'views/passwordRecovery/passwordRecovery.html',
        controller: 'PasswordRecoveryController'
    })
}])

.controller('PasswordRecoveryController', ['$scope', '$routeParams', 'authentication', function ($scope, $routeParams, authentication) {
    $scope.email 
}]);
