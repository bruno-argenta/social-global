'use strict';

angular.module('klovr.ui.api.client', ['klovr.ui.api.urls'])
  .factory('apiClient', ['$http', 'APIURLS', function ($http, APIURLS) {

      return {
          get: function (url, parameters, successCallback, errorCallback) {
              $http.get(APIURLS.apiBaseUrl + url, { params: parameters })
                .then(function (response) {
                    if (successCallback) {
                        successCallback(response);
                    }
                },
                function (response) {
                    if (errorCallback) {
                        errorCallback(response);
                    }
                });
          },
          post: function (url, data, successCallback, errorCallback) {
              $http({
                  method: 'POST',
                  data: data,
                  url: APIURLS.apiBaseUrl + url,
                  headers: {
                      'Content-Type': "application/json"
                  },
              })
                .then(function (response) {
                    if (successCallback) {
                        successCallback(response);
                    }
                },
                function (response) {
                    if (errorCallback) {
                        errorCallback(response);
                    }
                });
          }
      };
  }]);
