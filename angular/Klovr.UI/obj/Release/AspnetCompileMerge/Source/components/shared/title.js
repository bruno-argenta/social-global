'use strict';

angular.module('klovr.ui.title', ['ngRoute'])
  .factory('title', function ($location) {
      var baseTitle = ' - MyKlōver';
      var title = 'Klovr';
      return {
          title: function () { return title + baseTitle; },
          setTitle: function (newTitle) { title = newTitle; }
      };
  });
