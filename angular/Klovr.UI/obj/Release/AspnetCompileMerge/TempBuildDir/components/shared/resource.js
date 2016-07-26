'use strict';

angular.module('klovr.ui.resource', ['ngRoute', 'klovr.ui.api.urls', 'klovr.ui.api.client'])
  .factory('resource', ['APIURLS', 'apiClient', function (APIURLS, apiClient) {

      return {
          getResourceImage: function (resourceId, callbaclkSuccess, callbackError) {
              apiClient.get(APIURLS.resource.image,
                  { resourceId: resourceId },
                  function (response) {//success
                      if (response.data.OperationStatus == 0) {
                          callbaclkSuccess(response.data.OperationData.ResourceUrl);
                      } else {
                          callbackError(response.data);
                      }
                  },
                  function (response) {//error
                      if (callbackError) {
                          callbackError({ OperationStatus: -1, Message: { Text: "Server error", Level: "Error" }, OperationData: null });
                      } else {
                          console.error(response);
                      }
                  })
          },

          getResourceText: function (resourceId, callbaclkSuccess, callbackError) {
              apiClient.get(APIURLS.resource.text,
                  { resourceId: resourceId },
                  function (response) {//success
                      if (response.data.OperationStatus == 0) {
                          callbaclkSuccess(response.data.OperationData.ResourceText);
                      } else {
                          callbackError(response.data);
                      }
                  },
                  function (response) {//error
                      if (callbackError) {
                          callbackError({ OperationStatus: -1, Message: { Text: "Server error", Level: "Error" }, OperationData: null });
                      } else {
                          console.error(response);
                      }
                  })
          }
      };
  }]);
