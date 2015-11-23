'use strict';



angular.module('bbva.or.dashboard.pages', ['LocalStorageModule']);

/**
 * OPERATIONAL REPORTING INIT
 */
/* angular.element(document).ready(function(){

    var initInjector = angular.injector(["ng","LocalStorageModule"]);

    var localStorageService = initInjector.get("localStorageService");

    var lastUserID = localStorage.getItem('bbva.or.lastUser.id')

    if(lastUserID){
       var user = localStorageService.get('user.'+lastUserID);
        $rootScope.menu = $rootScope.user.menu;
    } else {
        $scope.selectUser(0, false);
    }
    angular.bootstrap(document, ["bbva.or"]);

    // Angular Injector
    var initInjector = angular.injector(["ng"]);

    // HTTP Service
    var $http = initInjector.get("$http");

    angular.constant("user", '{id:1, name:"jaja"}');
    var a = angular.constant("user");
    debugger;
    //Load User Configuration
    $http.get("user").
        success(function(data, status, headers, config){
            var user = data;
            if(user.id){
                angular.constant("user", data);
            }
            angular.bootstrap(document, ["bbva.or"]);
        }).
        error(function(data, status, headers, config){
            //TODO QUITAR CUANDO ESTE ENGANCHADO CON LA AUTENTICACION
        });
});*/

/**
 * @ngdoc overview
 * @name bbva.or
 * @description
 * bbva.or
 * Application Module for Operational Reporting.
 */
angular.module('bbva.or', [
    'LocalStorageModule', 'ngRoute',
    'ui.bootstrap', 'ui.checkbox',
    'ui.bootstrap.datetimepicker',
    'ui.grid', 'ui.grid.selection', 'ui.grid.cellNav',
    'ui.grid.grouping','ui.grid.pinning', 'ui.grid.autoResize',
    'ui.grid.resizeColumns', 'ui.grid.moveColumns', 'ui.grid.exporter',

    //BBVA Application Configuration
    'bbva.or.appConfig',

    //BBVA Dashboard Module & Dashboard Structures
    'bbva.or.db', 'bbva.or.db.structures',

    //BBVA Commons
    'bbva.or.commons',

    //BBVA Providers
    'bbva.or.db.provider',

    //BBVA Services
    'bbva.or.services',

    //BBVA Directives
    'bbva.or.directives',

    //BBVA Report Widgets
    'bbva.or.widgets.FiltroMaestro',
    'bbva.or.widgets.ProcesosAltaPorTipoOperacion',
    'bbva.or.widgets.InformeANS',
    'bbva.or.widgets.ProcessHierarchy',
    'bbva.or.widgets.ProcessInstancesMonitor',
    'bbva.or.widgets.ANSReclamaciones',
    'bbva.or.widgets.Map',
    'bbva.or.widgets.TimeFilter',

    //BBVA Dashboard Pages
    'bbva.or.dashboard',
    'bbva.or.userDashboard',
    'bbva.or.dashboard.pages'
])
.config(function($routeProvider, $locationProvider,  localStorageServiceProvider, appConfig){

    // Local Storage Prefix Configuration
    localStorageServiceProvider.setPrefix(appConfig.localStoragePreffix);

    // Route Provider - Main Dashboard
    $routeProvider.when('/dashboard', {
        templateUrl: 'templates/dashboardPage.html',
        controller: 'DashboardController'
    });

    // Route Provider - User Dashboard
    $routeProvider.when('/userDashboard/:dashboardUID', {
        controller: 'UserDashboardController',
        templateUrl: 'templates/dashboardPage.html'
    });

    //Route Provider Configuration by Menu Options
    var addRoutesByMenu = function(menu){
        if(menu.url){
            var routeUrl = "/" + menu.url,
                routeConfig = {};
            if (menu.templateUrl) routeConfig.templateUrl = menu.templateUrl;
            if (menu.pageController) routeConfig.controller = menu.pageController;
            $routeProvider.when(routeUrl, routeConfig);
        }
        if(menu.items){
            angular.forEach(menu.items, function (menuItem, key){
                addRoutesByMenu(menuItem);
            });
        }
    };


    //Route Provider Configuration by Routes of Application
    var addRoutesOfApplication = function(routes){

        if(routes && routes.length > 0){

            angular.forEach(routes, function(route, key){

                if(route.url && route.templateUrl && route.pageController){

                    var routeUrl = "/" + route.url,
                        routeConfig = {};

                    if (route.templateUrl)
                        routeConfig.templateUrl = route.templateUrl;

                    if (route.pageController)
                        routeConfig.controller = route.pageController;

                    $routeProvider.when(routeUrl, routeConfig);
                }
            });
        }
    };

    if(appConfig.dynamicMenu==false && appConfig.menu){
        angular.forEach(appConfig.menu, function(menuGroup, key){
            addRoutesByMenu(menuGroup);
        });
    }else if(appConfig.dynamicMenu==true && appConfig.applicationRoutes){
        addRoutesOfApplication(appConfig.applicationRoutes);
    }

    // Route Provider - Default Route
    $routeProvider.otherwise({
        redirectTo: '/dashboard'
    });

    /*$locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });*/
})
.run(function($rootScope, $location, hierarchyService, i18nService){

    i18nService.setCurrentLang('es');

    $rootScope.$on('$routeChangeStart', function(event, next, current) {

        if (typeof(current) !== 'undefined'){
            window.nv.charts = {};
            window.nv.graphs = [];
            window.nv.logs = {};

            // remove resize listeners
            window.onresize = null;
        }
    });

    hierarchyService.getProcessHierachy().then(
        function(processHierachy){
            $rootScope.processHierachy = processHierachy;
        }
    );
})


/**
 * @ngdoc controller
 * @name bbva.or.controller:ApplicationController
 * @description
 * Main Controller of the application.
 */
.controller('ApplicationController', function($rootScope, $scope, $location, $modal, localStorageService, adfTemplatePath, appConfig, hierarchyService,  dashboardConfigService, logService){


    $scope.test = true;

    /** User Locale */
    moment.locale('es');
    moment.locale('es', {
        months : ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre']
    });

    /**
     * @ngdoc property
     * @name  bbva.or.property:ApplicationController#navCollapsed
     * @propertyOf bbva.or.controller:ApplicationController
     * @property {boolean} Visual control for collapse/expand menu
     * @description
     * {boolean} Visual control for collapse/expand menu
     **/
    $scope.navCollapsed = true;

    /** Route Change Event -> Collapse Menu */
    $scope.$on('$routeChangeStart', function(){
        $scope.navCollapsed = true;
    });

    /**
     * @ngdoc method
     * @name bbva.or.controller:ApplicationController#toggleNav
     * @methodOf bbva.or.controller:ApplicationController
     * @description
     * Method to collapse/expand menu.
     */
    $scope.toggleNav = function(){
        $scope.navCollapsed = !$scope.navCollapsed;
    };


    /**
     * @ngdoc method
     * @name bbva.or.controller:ApplicationController#navClass
     * @methodOf bbva.or.controller:ApplicationController
     * @description
     * Method to get style class of a menu as a function of the current page.
     * @param {string} page The relative url page of the option menu.
     */
    $scope.navClass = function(page){
        var currentRoute = $location.path().substring(1) || '';
        return page === currentRoute || new RegExp(page).test(currentRoute) ? 'active' : '';
    };


    /**
     * @ngdoc method
     * @name bbva.or.controller:ApplicationController#addPage
     * @methodOf bbva.or.controller:ApplicationController
     * @description
     * Method to add new user dashboard page.
     * @param {string} pageName The name for the new dashboard
     */
    $scope.addPage = function(pageName){

        var userPageModel = {
            id: new Date().getTime(),
            title: pageName,
            configurable: true,
            userPage: true,
            removable: true,
            structure: '6-6',
            rows: [{
                columns: [
                    {styleClass: 'col-md-6', widgets: []},
                    {styleClass: 'col-md-6', widgets: []}
                ]
            }]
        };

        dashboardConfigService.updateDashboardConfig(pageName, userPageModel);
    };

    /**
     * @ngdoc method
     * @name bbva.or.controller:ApplicationController#showAddPage
     * @methodOf bbva.or.controller:ApplicationController
     * @description
     * Method to show add new user dashboard page dialog.
     */
    $scope.showAddPage = function(){

        var addPageScope = $scope.$new();
        addPageScope.pageName='';

        var instance = $modal.open({
           // size: 'lg',
            windowClass: 'center-modal',
            scope: addPageScope,
            templateUrl: adfTemplatePath + 'dashboardPage-add.html'
        });

        addPageScope.addPage = function(pageName){
            $scope.addPage(pageName);
            instance.close();
        };

        addPageScope.close = function(){
            instance.close();
            addPageScope.$destroy();
        };
    };


    //TODO..... START AUTENTICANTION SIMULATION ()
    $scope.selectUser = function(idUser, noRefresh){

        var localStorageUser = localStorageService.get('user.'+idUser);

        if(localStorageUser){
            $rootScope.user = localStorageUser
        }else{
            $rootScope.user = $rootScope.users[idUser];
        }

        localStorageService.set('user.'+$rootScope.user.id ,$rootScope.user);
        localStorageService.set('lastUser.id', $rootScope.user.id);

        if(!noRefresh)
            location.reload();
    };

    $scope.navUser = function(user){
        if($rootScope.user && $rootScope.user.name === user){
            return 'active';
        }else{
            return '';
        }
    };

    $rootScope.lastUserID = localStorageService.get('lastUser.id');
    if($rootScope.lastUserID) {
        $rootScope.user = localStorageService.get('user.' + $rootScope.lastUserID);
    }

    if(!$rootScope.user){
        $scope.selectUser(0, false);
    }

    if($rootScope.appConfig.dynamicMenu == true){
        //Dynamic Menu of User
        $rootScope.menu = $rootScope.user.menu;
    }else{
        // Static Menu
        $rootScope.menu = $rootScope.appConfig.menu;
    }
    //TODO..... END AUTENTICANTION SIMULATION
});
