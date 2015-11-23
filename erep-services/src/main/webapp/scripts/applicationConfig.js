'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.appConfig
 * @description
 * Module of Application Configuration. In this module will be configured:
 *  - Menu.
 *  - Layouts.
 *  - Report Widgets.
 *  - Security?
 */
angular.module('bbva.or.appConfig', [])
    .constant('appConfig', {

        // Application Name
        appName: 'Reporting Operacional',

        // Application Logo
        appLogo: 'images/or.png',

        // Application Routes
        applicationRoutes:[
            {url: 'ProcessReports1', templateUrl:'templates/dashboardPage.html', pageController: 'ProcesosAltaPorTipoOperacionController'},
            {url: 'ProcessReports2', templateUrl:'templates/dashboardPage.html', pageController: 'ProcesosCumplimientoANSController'},
            {url: 'ans/reclamaciones', templateUrl:'templates/dashboardPage.html', pageController: 'ANSReclamacionesPageController'},
            {url: 'management/users', templateUrl:'templates/dashboardPage.html', pageController: 'UsersController'},
            {url: 'management/groups', templateUrl:'templates/dashboardPage.html', pageController: 'GroupsController'},
            {url: 'management/security', templateUrl:'templates/dashboardPage.html', pageController: 'SecurityController'},
            {url: 'management/settings', templateUrl:'templates/dashboardPage.html', pageController: 'ConfigurationController'}
        ],

        // Application Menu
        dynamicMenu: true,
        menu: [{
            name: 'Informes',
            iconClass: 'fa-bar-chart',
            items: [{
                name: 'Altas por Tipo de Operacion',
                url: 'ProcessReports1',
                templateUrl: 'templates/dashboardPage.html',
                pageController: 'ProcesosAltaPorTipoOperacionController'
            },{
                name:'Informe ANS',
                url:'ProcessReports2',
                templateUrl: 'templates/dashboardPage.html',
                pageController: 'ProcesosCumplimientoANSController'
            }]
        },{
            name: 'Administración',
            iconClass: 'fa-wrench',
            items: [{
                name:'Usuarios',
                url:'management/users',
                iconClass: 'fa-user',
                templateUrl: 'templates/dashboardPage.html',
                pageController: 'UsersController'
            },{
                name:'Grupos',
                url:'management/groups',
                iconClass: 'fa-users',
                templateUrl: 'templates/dashboardPage.html',
                pageController: 'GroupsController'
            },{
                name:'Seguridad',
                url:'management/security',
                iconClass: 'fa-shield',
                templateUrl: 'templates/dashboardPage.html',
                pageController: 'SecurityController'
            },{
                name:'Configuración',
                url:'management/settings',
                iconClass: 'fa-sliders',
                templateUrl: 'templates/dashboardPage.html',
                pageController: 'ConfigurationController'
            }]
        }],

        //Dashboard Config
        dynamicDashboard: true,
        defaultDashboard: {"title":"Dashboard Default","configurable": false, "structure":"6-6","rows":[{"columns":[{"styleClass":"col-md-6","widgets":[{"type":"ProcessHierarchyWidget","config":{},"title":"Visor de Familias de Procesos"}]},{"styleClass":"col-md-6","widgets":[{"type":"ProcessInstancesMonitorWidget","config":{},"title":"Monitor de Procesos"}]}]}]},

        // LocaStorage
        localStoragePreffix: 'bbva.or',

        // Logs
        logs: {
            enabledLog: true,
            logPrefix: 'BBVA-OR: '
        },

        // Services
        services: {
            machine: 'lcpxg401.igrupobbva',
            protocol: 'http',
            port: '7420',
            endPointServicesBasePath: 'EREP_PROCESSES/rest',
            list:{
                processHierachy: {endPoint: 'hierarchy/list', method: 'get'},
                processDefinitions: {endPoint: 'processes/list', method: 'get'},
                operationsByProcess: {endPoint: 'reports/operationsByProcess',method: 'get'},
                processANSCompliance: {endPoint: 'reports/ansComplianceByProcess/list',method: 'get'},
                operationTypeANSCompliance: {endPoint: 'reports/ansComplianceByOperation/list',method: 'get'},
                updateDashboardPageConfig: {endPoint: 'config/updateDashboardPageConfig',method: 'post'},
                getDashboardPageConfig: {endPoint: 'config/getDashboardPageConfig',method: 'get'},
                ansClaims: {endPoint: 'reports/ansCompliance/claims/list',method: 'get'}
            }
        }
    })
    .run(function($rootScope, $location, appConfig){

        $rootScope.appConfig = appConfig;

        //TODO SIMULAMOS EL LOGIN DEL USUARIOS
        $rootScope.users = {
            0: {
                id:'0',
                name:'Anónimo',
                group: 'Grupo Default',
                //dashboard: '-1', No pongo anda para que muestre el dashboard por defecto configurado
                menu: [
                    // Menu
                    {
                        name: 'Informes de Procesos',
                        iconClass: 'fa-cogs',
                        //Menu Items
                        items: [
                            {name:'Altas por Tipo de Operacion', url:'ProcessReports1'}
                        ]
                    }
                ]
            },
            1: {
                id:'1',
                name:'Oscar',
                group: 'Grupo 1',
                dashboard: {"title":"Dashboard Group 1", "configurable": false, "structure":"12/6-6","rows":[{"columns":[{"styleClass":"col-md-12","widgets":[{"type":"ProcessInstancesMonitorWidget","config":{},"title":"Monitor de Procesos"}]}]},{"columns":[{"styleClass":"col-md-6","widgets":[]},{"styleClass":"col-md-6","widgets":[]}]}]},
                menu: [
                    // Menu
                    {
                        name: 'Informes de Procesos',
                        iconClass: 'fa-cogs',
                        //Menu Items
                        items: [
                            {name:'Altas por Tipo de Operacion', url:'ProcessReports1'}
                        ]
                    },{
                        name: 'Administración',
                        iconClass: 'fa-wrench',
                        //Menu Items
                        items: [
                            {name:'Usuarios', url:'management/users', iconClass: 'fa-user'},
                            {name:'Grupos', url:'management/groups', iconClass: 'fa-users'},
                            {name:'Seguridad', url:'management/security', iconClass: 'fa-shield'},
                            {name:'Configuración', url:'management/settings', iconClass: 'fa-sliders'}
                        ]
                    }
                ]
            },
            2: {
                id:'2',
                name:'Emilio',
                group: 'Grupo 2',
                dashboard: {
                    "title":"Dashboard Group 2",
                    "configurable": false,
                    "structure":"12/6-6",
                    "rows":[{"columns":[{"styleClass":"col-md-12","widgets":[{"type":"ProcessHierarchyWidget","config":{},"title":"Visor de Familias de Procesos"}]}]},{"columns":[{"styleClass":"col-md-6","widgets":[]},{"styleClass":"col-md-6","widgets":[]}]}]},
                menu: [
                    // Menu
                    {
                        name: 'Informes de Procesos',
                        iconClass: 'fa-cogs',
                        //Menu Items
                        items: [
                            {name:'Altas por Tipo de Operacion', url:'ProcessReports1'},
                        ]
                    },{
                        name: 'Informes ANS',
                        iconClass: 'fa-clock-o',
                        //Menu Items
                        items: [
                            {name:'Proceso de Reclamaciones', url:'ans/reclamaciones'}
                        ]
                    }
                ],
                dashboardPages: [],
                userPages: [{
                    id:'11111111111',
                    title: 'Dashboard Emilio',
                    structure: '12/6-6',
                    configurable: true,
                    userPage: true,
                    removable: true,
                    rows: [{
                        columns: [{
                            styleClass: 'col-md-12',
                            widgets: [{
                                type: 'ProcesosAltaPorTipoOperacionNVD3Widget',
                                config:{titleIcoClass:"fa-bar-chart", hasFilter:true,isFilterVisible:true}
                            }]
                        }]
                    }]
                }]
            }
        };
});




