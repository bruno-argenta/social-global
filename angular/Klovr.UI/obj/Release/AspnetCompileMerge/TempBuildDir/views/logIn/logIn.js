'use strict';

angular.module('klovr.ui.logIn', ['ngRoute', 'klovr.ui.authentication', 'klovr.ui.api.client', 'klovr.ui.api.urls', 'klovr.ui.resource', 'klovr.ui.title'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'views/logIn/logIn.html',
        controller: 'LogInController'
    })
}])

.controller('LogInController', ['$scope', '$timeout', 'authentication', '$location', 'apiClient', 'APIURLS', 'resource', 'title',
function ($scope, $timeout, authentication, $location, apiClient, APIURLS, resource, title) {

        title.setTitle("Login");


        $scope.showLogInForm = false;
        $scope.logInError = null;
        $scope.showRegisterForm = false;
        $scope.registerError = null;
        $scope.user = {};
        $scope.user.email = "";
        $scope.user.password = "";
        $scope.user.passwordConfirmation = "";
        $scope.user.rememberMe = false;

        $scope.scrollerElements = [];

        function setScrollerResources() {
            for (var i = 0; i < 4; i++) {
                setScrollerSectionResources(i);
                
            }
        }

        function setScrollerSectionResources(i){
            $scope.scrollerElements[i] = {};
            resource.getResourceImage('login.scroller' + (i + 1) + '.image', function (url) {
                $scope.scrollerElements[i].background = $scope.getBackgroundCSS(url);
            });

            resource.getResourceText('login.scroller' + (i + 1) + '.text', function (text) {
                $scope.scrollerElements[i].text = text;
            });
        }

        function destroyScroller() {
            if (typeof $.fn.fullpage.destroy == 'function') {
                $.fn.fullpage.destroy('all');
            }
        }

        $scope.getBackgroundCSS = function (url) {
            return {
                'background-image': 'url(' + url + ')'
            };
        }


        $scope.forgotPasswordUrl = function () {
            return "#!/passwordRecovery/address/" + ($scope.user.email ? $scope.user.email : "");
        }

        $scope.authenticate = function (providerName) {

            authentication.authenticateWithExternalProvider(providerName, function (result) {
                console.log(result);
                $location.url('/wizard');

            });
        }

        $scope.submitLogIn = function () {
            if ($scope.loginForm.$valid) {
                apiClient.post(APIURLS.authentication.login,
                    {
                        Email: $scope.user.email,
                        Password: $scope.user.password,
                        RememberMe: $scope.user.rememberMe
                    }, function (response) {
                        if (response.data.OperationStatus == 0) {
                            authentication.setUser(response.data.OperationData);
                            $scope.logInError = null;
                            $location.url('/');
                        } else {
                            $scope.logInError = response.data.Message.Text;
                        }
                    });
            }
        }


        $scope.submitRegister = function () {
            window.location = "/demo/TypeUser.html";
            if ($scope.registerForm.$valid && $scope.user.password == $scope.user.passwordConfirmation) {
                apiClient.post(APIURLS.authentication.registration, {
                    Email: $scope.user.email,
                    Password: $scope.user.password,
                    ConfirmPassword: $scope.user.passwordConfirmation
                },
                function (response) {
                    if (response.data.OperationStatus == 0) {
                        authentication.setUser(response.data.OperationData);
                        $scope.registerError = null;
                        $location.url('/wizard');
                    } else {
                        $scope.registerError = response.data.Message.Text;
                    }
                })
            }
        }

        setScrollerResources();

        $scope.$on("$destroy", function () {
            destroyScroller();
        });

        //inside timeout so angular evaluation is finish (it was having problems with the background images)
        $timeout(function () {
            $(function () {
                destroyScroller();
                var slideInterval = null;
                $('#site-img').fullpage({
                    'navigation': true,
                    'slidesNavigation': false,
                    'navigationPosition': 'left',
                    'loopTop': true,
                    'loopBottom': true,
                    'css3': true,
                    'fixedElements': '#header, #btn',
                    'normalScrollElements': '#login-content',
                    'afterRender': function () {
                        slideInterval = setInterval(function () {
                            $.fn.fullpage.moveSectionDown();
                        }, 4000);
                    }
                });

                //this is needed to remove the href used by single plage, wich colide with angular navigation
                $('#fp-nav a').each(function (i, e) {
                    $(e).click(function (ev) {
                        $.fn.fullpage.moveTo(i + 1);
                    });
                })
                $('#fp-nav').find('a').removeAttr('href');


                $('#site-img').mouseover(function () {
                    clearInterval(slideInterval);
                });
                $('#site-img').mouseleave(function () {
                    slideInterval = setInterval(function () {
                        $.fn.fullpage.moveSectionDown();
                    }, 4000);
                });
            });
        })
    }]);
