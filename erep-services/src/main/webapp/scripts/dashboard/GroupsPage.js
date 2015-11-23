'use strict';

angular.module('bbva.or.dashboard.pages')

    /**
     * @ngdoc controller
     * @name bbva.or.dashboard.pages.controller:GroupsController
     * @description
     * Controller the user groups functionality.
     */
    .controller('GroupsController', function($scope, localStorageService){

      var name = 'Groups';
      var model = localStorageService.get(name);

      if (!model){
        model = {
        title: 'Grupos',
        structure: '6-6',
        configurable: false,
        rows: [{
          columns: [{
            styleClass: 'col-md-12',
            widgets: [
              /*{
                type: 'ProcesosAltaPorTipoOperacionNVD3Widget',
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
