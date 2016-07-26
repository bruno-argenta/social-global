'use strict';

angular.module('klovr.ui.api.urls', [])
    .constant('APIURLS', {
        apiBaseUrl: 'http://localhost:3000/api',//without an / at the end
        authentication: {
            login: '/user/LoginUser',
            externalLogin: '/user/LoginUserExternalProvider',
            registration: '/user/RegisterUser',
        },
        passwordRecovery:{
            methods: '/user/RecoveryMethods',
            requestRecovery: '/user/RecoveryPassword',
            changePassword: '/user/ChangePasswordRecovery',
            validateCode: '/user/ValidateCode ',

        },
        resource: {
            image: '/resource/GetResource',
            text: '/resource/GetResourceText'
        }

    });