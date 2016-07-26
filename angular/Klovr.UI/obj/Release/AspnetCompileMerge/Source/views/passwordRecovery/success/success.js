'use strict';

angular.module('klovr.ui.passwordRecovery.success', ['ngRoute', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/passwordRecovery/success/', {
        templateUrl: 'views/passwordRecovery/success/success.html',
        controller: 'SuccessController'
    })

}])

.controller('SuccessController', ['$scope', '$routeParams', '$location', '$timeout', 'title', 'resource', function ($scope, $routeParams, $location, $timeout, title, resource) {

    $scope.loginUrl = function () {
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
