'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.widgets.InformeANS
 * @description
 * Module Report: "Informe ANS"
 */
angular.module('bbva.or.widgets.InformeANS', ['bbva.or.db.provider'])

    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('InformeANSWidget', {
                title: 'Informe ANS',
                description: 'Muestra un informe con los cumplimientos ANS.',
                previewImg : 'images/widgets/w4.png',
                controller: 'InformeANSController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/InformeANS/widget.html',
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
    }]).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.InformeANS.controller:InformeANSController
     * @description
     * Controller for Module Report: "Informe ANS"
     */
    controller('InformeANSController', function($http, $rootScope, $scope, $controller, config, hierarchyService, chartsService, exportService){

        /** BBVA CHART BASE CONTROLLER EXTENSION */
        angular.extend(this, $controller('BBVAChartBaseController', {$scope: $scope}));

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#config
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {object} The report widget configuration.
         * @description
         * config: {object} The report widget configuration.
         **/
        $scope.config = config;
        if (!$scope.config.masterFilter){
            if($scope.config.hasFilter == undefined){
                $scope.config.hasFilter = true;
            }
            if($scope.config.isFilterVisible == undefined){
                $scope.config.isFilterVisible = true;
            }
        }

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#filter
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {object} The report widget filter object.
         * @description
         * The report widget filter object.
         *   Object properties:
         *   - `idSelectedFamily` - `{string}` - The id of the selected family.
         *   - `idSelectedSubFamily` - `{string}` - The id of the selected subfamily.
         *   - `idSelectedProcess` - `{string}` - The id of the selected process.
         *   - `idSelectedOperationType` - `{string}` - The id of the selected operation type.
         *   - `queryMode` - `{string}` - The query mode: P=Process Mode / OT=Operation Mode
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
            queryMode : null,
            startDate: null,
            endDate: null,
            startDateMonth: null,
            endDateMonth: null
        };

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#mode
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {string} The report widget query mode: ['P'=Process, 'OT'=Operation TYpe]
         * @description
         * mode: {string} The report widget query mode: ['P'=Process, 'OT'=Operation TYpe]
         **/
        $scope.mode = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#isLoadData
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {boolean} Control flag for loading data of the service
         * @description
         * isLoadData: {boolean} Control flag for loading data of the service
         **/
        $scope.isLoadData = false;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#restData
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The results of the rest service request.
         * @description
         * restData: {Array} The results of the rest service request.
         **/
        $scope.restData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#typeOperationsProcess
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {object} Object Hash with the results of the consultation service ans rest after processing for displaying charts.
         * @description
         * restData: {object} Object Hash with the results of the consultation service ans rest after processing for displaying charts.
         **/
        $scope.typeOperationsProcess = {};

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#operationsTypeLineChartDates
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The list of dates that have data on the results returned by service
         * @description
         * operationsTypeLineChartDates: {Array} The list of dates that have data on the results returned by service
         **/
        $scope.operationsTypeLineChartDates = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#isFilterChartCollapsed
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {boolean} Visual control for expand/collapse report filter.
         * @description
         * isFilterChartCollapsed: {boolean} Visual control for expand/collapse report filter.
         **/
        $scope.isFilterChartCollapsed = false;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#isMultiBarCollapsed
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {boolean} Visual control for expand/collapse multibar chart.
         * @description
         * isMultiBarCollapsed: {boolean} Visual control for expand/collapse multibar chart.
         **/
        $scope.isMultiBarCollapsed = true;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#isLineCollapsed
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {boolean} Visual control for expand/collapse line chart.
         * @description
         * isLineCollapsed: {boolean} Visual control for expand/collapse line chart.
         **/
        $scope.isLineCollapsed = true;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#multibarChart
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {object} (nv.models.multiBarChart) The multibar ANS chart.
         * @description
         * multibarChart: {object} (nv.models.multiBarChart) The multibar ANS chart.
         **/
        $scope.multibarChart = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#multibarChartData
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The multibar ANS chart data.
         * @description
         * multibarChartData: {Array} The multibar ANS chart data.
         **/
        $scope.multibarChartData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#multibarChartElement
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {string} Id of multibar chart container element.
         * @description
         * multibarChartElement: {string} Id of multibar chart container element.
         **/
        $scope.multibarChartElement = 'multibarChartElementDiv' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#multibarChartSVG
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {string} Id of SGV element for the multibar chart.
         * @description
         * multibarChartSVG: {string} Id of SGV element for the multibar chart.
         **/
        $scope.multibarChartSVG = 'multibarChartSGV' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#lineChart
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {object} (nv.models.lineChart) The evolution line ANS chart.
         * @description
         * lineChart: {object} (nv.models.lineChart) The evolution line ANS chart.
         **/
        $scope.lineChart = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#lineChartData
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The evolution line ANS chart data.
         * @description
         * lineChartData: {Array} The evolution line ANS chart data.
         **/
        $scope.lineChartData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#lineChartElement
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {string} Id of evolution line chart container element.
         * @description
         * lineChartElement: {string} Id of evolution line chart container element.
         **/
        $scope.lineChartElement = 'lineChartElementDiv' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#lineChartSVG
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {string} Id of SGV element for the evolution line chart.
         * @description
         * lineChartSVG: {string} Id of SGV element for the evolution line chart.
         **/
        $scope.lineChartSVG = 'lineChartSGV' +$scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#families
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The list of families of the process hierarchy
         **/
        $scope.families = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#subfamilies
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The list of subfamilies of the selected family.
         **/
        $scope.subfamilies = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#processes
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The list of processes of the selected subfamily.
         **/
        $scope.processes = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#processes
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {Array} The list of type operations of the selected process.
         **/
        $scope.typeOperations = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.InformeANS.property:InformeANSController#dayDatePickerOptions
         * @propertyOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @property {object} Configuration options of the daily date components
         **/
        $scope.dayDatePickerOptions = {startView:'day',minView:'day'};


        /* Init Configuration */
        if($scope.config.masterFilter){

            $scope.$on('masterFilter:change', function (event, data) {

                if($scope.config.masterFilter){
                    $scope.generateCharts(data);
                }
            });
        }else{

            /* Init Configuration
            if($scope.config!=null && $scope.config.id!=null){
                $timeout($scope.initConfiguration,700);
            }*/
        }


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#reset
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to save report user preferences.
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
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#resetUserConfiguration
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to remove report user preferences.
         */
        $scope.resetUserConfiguration = function() {

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
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#toogleConfiguration
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to toogle user preferences(save or remove).
         */
        $scope.toogleConfiguration = function() {

            if ($scope.config.userConfig) {
                $scope.resetUserConfiguration();
            }else{
                $scope.saveUserConfiguration();
            }
        };


        /** Process Hierachy Watcher */
        $scope.$watch('processHierachy', function(){
            $scope.families = hierarchyService.getFamilies();

        });

        /** Filter Family Watcher */
        $scope.$watch('filter.idSelectedFamily', function(){

            $scope.reset();

            $scope.subfamilies = hierarchyService.getSubFamiliesOfFamily($scope.filter.idSelectedFamily);
            $scope.processes = null;

            if(!$scope.loadingConfig){
                $scope.filter.idSelectedSubFamily = null;
                $scope.filter.idSelectedProcess = null;
                $scope.filter.idSelectedOperationType = null;
            }

            $rootScope.$broadcast('rootScope:broadcast', 'Broadcast')
        });

        /** Filter SubFamily Watcher */
        $scope.$watch('filter.idSelectedSubFamily', function(){

            $scope.reset();

            if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily){

                $scope.processes = hierarchyService.getProcessOfSubfamily($scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily);

                if(!$scope.loadingConfig){
                    $scope.filter.idSelectedProcess = null;
                    $scope.filter.idSelectedOperationType = null;
                }
            }
        });

        /** Filter Process Watcher */
        $scope.$watch('filter.idSelectedProcess', function(){

            $scope.reset();

            $scope.filter.idSelectedOperationType = null;
            $scope.typeOperations = [];
            if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily
                && $scope.filter.idSelectedProcess){

                $scope.typeOperations = hierarchyService.getOperationsOfProcess($scope.filter.idSelectedFamily,
                    $scope.filter.idSelectedSubFamily,$scope.filter.idSelectedProcess);
            }
        });


        /** Filter Operation Type Watcher */
        $scope.$watch('idSelectedOperationType', function(){ $scope.reset(); });

        /** Filter Start Date Watcher */
        $scope.$watch('startDate', function(){ $scope.reset(); });

        /** Filter End Date Watcher */
        $scope.$watch('endDate', function(){ $scope.reset(); });

        /** LineCollapsed Visual Control  Watcher */
        $scope.$watch('isLineCollapsed', function(){
            if(!$scope.isLineCollapsed && $scope.lineChart){
                $scope.lineChart.update();
            }
        });

        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#reset
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to reset report charts, data and visual controls.
         */
        $scope.reset = function(){

            $scope.isMultiBarCollapsed = true;
            $scope.isLineCollapsed = true;

            if($scope.restData){

                $scope.restData = null;
                $scope.typeOperationsProcess = {};
                $scope.operationsTypeLineChartDates = null;

                $scope.selectedElementName = null;
                $scope.selectedTypeOperation = null;

                /* Multibar */
                $scope.multibarChartData = null;
                $scope.resetSGV($scope.multibarChartElement, $scope.multibarChartSVG, 200);

                /* Line Chart */
                $scope.lineChartData = null;
                $scope.resetSGV($scope.lineChartElement, $scope.lineChartSVG, 250);

                /* Remove Resize Event Listeners */
                $scope.removeAllResizeEventListeners();
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#generateCharts
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to load data by selected filters and show the charts results.
         * @param {object} filterCriterias The filter criterias save in user preferences if exist.
         */
        $scope.generateCharts = function(filterCriterias){

            $scope.reset();

            $scope.prepareFilterCriterias(filterCriterias);

            if($scope.validateFilterCriterias()){
                $scope.loadChartData();
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#validateFilterCriterias
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to validate filter criterias before to do request to the service.
         */
        $scope.validateFilterCriterias = function(){

            if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily && $scope.filter.idSelectedProcess
                &&  $scope.filter.startDate!=null && $scope.filter.endDate!=null){

                return true;
            }else{
                if(!$scope.config.masterFilter){
                    $scope.openModal('Debe introducir todos los criterios');
                }
                return false;
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#prepareFilterCriterias
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to prepare filter criterias by master filter/report filter
         * @param {object} masterFilter The master filter criterias object.
         */
        $scope.prepareFilterCriterias = function(masterFilter){

            if(masterFilter){

                // Master Filter
                $scope.filter = {
                    idSelectedFamily: masterFilter.idSelectedFamily,
                    idSelectedSubFamily: masterFilter.idSelectedSubFamily,
                    idSelectedProcess: masterFilter.idSelectedProcess,
                    idSelectedOperationType: masterFilter.idSelectedOperationType,
                    queryMode: null,
                    startDate: null,
                    endDate: null
                };

                switch(masterFilter.modeTime) {
                    case 'D':
                        if (masterFilter.startDate) $scope.filter.startDate = masterFilter.startDate;
                        if(masterFilter.endDate)    $scope.filter.endDate = masterFilter.endDate;
                        break;
                    case 'M':
                        if(masterFilter.startDateMonth) $scope.filter.startDate =  masterFilter.startDateMonth;
                        if(masterFilter.endDateMonth)   $scope.filter.endDate = masterFilter.endDateMonth;
                        break;
                }
            }

            //Query Mode
            if(!$scope.filter.idSelectedOperationType){
                // Process Mode
                $scope.filter.queryMode = 'P';
            }else {
                // Operation Type Mode
                $scope.filter.queryMode = 'OT';
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#loadChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to load data of the service with the filter criterias
         */
        $scope.loadChartData = function(){

            $scope.isLoadData = true;

            if($scope.filter.queryMode == 'P'){
                // Process Mode
                chartsService.getANSComplianceByProcess($scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily,
                    $scope.filter.idSelectedProcess, $scope.filter.startDate.getTime(), $scope.filter.endDate.getTime()).then(
                    function(ansComplianceProcess){
                        $scope.refreshChartData(ansComplianceProcess);
                    }
                );
            }else{
                // Operation Type Mode
                $scope.mode = 'OT';

                chartsService.getANSComplianceByOperationType($scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily,
                    $scope.filter.idSelectedProcess, $scope.filter.idSelectedOperationType, $scope.filter.startDate.getTime(), $scope.filter.endDate.getTime()).then(
                    function(ansComplianceProcess){
                        $scope.refreshChartData(ansComplianceProcess);
                    }
                );
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#refreshChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to refresh the charts with the request results
         * @param {Array} ansComplianceProcess The result of request.
         */
        $scope.refreshChartData = function(ansComplianceProcess){

            $scope.restData = ansComplianceProcess;

            if($scope.restData && $scope.restData.length > 0){

               $scope.processRestData();

               $scope.refreshMultibarChartData();

            }else{

                $scope.restData = null;

                if(!$scope.config.masterFilter) {
                    $scope.openModal('No existen datos para los criterios introducidos');
                }
            }

            $scope.isLoadData = false;
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#processRestData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to process the request results
         */
        $scope.processRestData = function(){

            $scope.typeOperationsProcess = {};
            $scope.operationsTypeLineChartDates = [];

            angular.forEach($scope.restData, function(dayData, index){

                if(dayData.operations){

                    $scope.operationsTypeLineChartDates.push(dayData.date);

                    angular.forEach(dayData.operations, function(operationType, index) {

                        if(!$scope.typeOperationsProcess[operationType.operationtype]) {
                            $scope.typeOperationsProcess[operationType.operationtype] = {
                                operationtype: operationType.operationtype,
                                ansavgtime: operationType.ansavgtime,
                                numInstances: operationType.instancecount,
                                ansok: 0,
                                ansko: 0,
                                answarning: 0,
                                days: []
                            };
                        }

                        /* Data By Day */
                        $scope.typeOperationsProcess[operationType.operationtype].days.push({
                            day: dayData.date,
                            ans: operationType.ans,
                            ansavgtime: operationType.ansavgtime,
                            ansok: operationType.ansok,
                            ansko: operationType.ansko,
                            answarning: operationType.answarning
                        });

                        /* Data By All Days */
                        $scope.typeOperationsProcess[operationType.operationtype].ansok =
                            $scope.typeOperationsProcess[operationType.operationtype].ansok + operationType.ansok;

                        $scope.typeOperationsProcess[operationType.operationtype].ansko =
                            $scope.typeOperationsProcess[operationType.operationtype].ansko + operationType.ansko;

                        $scope.typeOperationsProcess[operationType.operationtype].answarning =
                            $scope.typeOperationsProcess[operationType.operationtype].answarning + operationType.answarning;

                    }, this);
                }
            }, this);
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#generateMultibarChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to refresh the multibar chart with the new request results.
         */
        $scope.generateMultibarChartData = function(){

            /* Multibar Type Operation ChartDataSegments */
            var chartDataSegments = {
                /* Segmento ANS OK */
                ansok: {
                    key: 'ANS OK',
                    color: "#008822",
                    values: []
                },
                /* Segmento ANS WARNING */
                answarning: {
                    key: "ANS WARNING",
                    color: '#ffaa00',
                    values: []
                },
                /* Segmento ANS KO */
                ansko: {
                    key: "ANS KO",
                    color: '#cc0000',
                    values: []
                }
            };

            angular.forEach($scope.typeOperationsProcess, function(operationType, index){

                /* Segmento ANS OK */
                chartDataSegments.ansok.values.push({x: operationType.operationtype, y: operationType.ansok, color: "#008822"});

                /* Segmento ANS WARNING */
                chartDataSegments.answarning.values.push({x: operationType.operationtype, y: operationType.answarning, color: '#ffaa00'});

                /* Segmento ANS KO */
                chartDataSegments.ansko.values.push({x: operationType.operationtype, y: operationType.ansko, color: '#cc0000'});

            }, this);

            $scope.multibarChartData = [];
            for(var key in chartDataSegments){
                var aux = chartDataSegments[key];
                $scope.multibarChartData.push(aux);
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#refreshMultibarChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to process and prepare the results of the request to the multibar chart.
         */
        $scope.refreshMultibarChartData = function(){

            if($scope.isMultiBarCollapsed)
                $scope.isMultiBarCollapsed = false;

            $scope.generateMultibarChartData();

            $scope.selectedElementName = $scope.filter.idSelectedProcess;

            $scope.multibarChart = nv.addGraph(function(){

                var chart = nv.models.multiBarChart()
                    .margin({bottom: 50});

                chart.multibar.hideable(false);
                ///chart.reduceXTicks(false);

                chart.yAxis
                    .tickFormat(d3.format(',.1f'))

                d3.select('#'+$scope.multibarChartElement+' svg')
                    .datum($scope.multibarChartData)
                    .transition()
                    .duration(500)
                    .call(chart);

                $scope.addResizeEventListener(function(){chart.update();});

                chart.dispatch.on('stateChange', function(e){
                    chart.update();
                });

                chart.multibar.dispatch.on("elementClick", function(e){
                    var operationType = e.point.x;
                    $scope.showLineChartData(operationType);
                });

                return chart;
            });
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#showLineChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to show the evolution line chart of a operation type.
         * @param {string} operationType The operation type identifier.
         */
        $scope.showLineChartData = function(operationType){

            $scope.selectedTypeOperation = operationType;

            $scope.refreshLineChartData();

            $scope.$apply();
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#generateLineChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to process and prepare to evolution line chart data for a operation type
         */
        $scope.generateLineChartData = function(){

            $scope.lineChartData = [];

            var operationType = $scope.typeOperationsProcess[$scope.selectedTypeOperation];

            if(operationType.days && operationType.days.length>0) {

                var operationTypeSerie = {key:operationType.operationtype, values:[]};

                var ansSerie = {key:"Tiempo ANS", values:[], color: '#ffaa00'};

                angular.forEach(operationType.days, function (operationTypeDay, index) {

                    operationTypeSerie.values.push({x: operationTypeDay.day,y: operationTypeDay.ansavgtime});

                    ansSerie.values.push({x: operationTypeDay.day,y: operationTypeDay.ans,  color: '#ffaa00'});

                }, this);

                $scope.lineChartData.push(operationTypeSerie);
                $scope.lineChartData.push(ansSerie);
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#refreshLineChartData
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to refresh the evolution line chart for the selected operation type
         */
        $scope.refreshLineChartData = function(){

            if($scope.isLineCollapsed)
                $scope.isLineCollapsed = false;

            $scope.generateLineChartData();

            $scope.lineChart = nv.addGraph(function() {

                var chart = nv.models.lineChart()
                        .margin({left: 50})
                        .useInteractiveGuideline(true)
                        .showLegend(true)
                        .showYAxis(true)
                        .showXAxis(true);

                chart.calculateTicksValues = function(){

                    // Init & Final Tick
                    var initialTick = $scope.operationsTypeLineChartDates[0];
                    var finalTick = $scope.operationsTypeLineChartDates[$scope.operationsTypeLineChartDates.length - 1];

                    // Ticks
                    var ticks = [];
                    ticks.push(initialTick);

                    var chartContainer = document.getElementById($scope.lineChartSVG);
                    var numTicks = nv.utils.calcTicksX(chartContainer.clientWidth/80, $scope.lineChartData) - 2;

                    var tickDistance = parseInt(($scope.operationsTypeLineChartDates.length-2)/numTicks);
                    var index = parseInt(tickDistance/2);
                    while(numTicks>0){
                        index = index + tickDistance;
                        ticks.push($scope.operationsTypeLineChartDates[index]);
                        numTicks--;
                    }
                    ticks.push(finalTick);

                    this.xAxis
                        .tickValues(ticks)
                        .tickFormat(function(d){
                            return d3.time.format('%d/%m/%Y')(new Date(d));
                        });
                };

                chart.calculateTicksValues();

                chart.yAxis.axisLabel('Media ANS');

                d3.select('#'+$scope.lineChartElement+' svg')
                    .datum($scope.lineChartData)
                    .transition()
                    .duration(500)
                    .call(chart);

                $scope.addResizeEventListener(function(){chart.calculateTicksValues(); chart.update();});

                return chart;
            });
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#prepareReportDataToExport
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to configure the data of the the export of the report.
         *
         *   ReportDataObject = {
         *       // Report Title
         *       name: 'Informe Altas Procesos Por Tipo de Operacion',
         *       // Report Configuration
         *       config:{
         *           orientation: 'portrait', // 'landscape'
         *           unit: 'pt',
         *           format: 'a4',
         *           compress: false
         *       },
         *       // Report Params
         *       params: [
         *           {name: 'Familia', value: 'XXX'},
         *           ....
         *       ],
         *       // Report Charts
         *       charts: [
         *           {name: 'Grafica de Sectores', idElement: $scope.pieChartElementSVG, type: 'SVG', flexible: false, minWidth: 1, maxHeight: 150},
         *           ...
         *       ],
         *       // Report Tables
         *       tables: [{
         *           name: 'Tabla: Altas por Tipo de Operacion y '+timeLineLabel,
         *           // Table Columns
         *           columns: [
         *              {title: "titleColumn1", key: "rowObjectProperty1"},
         *              ...
         *              {title: "titleColumnX", key: "rowObjectPropertyX"},
         *           ],
         *           // Table Data Rows
         *           data: [
         *              {rowObjectProperty1: 'AAA 1', .. , rowObjectPropertyX: 'BBB 1'},
         *              ...
         *              {rowObjectProperty1: 'AAA N', .. , rowObjectPropertyX: 'BBB N'},
         *           ]
         *       }]
         *   }
         */
        $scope.prepareReportDataToExport = function(){

            // Generate PDF
            var timeLine;
            var timeLineLabel;

            timeLine = "[ "+moment($scope.filter.startDate).format('DD/MM/YYYY')+" - "+moment($scope.filter.endDate).format('DD/MM/YYYY')+" ]";
            timeLineLabel = "Dia";

            // Tables Columns & Data
            var columnsTable = [];
            columnsTable.push({title: "Fecha", key: "day"});
            columnsTable.push({title: "Instancias Totales", key: "dayInstances"});
            columnsTable.push({title: "Instancias ANS OK", key: "ansok"});
            columnsTable.push({title: "Instancias ANS KO", key: "ansko"});
            columnsTable.push({title: "Instancias ANS Warning", key: "answarning"});
            columnsTable.push({title: "Tiempo ANS", key: "ans"});
            columnsTable.push({title: "Tiempo ANS Medio", key: "ansavgtime"});
            columnsTable.push({title: "Tiempo ANS Variacion", key: "ansVariationPercentage"});

            var dataTableArray = [];
            if($scope.selectedTypeOperation && $scope.typeOperationsProcess[$scope.selectedTypeOperation]){

                var typeOperation = $scope.typeOperationsProcess[$scope.selectedTypeOperation];

                for(var i=0; i<typeOperation.days.length; i++){

                    var dayTypeOperation = typeOperation.days[i],
                        dayText = moment(dayTypeOperation.day).format('DD/MM/YYYY'),
                        dayInstances = dayTypeOperation.ansok + dayTypeOperation.ansko + dayTypeOperation.answarning,
                        ansOkPercentage = (dayTypeOperation.ansok/dayInstances) * 100,
                        ansKoPercentage = (dayTypeOperation.ansko/dayInstances) * 100,
                        ansWarnPercentage = (dayTypeOperation.answarning/dayInstances) * 100,
                        ansOkPercentageTxt = " (" + ansOkPercentage.toFixed(2) + "%)",
                        ansKoPercentageTxt = " (" + ansKoPercentage.toFixed(2) + "%)",
                        ansWarnPercentageTxt = " (" + ansWarnPercentage.toFixed(2) + "%)",
                        ansVariationPercentage = ((dayTypeOperation.ansavgtime-dayTypeOperation.ans)/dayTypeOperation.ans) * 100;

                    var dataRow = {
                        day: dayText,
                        dayInstances: dayInstances,
                        ansok: dayTypeOperation.ansok + ansOkPercentageTxt,
                        ansko: dayTypeOperation.ansko + ansKoPercentageTxt,
                        answarning: dayTypeOperation.answarning + ansWarnPercentageTxt,
                        ans: dayTypeOperation.ans,
                        ansavgtime: dayTypeOperation.ansavgtime,
                        ansVariationPercentage: ansVariationPercentage.toFixed(2) + '%'
                    };
                    dataTableArray.push(dataRow);
                };
            };

            // Report Data
            var reportData = {
                name: 'Informe de Cumplimiento ANS',
                config:{
                    orientation: 'portrait', // 'landscape'
                    unit: 'pt',
                    format: 'a4',
                    compress: false
                },
                params: [
                    {name: 'Familia', value: $scope.filter.idSelectedFamily},
                    {name: 'SubFamilia', value: $scope.filter.idSelectedSubFamily},
                    {name: 'Proceso', value: $scope.filter.idSelectedProcess},
                    {name: 'Operación', value: $scope.filter.idSelectedOperationType},
                    {name: 'Tiempo', value: timeLine}
                ],
                charts: [
                    {name: 'Grafica de Barras', idElement: $scope.multibarChartSVG, type: 'SVG', minWidth: 1, maxHeight: 200},
                    {name: 'Evolución de Cumplimiento ANS del Tipo de Operacion: '+ $scope.selectedTypeOperation,
                                idElement: $scope.lineChartSVG, type: 'SVG', flexible: true},
                ],
                tables: [{
                    name: 'Tabla: Evolucion del Cumplimiento ANS del Tipo de Operacion: '+ $scope.selectedTypeOperation,
                    columns: columnsTable,
                    data: dataTableArray
                }]
            };
            return reportData;
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#onStartDateSet
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to show in human format the start date.
         */
        $scope.onStartDateSet = function(){
            if($scope.filter.startDate)
                $scope.startDateText = moment($scope.filter.startDate).format('DD/MM/YYYY');
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.InformeANS.controller:InformeANSController#onEndDateSet
         * @methodOf bbva.or.widgets.InformeANS.controller:InformeANSController
         * @description
         * Method to show in human format the end date.
         */
        $scope.onEndDateSet = function(){
            if($scope.filter.endDate)
                $scope.endDateText = moment($scope.filter.endDate).format('DD/MM/YYYY');
        };

    }).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.InformeANS.controller:InformeANSEditController
     * @description
     * Edit Controller for Module Report: "Informe ANS"
     */
    controller('InformeANSEditController', function($scope){


    });
