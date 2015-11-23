'use strict';

angular.module('bbva.or.dashboard.pages')

    /**
     * @ngdoc controller
     * @name bbva.or.dashboard.pages.controller:SecurityController
     * @description
     * Controller for Module Page: "Security"
     */
    .controller('SecurityController', function($scope, localStorageService){

      var name = 'Security';
      var model = localStorageService.get(name);

      if (!model){
        model = {
        title: 'Security',
        structure: '6-6',
        configurable: false,
        rows: [{
          columns: [{
            styleClass: 'col-md-12',
            widgets: [
              /*{
                type: 'Widget',
                config: {//bodyClass: 'panel-body-chart',}
              }*/
            ]
          }]
        }]
      };
    }

    $scope.name = name;
    $scope.model = model;
    $scope.collapsible = false;

    $scope.$on('adfDashboardChanged', function (event, name, model) {
      localStorageService.set(name, model);
    });
});
