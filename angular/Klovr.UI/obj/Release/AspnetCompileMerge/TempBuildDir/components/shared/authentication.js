'use strict';

angular.module('klovr.ui.authentication', ['ngRoute', 'satellizer', 'LocalStorageModule', 'klovr.ui.api.client', 'klovr.ui.api.urls'])
  .factory('authentication', ['$location', '$auth', 'localStorageService', 'apiClient', 'APIURLS', function ($location, $auth, localStorageService, apiClient, APIURLS) {

      function fatalError(response) {

      }

      return {
          setUser: function (aUser) {
              localStorageService.set("user", aUser);
          },
          getUser: function () {
              return localStorageService.get("user");
          },
          isLoggedIn: function () {
              return (this.getUser()) ? this.getUser() : false;
          },
          redirectToLogInIfNotLogged: function () {
              if (!this.isLoggedIn()) {
                  $location.url('/login');
                  return false;
              }
              return true;
          },
          authenticateWithExternalProvider: function (providerName, callbackSuccess, callbackError) {
              var context = this;
              return $auth.authenticate(providerName).then(
                  function (response) {
                      console.log(response);
                      response.provider = providerName;
                      apiClient.post(APIURLS.authentication.externalLogin, response,
                            function (logInResponse) {
                                if (logInResponse.data.OperationStatus == 0) {
                                    context.setUser(logInResponse.data.OperationData);
                                }
                                if (callbackSuccess) {
                                    callbackSuccess(logInResponse.data);
                                }
                            }
                        );
                  });
          }
      };
  }]);
