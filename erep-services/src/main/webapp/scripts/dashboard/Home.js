'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.dashboard
 * @description
 * # bbva.or.dashboard
 * Module with main dashboard by group functionality.
 */
angular.module('bbva.or.dashboard', ['bbva.or.dashboard', 'LocalStorageModule'])

  /**
   * @ngdoc controller
   * @name bbva.or.dashboard.DashboardController.controller:FiltroMaestroController
   * @description
   * Controller with dashboard by group functionality.
   */
    .controller('DashboardController', function($rootScope, $scope, localStorageService, dashboardConfigService){

      var name = 'Dashboard';

      var model = dashboardConfigService.getApplicationDashboardConfig();

      $scope.name = name;
      $scope.model = model;
      $scope.collapsible = true;
});
