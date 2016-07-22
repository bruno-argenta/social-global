'use strict';

angular.module('klovr.ui.api.urls', [])
    .constant('APIURLS', {
        apiBaseUrl: 'http://localhost:3000',//without an / at the end
        authentication: {
            login: '/api/user/LoginUser',
            externalLogin: '/api/user/LoginUserExternalProvider',
            registration: '/api/user/RegisterUser',
        },
        resource: {
            image: '/api/resource/GetResource',
            text: '/api/resource/GetResourceText'
        }

    });