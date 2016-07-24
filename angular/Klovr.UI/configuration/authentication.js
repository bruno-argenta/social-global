'use strict';

angular.module('klovr.ui.configuration.authentication', [])
    .constant('authenticationConfiguration', {
        providers: {
            google: {
                clientId: '288288375615-hse0tk8cq6oqbnmn7o6f0isekjsk2475.apps.googleusercontent.com'
            },
            twitter: {
                clientId: 'KXImyzHQevZYc97BHqkzEP8hY'//no info needed
            },
            facebook: {
                clientId: '1780604565560032'
            }
        }

    });