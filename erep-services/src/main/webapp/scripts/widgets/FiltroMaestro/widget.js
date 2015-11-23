'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.widgets.FiltroMaestro
 * @description
 * # bbva.or.widgets.FiltroMaestro
 * Module with the master filter widget.
 */
angular.module('bbva.or.widgets.FiltroMaestro', ['bbva.or.db.provider'])
    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('FiltroMaestroWidget', {
                title: 'Filtro Maestro',
                description: 'Muestra un filtro maestro para el resto de las gráficas de un dashboard.',
                previewImg : 'images/widgets/wfm.png',
                controller: 'FiltroMaestroController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/FiltroMaestro/widget.html',
                config: {
                    titleIcoClass: 'fa-search'
                },
                edit: {
                    templateUrl: 'partials/widgets/FiltroMaestro/edit.html',
                    reload: true,
                    controller: 'FiltroMaestroEditController'
                }
            });
    }]).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
     * @description
     * Controller with the master filter functionality.
     */
    controller('FiltroMaestroController', function($http, $rootScope, $scope, $modal, $timeout, config, hierarchyService, chartsService, exportService, dashboardConfigService){

       /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#UID
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {number} UID Universal identifier for controller instance.
         * @description
         * {number} UID Universal identifier for controller instance.
        **/
        $scope.UID = new Date().getTime();

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#config
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {number} config The report config.
         * @description
         * {number} UID Universal identifier for controller instance.
         **/
        $scope.config = config;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#filter
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {object} filter The report filter.
         * @description
         *   Object properties:
         *   - `idSelectedFamily` - `{string}` - The id of the selected family.
         *   - `idSelectedSubFamily` - `{string}` - The id of the selected subfamily.
         *   - `idSelectedProcess` - `{string}` - The id of the selected process.
         *   - `idSelectedOperationType` - `{string}` - The id of the selected operation type.
         *   - `modeTime` - `{string}` - The mode time: D=Daily / M=Monthly
         *   - `startDate` - `{object}` - The start date in daily mode time.
         *   - `endDate` - `{object}` - The end date in daily mode time.
         *   - `startDateMonth` - `{object}` - The start date in monthly mode time.
         *   - `endDateMonth` - `{object}` - The end date in monthly mode time.
         **/
        $scope.filter = {
            idSelectedFamily: null,
            idSelectedSubFamily: null,
            idSelectedProcess: null,
            idSelectedOperationType: null,
            modeTime: 'D',
            startDate: null,
            endDate: null,
            startDateMonth: null,
            endDateMonth: null
        };

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#families
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {Array} The list of families of the process hierarchy
         **/
        $scope.families = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#subfamilies
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {Array} The list of subfamilies of the selected family.
         **/
        $scope.subfamilies = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#processes
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {Array} The list of processes of the selected subfamily.
         **/
        $scope.processes = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#typeOperations
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {Array} The list of type operations of the selected process.
         **/
        $scope.typeOperations = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#dayDatePickerOptions
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {object} Configuration options of the daily date components
         **/
        $scope.dayDatePickerOptions = {startView:'day',minView:'day'};

        /**
         * @ngdoc property
         * @name bbva.or.widgets.FiltroMaestro.property:FiltroMaestroController#monthDatePickerOptions
         * @propertyOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @property {object} Configuration options of the monthly date components
         **/
        $scope.monthDatePickerOptions = {startView:'month',minView:'month'};

        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#initConfiguration
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to init widget configuration.
         */
        $scope.initConfiguration = function(){

            $scope.userConfig = true;
            $scope.loadingConfig = true;

            if($scope.config.idSelectedFamily) $scope.filter.idSelectedFamily = $scope.config.idSelectedFamily;
            if($scope.config.idSelectedSubFamily) $scope.filter.idSelectedSubFamily = $scope.config.idSelectedSubFamily;
            if($scope.config.idSelectedProcess)  $scope.filter.idSelectedProcess = $scope.config.idSelectedProcess;
            if($scope.config.modeTime) $scope.filter.modeTime = $scope.config.modeTime;
            if($scope.config.startDate) $scope.filter.startDate = new Date($scope.config.startDate);
            if($scope.config.endDate) $scope.filter.endDate = new Date($scope.config.endDate);
            if($scope.config.startDateMonth){
                $scope.filter.startDateMonth = new Date($scope.config.startDateMonth);
                $scope.startDateMonthText = moment($scope.filter.startDateMonth).format('MMMM') + ' de ' + moment($scope.startDateMonth).format('YYYY');
            }
            if($scope.config.endDateMonth){
                $scope.filter.endDateMonth = new Date($scope.config.endDateMonth);
                $scope.endDateMonthText = moment($scope.filter.endDateMonth).format('MMMM') + ' de ' + moment($scope.filter.endDateMonth).format('YYYY');
            }

            $timeout(function(){$scope.loadingConfig = false;});

        };

        /** Initialization Config Report */
        if($scope.config!=null && $scope.config.id!=null){
            $timeout($scope.initConfiguration,10);
        }


        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#saveUserConfiguration
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to save user widget configuration.
         */
        $scope.saveUserConfiguration = function() {

            $scope.config.userConfig = true;

            if(!$scope.config.id) $scope.config.id = new Date().getTime();
            if(!$scope.config.type) $scope.config.type = $scope.$parent.content.type;

            if($scope.filter.idSelectedFamily) $scope.config.idSelectedFamily = $scope.filter.idSelectedFamily;
            if($scope.filter.idSelectedSubFamily) $scope.config.idSelectedSubFamily = $scope.filter.idSelectedSubFamily;
            if($scope.filter.idSelectedProcess) $scope.config.idSelectedProcess = $scope.filter.idSelectedProcess;
            if($scope.filter.modeTime) $scope.config.modeTime = $scope.filter.modeTime;
            if($scope.filter.startDate) $scope.config.startDate = $scope.filter.startDate;
            if($scope.filter.endDate) $scope.config.endDate = $scope.filter.endDate;
            if($scope.filter.startDateMonth) $scope.config.startDateMonth = $scope.filter.startDateMonth;
            if($scope.filter.endDateMonth) $scope.config.endDateMonth = $scope.filter.endDateMonth;

            $rootScope.$broadcast('adfDashboardPageSave');
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#resetUserConfiguration
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to reset user widget configuration.
         */
        $scope.resetUserConfiguration = function(){

            $scope.config.userConfig = false;
            $scope.config.idSelectedFamily = undefined;
            $scope.config.idSelectedSubFamily = undefined;
            $scope.config.idSelectedProcess = undefined;
            $scope.config.modeTime = undefined;
            $scope.config.startDate = undefined;
            $scope.config.endDate = undefined;
            $scope.config.startDateMonth = undefined;
            $scope.config.endDateMonth = undefined;

            $rootScope.$broadcast('adfDashboardPageSave');
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#toogleConfiguration
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to toogle user widget configuration: save/reset configuration.
         */
        $scope.toogleConfiguration = function() {

            if ($scope.config.userConfig){
                $scope.resetUserConfiguration();
            }else{
                $scope.saveUserConfiguration();
            }
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#onEndDateSet
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to show in human format the start date in function of time mode.
         */
        $scope.onStartDateSet = function(){
            switch($scope.filter.modeTime){
                case 'D':
                    if($scope.filter.startDate)
                        $scope.startDateText = moment($scope.filter.startDate).format('DD/MM/YYYY');
                    break;
                case 'M':
                    if($scope.filter.startDateMonth){
                        var startMoment = moment($scope.filter.startDateMonth);
                        if(startMoment)
                            $scope.startDateMonthText = startMoment.format('MMMM') + ' de ' + startMoment.format('YYYY');
                    }
                    break;
            }
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#onEndDateSet
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to show in human format the end date in function of time mode.
         */
        $scope.onEndDateSet = function(){
            switch($scope.filter.modeTime){
                case 'D':
                    if($scope.filter.endDate)
                        $scope.endDateText = moment($scope.filter.endDate).format('DD/MM/YYYY');
                    break;
                case 'M':
                    if($scope.filter.endDateMonth){
                        var endMoment = moment($scope.filter.endDateMonth);
                        if(endMoment)
                            $scope.endDateMonthText = endMoment.format('MMMM') + ' de ' + endMoment.format('YYYY');
                    }
                    break;
            }
        };

        /** Fire Filter Change Event */
        $scope.$watch('filter', function(newFilterCriterias, oldFilterCriterias){
            console.log('masterFilter:change');
            $rootScope.$broadcast('masterFilter:change', newFilterCriterias);
        }, true);


        /** Process Hierachy Watcher */
        $scope.$watch('processHierachy', function(){
            $scope.families = hierarchyService.getFamilies();

        });

        /** Filter Family Watcher */
        $scope.$watch('filter.idSelectedFamily', function(){

            $scope.subfamilies = hierarchyService.getSubFamiliesOfFamily($scope.filter.idSelectedFamily);
            $scope.processes = null;

            if(!$scope.loadingConfig){
                $scope.filter.idSelectedSubFamily = null;
                $scope.filter.idSelectedProcess = null;
            }

            $rootScope.$broadcast('rootScope:broadcast', 'Broadcast')
        });

        /** Filter SubFamily Watcher */
        $scope.$watch('filter.idSelectedSubFamily', function(){

            if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily){

                $scope.processes = hierarchyService.getProcessOfSubfamily($scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily);

                if(!$scope.loadingConfig){
                    $scope.filter.idSelectedProcess = null;
                    $scope.selectedProcess = null;
                }
            }
        });

        /** Filter Process Watcher */
        $scope.$watch('filter.idSelectedProcess', function(){

            $scope.filter.idSelectedOperationType = null;
            $scope.typeOperations = []
            if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily
                && $scope.filter.idSelectedProcess){

                $scope.typeOperations = hierarchyService.getOperationsOfProcess($scope.filter.idSelectedFamily,
                    $scope.filter.idSelectedSubFamily,$scope.filter.idSelectedProcess);
            }
        });

        /** Filter Mode Time Watcher */
        $scope.$watch('filter.modeTime', function(){
            if($scope.filter.modeTime && !$scope.loadingConfig){
                $scope.chartData = {labels:[], datasets: []};
                $scope.filter.startDate = null;
                $scope.filter.endDate = null;
                $scope.startDateText = null;
                $scope.endDateText = null;
            }
        });
    });
