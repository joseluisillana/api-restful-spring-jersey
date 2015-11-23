'use strict';

angular.module('bbva.or.dashboard.pages')

    /**
     * @ngdoc controller
     * @name bbva.or.dashboard.pages.controller:ConfigurationController
     * @description
     * Controller with configuration functionality.
     */
    .controller('ConfigurationController', function($scope, localStorageService){

      var name = 'Configuration';
      var model = localStorageService.get(name);
        
      if (!model){
        model = {
        title: 'Configuration',
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
