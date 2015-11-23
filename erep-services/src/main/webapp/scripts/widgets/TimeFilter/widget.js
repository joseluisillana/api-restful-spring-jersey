//'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.widgets.TimeFilter
 * @description
 * Time filter for all widgets of a dashboard.
 */
angular.module('bbva.or.widgets.TimeFilter', ['bbva.or.db.provider'])

    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('TimeFilterWidget', {
                title: 'Filtro de Tiempo',
                description: 'Muestra un filtro de tiempo.',
                previewImg : 'images/widgets/wtm.png',
                controller: 'TimeFilterController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/TimeFilter/widget.html',
                config: {
                    titleIcoClass: 'fa-clock-o',
                    hasFilter: false
                }
            });
    }]).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.TimeFilter.controller:TimeFilterController
     * @description
     * Controller of bbva.or.widgets.TimeFilter widget.
     */
    controller('TimeFilterController', function($rootScope, $scope){

        $scope.timeFilterOptions={};

    });
