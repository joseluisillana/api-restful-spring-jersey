'use strict';

angular.module('bbva.or.dashboard.pages')

  /**
   * @ngdoc controller
   * @name bbva.or.dashboard.pages.controller:ProcesosAltaPorTipoOperacionController
   * @description
   * Controller for Module Page: "Altas de Procesos por Tipo de Operacion"
   */
    .controller('ProcesosAltaPorTipoOperacionController', function($rootScope, $scope, localStorageService, dashboardConfigService, logService){

      // Dashboard Page Config
      $scope.dashboardPageName = 'ProcesosAltaPorTipoOperacion';
      $scope.collapsible = false;

      // Default Page Model
      $scope.defaultModel = {
        title: 'Informe de Procesos de Alta por Tipo de Operación - Default',
        structure: '6-6',
        rows: [{
          columns: [{
            styleClass: 'col-md-12',
            widgets: [{
              type: 'ProcesosAltaPorTipoOperacionNVD3Widget',
              config:{titleIcoClass:"fa-bar-chart", hasFilter:true,isFilterVisible:true}
            }]
          }]
        }]
      };

      //CARGA DESDE CONFIGURACIÓN LOCAL
      $scope.model = dashboardConfigService.getUserDashboardConfig($scope.dashboardPageName);

      /* CARGA DESDE SERVIDOR
        dashboardConfigService.getDashboardPageConfig($rootScope.user.id, $scope.name).then(
          //Sucess
          function(data){
            debugger;
            logService.info("Configuración de Máquina", data);
            $scope.model = model;
          },
          //Error
          function(errorData){
            debugger;
            logService.error("No se ha podido cargar la configuración de la pagina", errorData);
            $scope.model = $scope.defaultModel;
          }
      );*/

      if($scope.model == null){
        $scope.model = $scope.defaultModel;
      }

      $scope.$on('adfDashboardChanged', function (event, name, model){
          dashboardConfigService.updateDashboardConfig($scope.dashboardPageName, model);
      });

    });
