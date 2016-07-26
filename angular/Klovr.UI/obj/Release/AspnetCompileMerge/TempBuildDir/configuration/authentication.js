'use strict';

angular.module('klovr.ui.configuration.authentication', [])
    .constant('authenticationConfiguration', {
        providers: {
            google: {
                clientId: '441960673979-n9ll57lsrj80lh9fpfj38885267a41ne.apps.googleusercontent.com'
            },
            twitter: {
                //no info needed
            },
            facebook: {
                clientId: 'Facebook App ID'
            }
        }

    });