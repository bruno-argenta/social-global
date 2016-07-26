'use strict';

angular.module('klovr.ui.passwordRecovery.method', ['ngRoute', 'klovr.ui.api.client', 'klovr.ui.api.urls', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery/method/:email?', {
        templateUrl: 'views/passwordRecovery/method/method.html',
        controller: 'MethodController'
    })

}])

.controller('MethodController', ['$scope', '$routeParams', '$location', '$timeout', 'apiClient', 'APIURLS', 'title', 'resource', function ($scope, $routeParams, $location, $timeout, apiClient, APIURLS, title, resource) {

    title.setTitle("Recover your password");

    $scope.email = $routeParams.email;
    $scope.recoveryMethods = [];
    $scope.selectedMethod = null;
    $scope.error = "";

    $scope.cancelUrl = function () {
        return "#!/";
    }

    $scope.submit = function () {
        if ($scope.methodsForm.$valid) {
            apiClient.post(APIURLS.passwordRecovery.requestRecovery, 
                { Email: $scope.email, Mode: $scope.selectedMethod.method },
                function (response) {
                    if (response.data.OperationStatus == 0) {
                        if ($scope.selectedMethod.method == "EMAIL") {
                            $location.url('/passwordRecovery/emailSent/' + $scope.selectedMethod.contact);
                        } else {
                            $location.url('/passwordRecovery/code/' + $scope.email + "/" + $scope.selectedMethod.contact);
                        }
                    } else {
                        $scope.error = response.data.Message.Text;
                    }
                })
            //$location.url('/passwordRecovery/method/' + $scope.email);
        }
    };


    $timeout(function () {
        apiClient.get(APIURLS.passwordRecovery.methods, { Email: $scope.email }, function (response) {
            if (response.data.OperationStatus == 0) {
                var methods = response.data.OperationData;
                for (var i = 0; i < methods.length; i++) {
                    var text = methods[i].Contact;
                    if (methods[i].Method=="EMAIL") {
                        text = "Confirm access to my recovery email " + text;
                    }
                    if (methods[i].Method == "PHONE_CODE") {
                        text = "Get a verification code on my phone " + text;
                    }
                    $scope.recoveryMethods.push({
                        text: text,
                        method: methods[i].Method,
                        contact: methods[i].Contact
                    });
                }
            } else {
                $location.url('/passwordRecovery/address/' + $scope.email + '/' + response.data.Message.Text);
            }
        })
    })


    resource.getResourceImage('passwordrecovery.background.image', function (url) {
        $scope.background = $scope.getBackgroundCSS(url);
    });

    $scope.getBackgroundCSS = function (url) {
        return {
            'background-image': 'url(' + url + ')'
        };
    }

}]);
