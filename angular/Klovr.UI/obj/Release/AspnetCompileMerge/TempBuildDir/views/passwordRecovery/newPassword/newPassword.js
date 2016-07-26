'use strict';

angular.module('klovr.ui.passwordRecovery.newPassword', ['ngRoute', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery/newPassword/:email/:code', {
        templateUrl: 'views/passwordRecovery/newPassword/newPassword.html',
        controller: 'NewPasswordController'
    })

}])

.controller('NewPasswordController', ['$scope', '$routeParams', '$location', '$timeout', 'apiClient', 'APIURLS', 'title', 'resource', function ($scope, $routeParams, $location, $timeout, apiClient, APIURLS, title, resource) {

    title.setTitle("Recover your password");


    $scope.email = $routeParams.email;
    $scope.code = $routeParams.code;

    $scope.password = "";
    $scope.passwordConfirmation = "";

    $scope.submit = function () {
        if ($scope.form.$valid && $scope.password == $scope.passwordConfirmation) {
            apiClient.post(APIURLS.passwordRecovery.changePassword, {
                Email: $scope.email,
                Password: $scope.password,
                ValidationCode: $scope.code
            },
            function (response) {
                if (response.data.OperationStatus == 0) {
                    $scope.error = null;
                    $location.url('/passwordRecovery/success/');
                } else {
                    $scope.error = response.data.Message.Text;
                }
            })
        }
    }
    resource.getResourceImage('passwordrecovery.background.image', function (url) {
        $scope.background = $scope.getBackgroundCSS(url);
    });

    $scope.getBackgroundCSS = function (url) {
        return {
            'background-image': 'url(' + url + ')'
        };
    }
}]);
