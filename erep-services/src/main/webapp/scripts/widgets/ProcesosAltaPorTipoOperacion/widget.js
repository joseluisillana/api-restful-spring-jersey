'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion
 * @description
 * Module Report: "Altas de Procesos por Tipo de Operacion"
 */
angular.module('bbva.or.widgets.ProcesosAltaPorTipoOperacion', ['bbva.or.db.provider'])

    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('ProcesosAltaPorTipoOperacionNVD3Widget', {
                title: 'Procesos de Alta Por Tipo de Operacion',
                description: 'Muestra un informe con las instancias de los procesos por tipo de operación.',
                previewImg : 'images/widgets/w3.png',
                controller: 'ProcesosAltaPorTipoOperacionNVD3Controller',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/ProcesosAltaPorTipoOperacion/widget.html',
                config:{
                    titleIcoClass: 'fa-bar-chart',
                    hasFilter: true,
                    isFilterVisible: true
                },
                edit: {
                    templateUrl: 'partials/widgets/ProcesosAltaPorTipoOperacion/edit.html',
                    reload: true,
                    controller: 'ProcesosAltaPorTipoOperacionNVD3EditController'
                }
            });
    }]).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
     * @description
     * Controller for Module Report: "Altas de Procesos por Tipo de Operacion"
     */
    controller('ProcesosAltaPorTipoOperacionNVD3Controller', function($http, $rootScope, $scope, $controller, $modal, $timeout, config, hierarchyService, chartsService, exportService, dashboardConfigService) {

        /** BBVA CHART BASE CONTROLLER EXTENSION */
        angular.extend(this, $controller('BBVAChartBaseController', {$scope: $scope}));

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#config
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} The report widget configuration.
         * @description
         * config: {object} The report widget configuration.
         **/
        $scope.config = config;

        if(!$scope.config.masterFilter){
            if ($scope.config.hasFilter == undefined){
                $scope.config.hasFilter = true;
            }
            if ($scope.config.isFilterVisible == undefined){
                $scope.config.isFilterVisible = true;
            }
        }

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#filter
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#isBarChartCollapsed
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {boolean} Visual control for expand/collapse bar chart.
         * @description
         * isBarChartCollapsed:  Visual control for expand/collapse bar chart.
         **/
        $scope.isBarChartCollapsed = true;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#isPieChartCollapsed
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {boolean} Visual control for expand/collapse pie chart.
         * @description
         * isBarChartCollapsed:  Visual control for expand/collapse pie chart.
         **/
        $scope.isPieChartCollapsed = true;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#isTypeOperationChartCollapsed
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {boolean} Visual control for expand/collapse evolution line chart.
         * @description
         * isTypeOperationChartCollapsed:  Visual control for expand/collapse evolution line chart.
         **/
        $scope.isTypeOperationChartCollapsed = true;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#isMultiBarCollapsed
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {boolean} Visual control for expand/collapse multibar chart.
         * @description
         * isMultiBarCollapsed:  Visual control for expand/collapse evolution multibar chart.
         **/
        $scope.isMultiBarCollapsed = true;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#isFilterChartCollapsed
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {boolean} Visual control for expand/collapse chart filter.
         * @description
         * isFilterChartCollapsed:  Visual control for expand/collapse chart filter.
         **/
        $scope.isFilterChartCollapsed = false;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#typeOperationsProcess
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} Object Hash with the results of the consultation service ans rest after processing for displaying charts.
         * @description
         * typeOperationsProcess: Object Hash with the results of the consultation service ans rest after processing for displaying charts.
         **/
        $scope.typeOperationsProcess = {};

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#typeOperationsProcess
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} (nv.models.discreteBarChart) The bar chart object of the report.
         * @description
         * barChart: {object} (nv.models.discreteBarChart) The bar chart object of the report.
         **/
        $scope.barChart = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#typeOperationsProcess
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The bar chart data.
         * @description
         * barChart: {Array} The bar chart data.
         **/
        $scope.barChartData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#typeOperationsProcess
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} Id of the chart element container.
         * @description
         * barChartElement: {string} Id of the chart element container.
         **/
        $scope.barChartElement = 'barChartDiv'+ $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#barChartSVG
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} Id of the chart SVG.
         * @description
         * barChartSVG: {string} Id of the chart SVG.
         **/
        $scope.barChartSVG = 'barChartSVG'+ $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#pieChart
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} (nv.models.pieChart) The pie chart of the report.
         * @description
         * pieChart: {object} (nv.models.pieChart) The pie chart of the report.
         **/
        $scope.pieChart = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#pieChartData
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The pie chart data.
         * @description
         * pieChartData: {Array} The pie chart data.
         **/
        $scope.pieChartData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#pieChartElement
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} The id of the pie chart element container.
         * @description
         * pieChartElement: {string} The id of the pie chart element container.
         **/
        $scope.pieChartElement = 'pieChartDiv'+ $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#pieChartSGV
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} The id of the SVG pie chart.
         * @description
         * pieChartSGV: {string} The id of the SVG pie chart.
         **/
        $scope.pieChartSGV = 'pieChartSGV'+ $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#operationsTypeLineChart
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} (nv.models.lineWithFocusChart) The line evolution chart of the report.
         * @description
         * operationsTypeLineChart: {object} (nv.models.lineWithFocusChart) The line evolution chart of the report.
         **/
        $scope.operationsTypeLineChart = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#operationsTypeLineChartData
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The line evolution chart data.
         * @description
         * operationsTypeLineChartData: {Array} The line evolution chart data.
         **/
        $scope.operationsTypeLineChartData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#operationsTypeLineElement
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} The line evolution chart container element.
         * @description
         * operationsTypeLineElement: {string} The line evolution chart container element.
         **/
        $scope.operationsTypeLineElement = 'operationsTypeLineChartDiv' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#operationsTypeLineSGV
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} The line evolution chart SVG element.
         * @description
         * operationsTypeLineSGV: {string} The line evolution chart SVG element.
         **/
        $scope.operationsTypeLineSGV = 'lineChartSGV' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#multibarChart
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} (nv.models.multiBarChart) The multibar chart of the report.
         * @description
         * multibarChart: {object} (nv.models.multiBarChart) The multibar chart of the report
         **/
        $scope.multibarChart = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#multibarChartData
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The multibar chart data.
         * @description
         * multibarChartData: {Array} The multibar chart data.
         **/
        $scope.multibarChartData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#multibarChartElement
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {string} The multibar chart element container.
         * @description
         * multibarChartElement: {string} The multibar chart element container.
         **/
        $scope.multibarChartElement = 'multibarChartElementDiv' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#multibarChartSGV
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The multibar SVG chart.
         * @description
         * multibarChartSGV: {Array} The multibar SVG chart.
         **/
        $scope.multibarChartSGV = 'multibarChartSGV' + $scope.UID;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#restData
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The results of the rest service request.
         * @description
         * restData: {Array} The results of the rest service request.
         **/
        $scope.restData = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#operationsTypeLineChartDates
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {Array} The list of dates that have data on the results returned by service
         * @description
         * operationsTypeLineChartDates: {Array} The list of dates that have data on the results returned by service
         **/
        $scope.operationsTypeLineChartDates = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#families
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description {Array} The list of families of the process hierarchy.
         **/
        $scope.families = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#subfamilies
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description {Array} The list of subfamilies of the selected family.
         **/
        $scope.subfamilies = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#processes
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description {Array} The list of processes of the selected subfamily.
         **/
        $scope.processes = null;

        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#dayDatePickerOptions
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description {object} Configuration options of the daily date components
         **/
        $scope.dayDatePickerOptions = {startView:'day',minView:'day'};


        /**
         * @ngdoc property
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.property:ProcesosAltaPorTipoOperacionController#monthDatePickerOptions
         * @propertyOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @property {object} Configuration options of the monthly date components
         **/
        $scope.monthDatePickerOptions = {startView:'month',minView:'month'};


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#initConfiguration
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to apply user preferences configuration to the report.
         */
        $scope.initConfiguration = function(){

            $scope.userConfig = true;
            $scope.loadingConfig = true;

            if($scope.config.idSelectedFamily) $scope.filter.idSelectedFamily = $scope.config.idSelectedFamily;
            if($scope.config.idSelectedSubFamily) $scope.filter.idSelectedSubFamily = $scope.config.idSelectedSubFamily;
            if($scope.config.idSelectedProcess)  $scope.filter.idSelectedProcess = $scope.config.idSelectedProcess;
            if($scope.config.modeTime) $scope.filter.modeTime = $scope.config.modeTime;
            if($scope.config.startDate) {
                $scope.filter.startDate = $scope.config.startDate;
                $scope.startDateText = moment($scope.filter.startDate).format('DD/MM/YYYY');
            }

            if($scope.config.endDate) {
                $scope.filter.endDate = $scope.config.endDate;
                $scope.endDateText = moment($scope.filter.endDate).format('DD/MM/YYYY');
            }
            if($scope.config.startDateMonth){
                $scope.filter.startDateMonth = $scope.config.startDateMonth;
                $scope.startDateMonthText = moment($scope.startDateMonth).format('MMMM') + ' de ' + moment($scope.startDateMonth).format('YYYY');
            }
            if($scope.config.endDateMonth){
                $scope.filter.endDateMonth = $scope.config.endDateMonth;
                $scope.endDateMonthText = moment($scope.endDateMonth).format('MMMM') + ' de ' + moment($scope.endDateMonth).format('YYYY');
            }

            $timeout(function(){$scope.loadingConfig = false;});

            if(($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily && $scope.filter.idSelectedProcess && $scope.filter.modeTime)){
                switch($scope.filter.modeTime){
                    case 'D':
                        if($scope.filter.startDate && $scope.filter.endDate){
                            $scope.loadChartData();
                        }
                        break;
                    case 'M':
                        if($scope.filter.startDateMonth && $scope.filter.endDateMonth){
                            $scope.loadChartData();
                        }
                        break;
                }
            }
        };

        if($scope.config.masterFilter){
            $scope.$on('masterFilter:change', function (event, data){
                if($scope.config.masterFilter){
                    $scope.modeTime = data.modeTime;
                    $scope.generateCharts(data);
                }
            });
        }else{
            /* Init Configuration */
            if($scope.config!=null && $scope.config.id!=null){
                $timeout($scope.initConfiguration,1000);
            }
        }


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#saveUserConfiguration
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#resetUserConfiguration
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to remove report user preferences.
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
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#toogleConfiguration
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#onStartDateSet
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#generateCharts
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#validateFilterCriterias
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to validate filter criterias before to do request to the service.
         */
        $scope.validateFilterCriterias = function(){

            if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily && $scope.filter.idSelectedProcess
                && (($scope.filter.modeTime == 'D' && $scope.filter.startDate && $scope.filter.endDate) ||
                    ($scope.filter.modeTime == 'M' && $scope.filter.startDateMonth && $scope.filter.endDateMonth))){

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
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#prepareFilterCriterias
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to validate filter criterias before to do request to the service.
         * * @param {object} masterFilter The master filter criterias object.
         */
        $scope.prepareFilterCriterias = function(masterFilter){

            if(masterFilter){

                $scope.loadingConfig = true;

                // Master Filter
                var newFilter = {
                    idSelectedFamily: masterFilter.idSelectedFamily,
                    idSelectedSubFamily: masterFilter.idSelectedSubFamily,
                    idSelectedProcess: masterFilter.idSelectedProcess,
                    modeTime: masterFilter.modeTime,
                    startDate: null,
                    endDate: null,
                    startDateMonth: null,
                    endDateMonth: null
                };

                switch(masterFilter.modeTime){
                    case 'D':
                        if (masterFilter.startDate) newFilter.startDate = masterFilter.startDate;
                        if (masterFilter.endDate) newFilter.startDate = masterFilter.endDate;
                        break;
                    case 'M':
                        if(masterFilter.startDateMonth) newFilter.startDateMonth = masterFilter.startDateMonth;
                        if(masterFilter.endDateMonth)   newFilter.endDateMonth = masterFilter.endDateMonth;
                        break;
                }

                $scope.filter = newFilter;
                $timeout(function(){$scope.loadingConfig = false;});
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#loadChartData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to load the data from REST service through searching filters.
         */
        $scope.loadChartData = function(){

            var startDateText = null;
            var endDateText = null;

            switch($scope.filter.modeTime){
                case 'D':
                    if($scope.filter.startDate)
                        startDateText = moment($scope.filter.startDate).format('YYYYMMDD');
                    if($scope.filter.endDate)
                        endDateText = moment($scope.filter.endDate).format('YYYYMMDD');
                    break;
                case 'M':
                    if($scope.filter.startDateMonth)
                        startDateText = moment($scope.filter.startDateMonth).format('YYYYMM');
                    if($scope.filter.endDateMonth)
                        endDateText = moment($scope.filter.endDateMonth).format('YYYYMM');
                    break;
            };

            // ChartService Call
            chartsService.getOperationsByProcess( $scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily, $scope.filter.idSelectedProcess,
                $scope.filter.modeTime, startDateText, endDateText).then(
                function(operationsProcess){

                    // Process Data & Refresh Charts
                    $scope.refreshChartData(operationsProcess);
                }
            );
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#refreshChartData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to refresh report charts with the data obtained from the service.
         * @param {Array} operationsProcess The data obtained of the REST service
         */
        $scope.refreshChartData = function(operationsProcess){

            if(operationsProcess && operationsProcess.length > 0){

                // Process Data
                $scope.processRestData(operationsProcess);

                // Refresh Bar Chart
                $scope.refreshBarChart();

                // Refresh Pie Chart
                $scope.refreshPieChart();

                // Refresh Multibar Chart
                $scope.refreshMultibarChart();

                // Refresh Line Chart
                $scope.refreshLineChart();

            }else{

                $scope.restData = null;

                if(!$scope.config.masterFilter) {
                    $scope.openModal('No existen datos para los criterios introducidos');
                }
            }
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#processRestData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to process the data obtained from the service.
         * @param {Array} restData The data obtained of the REST service.
         */
        $scope.processRestData = function(restData){

            $scope.restData = restData;

            $scope.typeOperationsProcess = {};

            angular.forEach($scope.restData, function(dayData, index){
                if(dayData.total){
                    angular.forEach(dayData.total, function(operationType, index) {
                        if(!$scope.typeOperationsProcess[operationType.operationtype]) {
                            $scope.typeOperationsProcess[operationType.operationtype] = 0;
                        }
                        $scope.typeOperationsProcess[operationType.operationtype] = $scope.typeOperationsProcess[operationType.operationtype] + operationType.amount;
                    }, this);
                }
            }, this);
        };

        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#generateBarChartData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to process the data obtained from the service to generate for the bar chart
         */
        $scope.generateBarChartData = function(){

            var chartData = {
                key: $scope.restData.process,
                values: []
            };

            angular.forEach($scope.typeOperationsProcess, function(typeOperationInstances, typeOperationName){
                chartData.values.push({
                    label: typeOperationName,
                    value: typeOperationInstances
                });
            }, this);

            $scope.barChartData = [];
            $scope.barChartData.push(chartData);
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#refreshBarChart
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to refresh report bar chart.
         */
        $scope.refreshBarChart = function(){

            $scope.generateBarChartData();

            $scope.isBarChartCollapsed = true;
            $scope.isPieChartCollapsed = true;
            $scope.isTypeOperationChartCollapsed = true;
            $scope.isMultiBarCollapsed = true;
            $scope.isBarChartCollapsed = false;

            $scope.barChart = nv.addGraph(function(){

                var chart = nv.models.discreteBarChart()
                    .x(function(d) { return d.label })
                    .y(function(d) { return d.value })
                    //.staggerLabels(true)
                    .tooltips(true)
                    .showValues(true);

                chart.xAxis
                    .axisLabel('Tipos de Operación')
                    .axisLabelDistance(5);

                chart.yAxis
                    .axisLabel('Nº Altas');

                chart.discretebar.dispatch.on("elementClick", function(e) {
                    $scope.showOperationType(e.point.label);
                });

                d3.select('#'+$scope.barChartElement+' svg')
                    .datum($scope.barChartData)
                    .transition()
                    .duration(500)
                    .call(chart);

                $scope.addResizeEventListener(function(){ chart.update();});

                return chart;
            });
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#generateLineChartData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to process the data obtained from the service to generate for the evolution line chart.
         */
        $scope.generateLineChartData = function(){

            var chartDataSegments = [];

            $scope.operationsTypeLineChartDates = [];

            var selectedProcess = hierarchyService.getProcess($scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily,
                $scope.filter.idSelectedProcess);

            angular.forEach($scope.restData, function(dayData, index){

                var dayMoment = moment(dayData.date, "YYYYMMDD");
                if(dayData.total && dayMoment){

                    $scope.operationsTypeLineChartDates.push(dayMoment._d.getTime());

                    angular.forEach(dayData.total, function(operationType, index){
                        if(!chartDataSegments[operationType.operationtype]){
                            chartDataSegments[operationType.operationtype] = { key:operationType.operationtype, values: []};
                        }
                        chartDataSegments[operationType.operationtype].values.push({x: dayMoment._d.getTime(), y:operationType.amount, date: dayData.date})
                    }, this);

                    // Rellenamos valores vacios


                    angular.forEach(selectedProcess.operationtypes, function(operationTypesOfProcess, key){
                        if(!chartDataSegments[operationTypesOfProcess]){
                            chartDataSegments[operationTypesOfProcess] = {key: operationTypesOfProcess, values: []};
                            chartDataSegments[operationTypesOfProcess].values.push({x: dayMoment._d.getTime(), y:0})
                        }
                    }, this);
                }
            }, this);

            $scope.operationsTypeLineChartData = [];

            for(var key in chartDataSegments){
                var aux = chartDataSegments[key];
                $scope.operationsTypeLineChartData.push(aux);
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#refreshLineChart
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to refresh report evolution line chart.
         */
        $scope.refreshLineChart = function(){

            $scope.isTypeOperationChartCollapsed = false;

            $scope.generateLineChartData();

            $scope.operationsTypeLineChart = nv.addGraph(function(){

                var chart = nv.models.lineWithFocusChart();
                chart.margin({bottom: 30});

                chart.calculateTicksValues = function(){

                    // Init & Final Tick
                    var initialTick = $scope.operationsTypeLineChartDates[0];
                    var finalTick = $scope.operationsTypeLineChartDates[$scope.operationsTypeLineChartDates.length - 1];

                    // Ticks
                    var ticks = [];
                    ticks.push(initialTick);

                    var chartContainer = document.getElementById($scope.operationsTypeLineSGV);
                    var numTicks = nv.utils.calcTicksX(chartContainer.clientWidth/80, $scope.operationsTypeLineChartData) - 2;

                    var tickDistance = parseInt(($scope.operationsTypeLineChartDates.length-2)/numTicks);
                    var index = parseInt(tickDistance/2);
                    numTicks = numTicks + 1;

                    while(numTicks>0){
                        index = index + tickDistance;
                        ticks.push($scope.operationsTypeLineChartDates[index]);
                        numTicks--;
                    }
                    ticks.push(finalTick);

                    this.xAxis
                        .tickValues(ticks)
                        .tickFormat(function(d){
                            var xAxisTickLabel='';
                            switch($scope.filter.modeTime){
                                case 'D':
                                    xAxisTickLabel = d3.time.format('%d/%m/%Y')(new Date(d));
                                    break;
                                case 'M':
                                    xAxisTickLabel = d3.time.format('%B')(new Date(d));
                                    break;
                            };
                            return xAxisTickLabel;
                        });

                    this.x2Axis.tickValues(ticks);
                    this.x2Axis.tickFormat(function(d){
                        var xAxisTickLabel='';
                        switch($scope.filter.modeTime){
                            case 'D':
                                xAxisTickLabel = d3.time.format('%d/%m/%Y')(new Date(d));
                                break;
                            case 'M':
                                xAxisTickLabel = d3.time.format('%B')(new Date(d));
                                break;
                        };
                        return xAxisTickLabel;
                    });
                };

                chart.calculateTicksValues();

                //chart.xAxis.rotateLabels(45);
                chart.x2Axis.rotateLabels(45);

                chart.yAxis.tickFormat(d3.format(',.2f'));
                chart.y2Axis.tickFormat(d3.format(',.2f'));

                d3.select('#'+$scope.operationsTypeLineElement+' svg')
                    .datum($scope.operationsTypeLineChartData)
                    .transition()
                    .duration(500)
                    .call(chart);


                $scope.addResizeEventListener(function(){chart.calculateTicksValues() ; chart.update();});

                return chart;
            });
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#generateMultibarChartData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to process the data obtained from the service to generate for the multibar chart.
         */
        $scope.generateMultibarChartData = function(){

            var chartDataSegments = [];

            angular.forEach($scope.restData, function(dayData, index){

                var dayMoment = moment(dayData.date, "YYYYMMDD");
                if(dayData.total && dayMoment){

                    angular.forEach(dayData.total, function(operationType, index) {
                        if(!chartDataSegments[operationType.operationtype]) {
                            chartDataSegments[operationType.operationtype] = { key:operationType.operationtype, values: []};
                        }
                        chartDataSegments[operationType.operationtype].values.push({x: dayMoment._d.getTime(), y:operationType.amount})
                    }, this);
                }
            }, this);

            $scope.multibarChartData = [];
            for(var key in chartDataSegments){
                var aux = chartDataSegments[key];
                $scope.multibarChartData.push(aux);
            }
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#refreshMultibarChart
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to refresh report multibar chart.
         */
        $scope.refreshMultibarChart = function(){

            $scope.isMultiBarCollapsed = false;

            $scope.generateMultibarChartData();

            $scope.multibarChart = nv.addGraph(function(){

                var chart = nv.models.multiBarChart()
                    .groupSpacing(0.1)
                    .reduceXTicks(true)
                    .margin({bottom: 30});

                chart.multibar.hideable(true);

                chart.xAxis
                    .axisLabel("")
                    .showMaxMin(true)
                    .tickFormat(function(d) {
                        var xAxisTickLabel='';
                        switch($scope.filter.modeTime){
                            case 'D':
                                xAxisTickLabel = d3.time.format('%d/%m/%Y')(new Date(d));
                                break;
                            case 'M':
                                //xAxisTickLabel = moment(new Date(d)).format("MMMM");
                                xAxisTickLabel = d3.time.format('%B')(new Date(d));
                                break;
                        };
                        return xAxisTickLabel;
                    });

                chart.yAxis
                    .tickFormat(d3.format(',.1f'));

                d3.select('#'+$scope.multibarChartElement+' svg')
                    .datum($scope.multibarChartData)
                    .transition()
                    .duration(500)
                    .call(chart);

                var eventListener = function(){ chart.update(); };

                $scope.addResizeEventListener(function(){chart.update();});

                chart.dispatch.on('stateChange', function(e) { chart.update()});

                return chart;
            });
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#generatePieChartData
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to process the data obtained from the service to generate for the pie chart.
         */
        $scope.generatePieChartData = function(){

            var chartData = [];

            angular.forEach($scope.typeOperationsProcess, function(typeOperationInstances, typeOperationName){
                chartData.push({
                    label: typeOperationName,
                    value: typeOperationInstances
                });
            }, this);

            $scope.pieChartData = chartData;
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#refreshPieChart
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to refresh report pie chart.
         */
        $scope.refreshPieChart = function(){

            $scope.generatePieChartData();

            $scope.isPieChartCollapsed = false;

            $scope.pieChart = nv.addGraph(function() {

                var chart = nv.models.pieChart()
                    .x(function(d) { return d.label })
                    .y(function(d) { return d.value })
                    .tooltips(true)
                    .labelThreshold(.05)
                    .labelType("percent")
                    .showLabels(true);

                d3.select('#'+$scope.pieChartElement+' svg')
                    .datum($scope.pieChartData)
                    .transition()
                    .duration(500)
                    .call(chart);

                var eventListener = function(){
                    chart.update();
                };

                $scope.addResizeEventListener(function(){chart.update();});

                return chart;
            });
        };


        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#prepareReportDataToExport
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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
        $scope.prepareReportDataToExport = function() {

            // Generate PDF
            var timeLine;
            var timeLineLabel;

            switch($scope.filter.modeTime){
                case 'D':
                    timeLine = "[ "+moment($scope.filter.startDate, 'YYYYMMDD').format('DD/MM/YYYY')+" - "
                                    + moment($scope.filter.endDate, 'YYYYMMDD').format('DD/MM/YYYY')+" ]";
                    timeLineLabel = "Dia";
                    break;
                case 'M':
                    var startMonthMoment = moment($scope.filter.startDateMonth, 'YYYYMM');
                    var endMonthMoment = moment($scope.filter.endDateMonth, 'YYYYMM');
                    timeLine = "[ "+ startMonthMoment.format('MMMM') + ' de ' + startMonthMoment.format('YYYY')+
                    " - "+endMonthMoment.format('MMMM') + ' de ' + endMonthMoment.format('YYYY')+" ]";
                    timeLineLabel = "Mes";
                    break;
            };

            // Tables Columns & Data
            var columnsTable = [];
            var dataTable = {};

            columnsTable.push({title: "Fecha", key: "fecha"});

            for(var i=0; i<$scope.operationsTypeLineChartDates.length; i++){
                var dataByTimeItemID = $scope.operationsTypeLineChartDates[i];
                var dayText = moment(new Date(dataByTimeItemID)).format('DD/MM/YYYY');
                var dataByTimeItem = {fecha: dayText};
                dataTable[dataByTimeItemID] = dataByTimeItem;
            }

            for(var i=0; i<$scope.operationsTypeLineChartData.length; i++) {
                var operationTypeData = $scope.operationsTypeLineChartData[i];
                columnsTable.push({title: operationTypeData.key, key: operationTypeData.key});

                for(var j=0; j<operationTypeData.values.length; j++){
                    var operationTypeDataDay = operationTypeData.values[j];
                    dataTable[operationTypeDataDay.x][operationTypeData.key] = operationTypeDataDay.y;
                }
            }

            var dataTableArray = [];
            angular.forEach(dataTable, function(dataItem, key){
                dataTableArray.push(dataItem);
            }, this);

            // Report Data
            var reportData = {
                //Config
                name: 'Informe Altas Procesos Por Tipo de Operacion',
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
                    {name: 'Tiempo', value: timeLine}
                ],
                charts: [
                    {name: 'Grafica de Sectores', idElement: $scope.pieChartElement, type: 'SVG', flexible: false, minWidth: 1, maxHeight: 150},
                    {name: 'Grafica de Barras', idElement: $scope.barChartElement, type: 'SVG', flexible: false},
                    {name: 'Grafica de Evolucion (Multibar)', idElement: $scope.multibarChartElement, type: 'SVG', flexible: false},
                    {name: 'Grafica de Evolucion (Lineas)', idElement: $scope.operationsTypeLineElement, type: 'SVG', flexible: false}
                ],
                tables: [{
                    name: 'Tabla: Altas por Tipo de Operacion y '+timeLineLabel,
                    columns: columnsTable,
                    data: dataTableArray
                }]
            };
            return reportData;
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

        /** Filter Start Date Watcher */
        $scope.$watch('filter.modeTime', function(){
            if($scope.filter.modeTime && !$scope.loadingConfig){
                $scope.filter.startDate = null;
                $scope.filter.endDate = null;
                $scope.filter.startDateMonth = null;
                $scope.filter.endDateMonth = null;
                $scope.startDateText = null;
                $scope.endDateText = null;
                $scope.startDateMonthText = null;
                $scope.endDateMonthText = null;
            }
        });

        /** Filter Start Date Watcher */
        $scope.$watch('startDate', function(){ $scope.reset(); });

        /** Filter End Date Watcher */
        $scope.$watch('endDate', function(){ $scope.reset(); });

        /** BarChart Collapse/Expand Visual Control */
        $scope.$watch('isBarChartCollapsed', function() {
            if(!$scope.isBarChartCollapsed && $scope.barChart){
                $scope.barChart.update();
            }
        });

        /** PieChart Collapse/Expand Visual Control */
        $scope.$watch('isPieChartCollapsed', function() {
            if(!$scope.isPieChartCollapsed && $scope.pieChart){
                $scope.pieChart.update();
            }
        });


        /** Evolution Line Collapse/Expand Visual Control */
        $scope.$watch('isTypeOperationChartCollapsed', function(){
            if(!$scope.isTypeOperationChartCollapsed && $scope.operationsTypeLineChart){
                $scope.operationsTypeLineChart.update();
            }
        });

        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#onEndDateSet
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
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

        /**
         * @ngdoc method
         * @name bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController#reset
         * @methodOf bbva.or.widgets.ProcesosAltaPorTipoOperacion.controller:ProcesosAltaPorTipoOperacionController
         * @description
         * Method to reset report charts, data and visual controls.
         */
        $scope.reset = function(){

            $scope.isBarChartCollapsed = true;
            $scope.isPieChartCollapsed = true;
            $scope.isTypeOperationChartCollapsed = true;
            $scope.isMultiBarCollapsed = true;

            if($scope.restData){

                $scope.restData = null;

                /* Reset Bar Chart */
                $scope.barChart = null;
                $scope.barChartData = null;
                $scope.resetSGV($scope.barChartElement, $scope.barChartSVG, 150);

                /* Reset Pie Chart */
                $scope.pieChart = null;
                $scope.pieChartData = null;
                $scope.resetSGV($scope.pieChartElement, $scope.pieChartSGV, 150);

                /* Reset Line Evolution Chart */
                $scope.operationsTypeLineChart = null;
                $scope.operationsTypeLineChartData = null;
                $scope.operationsTypeLineChartDates = null;
                $scope.resetSGV($scope.operationsTypeLineElement, $scope.operationsTypeLineSGV, 300);

                /* MultiBar Evolution Chart */
                $scope.multibarChart = null;
                $scope.multibarChartData = null;
                $scope.resetSGV($scope.multibarChartElement, $scope.multibarChartSGV, 150);

                /* Remove Resize Event Listeners */
                $scope.removeAllResizeEventListeners();

                $scope.config.isFilterVisible = true;
            }
        };

    }).controller('ProcesosAltaPorTipoOperacionNVD3EditController', function($scope){

        debugger;
    });
