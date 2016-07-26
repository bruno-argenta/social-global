'use strict';

angular.module('klovr.ui.passwordRecovery.getAddress', ['ngRoute', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery/address/:email?/:error?', {
        templateUrl: 'views/passwordRecovery/getAddress/getAddress.html',
        controller: 'GetAddressController'
    })

}])

.controller('GetAddressController', ['$scope', '$routeParams', '$location', 'title', 'resource', function ($scope, $routeParams, $location, title, resource) {

    title.setTitle("Recover your password");

    $scope.email = $routeParams.email ? $routeParams.email : "";
    $scope.error = $routeParams.error ? $routeParams.error : "";

    $scope.submit = function () {
        if ($scope.emailForm.$valid) {
            $location.url('/passwordRecovery/method/' + $scope.email);
        }
    };

    resource.getResourceImage('passwordrecovery.background.image', function (url) {
        $scope.background = $scope.getBackgroundCSS(url);
    });

    $scope.getBackgroundCSS = function (url) {
        return {
            'background-image': 'url(' + url + ')'
        };
    }

    $scope.cancelUrl = function () {
        return "#!/";
    }
}]);
