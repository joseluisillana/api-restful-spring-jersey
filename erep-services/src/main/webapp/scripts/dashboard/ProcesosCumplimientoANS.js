'use strict';

angular.module('bbva.or.dashboard.pages')

  /**
   * @ngdoc controller
   * @name bbva.or.dashboard.pages.controller:ProcesosCumplimientoANSController
   * @description
   * Controller for Module Page: "Informe ANS"
   */
    .controller('ProcesosCumplimientoANSController', function($rootScope, $scope, localStorageService, dashboardConfigService){

      $scope.name = 'ProcesosCumplimientoANS';
      $scope.collapsible = false;

      // Default Page Model
      $scope.defaultModel = {
        title: 'Informe de Cumplimiento ANS de Procesos-Default',
        structure: '6-6',
        rows: [{
          columns: [{
            styleClass: 'col-md-12',
            widgets: [{
              type: 'InformeANSWidget',
              config:{titleIcoClass:"fa-bar-chart", hasFilter:true,isFilterVisible:true}
            }]
          }]
        }]
      };

      //CARGA DESDE CONFIGURACIÓN LOCAL
      $scope.model = dashboardConfigService.getUserDashboardConfig($scope.name);

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

      if($scope.model==null){
        $scope.model = $scope.defaultModel;
      }

      $scope.$on('adfDashboardChanged', function (event, name, model) {
        dashboardConfigService.updateDashboardConfig(name, model);
      });

    });
