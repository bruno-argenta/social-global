'use strict';

// Declare app level module which depends on views, and components
angular.module('klovr.ui', [
  'ngRoute',
  'ngMessages',
  'klovr.ui.api.client',
  'klovr.ui.api.urls',
  'klovr.ui.validations',
  'klovr.ui.home',
  'klovr.ui.logIn',
  'klovr.ui.wizard',
  'klovr.ui.resource',
  'klovr.ui.passwordRecovery.getAddress',
  'klovr.ui.passwordRecovery.method',
  'klovr.ui.passwordRecovery.emailSent',
  'klovr.ui.passwordRecovery.code',
  'klovr.ui.passwordRecovery.newPassword',
  'klovr.ui.passwordRecovery.success',
  'klovr.ui.configuration.authentication',
  'satellizer',
  'klovr.ui.authentication',
  'klovr.ui.title'
]).
config(['$locationProvider', '$routeProvider', '$authProvider', 'authenticationConfiguration', '$httpProvider', function ($locationProvider, $routeProvider, $authProvider, authenticationConfiguration, $httpProvider) {


    $locationProvider.hashPrefix('!');
    $routeProvider.otherwise({ redirectTo: '/' });

    $authProvider.google({
        clientId: authenticationConfiguration.providers.google.clientId,
        url: null
    });



    $authProvider.facebook({
        clientId: authenticationConfiguration.providers.facebook.clientId,
        url: null
    });

    $authProvider.twitter({
        //client id not needed
        url: null
    });
}])

.controller('MainController', ['$scope', 'title', function ($scope, titleProvider) {
    $scope.title = titleProvider;
}]);
