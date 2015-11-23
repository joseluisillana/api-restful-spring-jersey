'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.userDashboard
 * @description
 * Module for the Users Dashboards Pages.
 */
angular.module('bbva.or.userDashboard', ['bbva.or.dashboard', 'LocalStorageModule'])

    /**
     * @ngdoc controller
     * @name bbva.or.userDashboard.controller:UserDashboardController
     * @description
     * Controller for Users Dashboards Module Page.
     */
    .controller('UserDashboardController', function($rootScope, $scope, $routeParams, $location, localStorageService, dashboardConfigService){
        debugger;
      $scope.dashboardUID = $routeParams.dashboardUID;
      $scope.collapsible = false;

      //CARGA DESDE CONFIGURACIÓN LOCAL
      $scope.model = dashboardConfigService.getUserDashboardConfig($scope.dashboardUID);
      $scope.dashboardPageName = $scope.model.title;

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

       if(!$scope.model){
            $location.path('/dashboard');
        }


      /*
      if($scope.model == null){
        $scope.model = $scope.defaultModel;

        angular.forEach($rootScope.userPages, function(userPage, key){
          if(userPage.title === $scope.dashboardPageName){
            $scope.model = userPage;
          }
        });

        if(!$scope.model){
            $location.path('/dashboard');
            location.reload();
        }
      }*/

      $scope.$on('adfDashboardChanged', function (event, name, model){
        dashboardConfigService.updateDashboardConfig($scope.dashboardPageName, model);
      });

});
