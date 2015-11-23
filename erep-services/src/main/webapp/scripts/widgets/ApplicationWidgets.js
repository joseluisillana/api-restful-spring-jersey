'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.dashboard.widgets
 * @description
 * Module with all report widgets of the application.
 */
angular.module('bbva.or.widgets', ['bbva.or.db.provider'])
    .config(["dashboardProvider",function(dashboardProvider){


        // ANS COMPLIANCE REPORT
        dashboardProvider
            .widget('InformeANSWidget', {
                title: 'Informe ANS',
                description: 'Muestra un informe con los cumplimientos ANS.',
                previewImg : '/images/widgets/w4.png',
                controller: 'InformeANSController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/InformeANS/chart.html',
                config: {
                    titleIcoClass: 'fa-bar-chart',
                    hasFilter: true,
                    isFilterVisible: true
                },
                edit: {
                    templateUrl: 'partials/widgets/InformeANS/edit.html',
                    reload: true,
                    controller: 'InformeANSEditController'
                }
            });

        // MASTER FILTER
        dashboardProvider
            .widget('FiltroMaestroWidget', {
                title: 'Filtro Maestro',
                description: 'Muestra un filtro maestro para el resto de las gráficas de un dashboard.',
                previewImg : '/images/widgets/wfm.png',
                controller: 'FiltroMaestroController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/FiltroMaestro/chart.html',
                config: {
                    titleIcoClass: 'fa-search'
                },
                edit: {
                    templateUrl: 'partials/widgets/FiltroMaestro/edit.html',
                    reload: true,
                    controller: 'FiltroMaestroEditController'
                }
            });

    }]);
