'use strict';

angular.module('klovr.ui.passwordRecovery.code', ['ngRoute', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery/code/:email/:contact', {
        templateUrl: 'views/passwordRecovery/code/code.html',
        controller: 'CodeController'
    })

}])

.controller('CodeController', ['$scope', '$routeParams', '$location', '$timeout','apiClient', 'APIURLS', 'title', 'resource', function ($scope, $routeParams, $location, $timeout, apiClient, APIURLS, title, resource) {
    title.setTitle("Recover your password");
    $scope.email = $routeParams.email;
    $scope.title = 'MyKlōver just sent a verification code via text message to ' + $routeParams.contact;
    $scope.code = "";
    $scope.error = "";

    $scope.forgotPasswordMethodUrl = function () {
        return "#!/passwordRecovery/method/" + $scope.email;
    }

    $scope.submit = function () {
        if ($scope.codeForm.$valid) {
            apiClient.post(APIURLS.passwordRecovery.validateCode, {
                ValidationCode: $scope.code
            },
            function (response) {
                if (response.data.OperationStatus == 0) {
                    $scope.error = null;
                    $location.url('/passwordRecovery/newPassword/' + $scope.email + '/' + $scope.code);
                } else {
                    $scope.error = response.data.Message.Text;
                }
            })
        }
    }

    $scope.cancelUrl = function () {
        return "#!/";
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
