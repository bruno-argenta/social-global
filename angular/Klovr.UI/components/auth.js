'use strict';

angular.module('klovr.ui.auth', ['ngRoute'])
  .factory('auth', function($location) {
    var user;
    return {
      setUser : function(aUser){
        user = aUser;
      },
      isLoggedIn : function(){
        return(user)? user : false;
      },
      redirectToLogInIfNotLogged: function(){
        if (!this.isLoggedIn()){
          $location.url('/login');
          return false;
        }
        return true;
      }
    };
  });
