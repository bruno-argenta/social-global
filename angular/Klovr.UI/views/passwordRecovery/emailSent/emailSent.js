'use strict';

angular.module('klovr.ui.passwordRecovery.emailSent', ['ngRoute', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery/emailSent/:email', {
        templateUrl: 'views/passwordRecovery/emailSent/emailSent.html',
        controller: 'EmailSentController'
    })

}])

.controller('EmailSentController', ['$scope', '$routeParams', '$location', '$timeout', 'title', 'resource', function ($scope, $routeParams, $location, $timeout, title, resource) {

    title.setTitle("Recover your password");


    $scope.title = 'MyKlōver just sent an email to ' + $routeParams.email;

    resource.getResourceImage('passwordrecovery.background.image', function (url) {
        $scope.background = $scope.getBackgroundCSS(url);
    });

    $scope.getBackgroundCSS = function (url) {
        return {
            'background-image': 'url(' + url + ')'
        };
    }
}]);
