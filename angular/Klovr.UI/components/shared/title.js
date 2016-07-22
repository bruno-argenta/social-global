'use strict';

angular.module('klovr.ui.title', ['ngRoute'])
  .factory('title', function ($location) {
      var title = 'Klovr';
      return {
          title: function () { return title; },
          setTitle: function (newTitle) { title = newTitle; }
      };
  });
