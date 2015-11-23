'use strict';

angular.module('bbva.or.widgets.ANSReclamaciones', ['bbva.or.db.provider'])
    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('ANSReclamacionesWidget', {
                title: 'ANS Proceso de Reclamaciones',
                description: 'Muestra un el informe ANS para el proceso de Reclamaciones',
                previewImg : 'images/widgets/war.png',
                controller: 'ANSReclamacionesController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/ANSReclamaciones/widget.html',
                config: {
                    titleIcoClass: 'fa-clock-o',
                    hasFilter: false
                }
            });
    }]).
    controller('ANSReclamacionesController', function($q, $http, $rootScope, $timeout, $controller, $scope, $modal, config,
                                                      hierarchyService, chartsService, exportService, uiGridConstants,
                                                      uiGridGroupingConstants){

        /** BBVA CHART BASE CONTROLLER EXTENSION */
        angular.extend(this, $controller('BBVAChartBaseController', {$scope: $scope}));

        $scope.config = config;

        // Time Selection
        $scope.dateFilter = {};
        $scope.$on('timeSelectionChangeEvent', function (event, data) {
            $scope.dateFilter = data.dateFilter;
            if($scope.processDefinition){
                $scope.loadData();
            }
        });

        // Claims Data
        $scope.claimsData = {};

        // Process Definition
        $scope.taskSelected = {};

        // Visual Controls
        $scope.isLoading = true;
        $scope.reportModeView = 'C';
        $scope.$watch('reportModeView', function(){
            if($scope.reportModeView=='C')
                $scope.resizeCharts();


                //TODO..... PARA SOLUCIONAR LA DIRECTIVA DE DASHBOARDcOUNTER
        });

        /* Claims ANS KO */
        $scope.ansKOClaimsCounter = null;
        $scope.ansKOClaimsPercent = null;
        $scope.ansKOClaimsVariation = null;

        /* Claims ANS OK */
        $scope.ansOKClaimsCounter = null;
        $scope.ansOKClaimsPercent = null;
        $scope.ansOKClaimsVariation = null;

        /* ANS EVOLUTION CHART */
        $scope.ansEvolutionChartHeight = 200;
        $scope.ansEvolutionChartElement = 'mcpe1' + $scope.UID;
        $scope.ansEvolutionChartSGV = 'mcpsvg1' + $scope.UID;
        $scope.ansEvolutionChartChart = null;
        $scope.ansEvolutionChartData = null;

        /* TIME LEVELS PROCESS CHART */
        $scope.timeLevelsProcessChartHeight = 200;
        $scope.timeLevelsProcessChartElement = 'mcpe2' + $scope.UID;
        $scope.timeLevelsProcessChartSGV = 'mcpsvg2' + $scope.UID;
        $scope.timeLevelsProcessChartChart = null;
        $scope.timeLevelsProcessChartData = null;

        /* TASKS PROCESS CHART */
        $scope.tasksProcessChartHeight = 200;
        $scope.tasksProcessChartElement = 'mcpe3' + $scope.UID;
        $scope.tasksProcessChartSGV = 'mcpsvg3' + $scope.UID;
        $scope.tasksProcessChart = null;
        $scope.tasksProcessChartData = null;


        /* PROCESS DATA GRID */
        $scope.processGridOpts = {
            //enableFiltering: true,
            //showFilter: false,
            height:200,
            enableColumnResize: true,
            enablePinning: true,
            enableColumnReordering: true,
            enableRowSelection: true,
            treeRowHeaderAlwaysVisible: false,
            enableHighlighting: true,
            showGridFooter: false,
            showColumnFooter: true,
            enableHorizontalScrollbar: 1,
            enableVerticalScrollbar: 1,
            columnDefs: [
                {name: 'day', displayName:'Día', type: 'date', cellFilter: 'date:\' dd of MMMM\'' , pinnedLeft:true, minWidth: 135,
                    cellTemplate: "<div class=\"{{grid.appScope.ansCellStyleClass(row.entity)}}\">{{row.entity.day | date:'dd of MMMM'}}</div>"},
                {name: 'newInstances', displayName:'Rec.Nuevas', type: 'number', minWidth: 135},
                {name: 'inProcess', displayName:'Rec. En Proceso',  type: 'number', minWidth: 135},
                {name: 'endInstances', displayName:'Rec. Finalizadas',  type: 'number', minWidth: 135},
                {name: 'ansOk', displayName:'Rec. En Tiempo',  type: 'number', minWidth: 135, filters:[
                    {condition: uiGridConstants.filter.GREATER_THAN,placeholder: 'Mayor que..'},
                    {condition: uiGridConstants.filter.LESS_THAN,placeholder: 'Menor que..'}
                ]},
                {name: 'ansOkPercent', displayName:'% En Tiempo', type: 'number', cellTemplate: "<div class=\"gridCell\">{{row.entity[\"ansOkPercent\"] ? row.entity[\"ansOkPercent\"] +'%' : ''}}</div>", minWidth: 135},
                {name: 'ansKo', displayName:'Rec. Con Retraso',  type: 'number', minWidth: 135},
                {name: 'ansKoPercent', displayName:'% Con Retraso',  type: 'number', cellTemplate: "<div class=\"{{grid.appScope.ansCellStyleClass(row.entity)}}\">{{row.entity[\"ansKoPercent\"] ? row.entity[\"ansKoPercent\"] +'%' : ''}}</div>", minWidth: 135},
            ]
        };

        /* PHASES DATA GRID */
        $scope.phasesGridOpts = {
            enableFiltering: true,
            enableColumnResize: true,
            enablePinning: true,
            enableColumnReordering: true,
            enableRowSelection: true,
            treeRowHeaderAlwaysVisible: false,
            enableHighlighting: true,
            showGridFooter: false,
            showColumnFooter: true,
            columnDefs: [
                {name: 'phaseName', displayName:'Fase', minWidth: 135, grouping: { groupPriority: 0 }, sort: { priority: 0, direction: 'asc'}, pinnedLeft:true,
                        cellTemplate: '<div><div ng-if="!col.grouping || col.grouping.groupPriority === undefined || col.grouping.groupPriority === null || ( row.groupHeader && col.grouping.groupPriority === row.treeLevel )" class="ui-grid-cell-contents" title="TOOLTIP">{{COL_FIELD CUSTOM_FILTERS}}</div></div>'},

                {name: 'day', displayName:'Día', type: 'date', cellFilter: 'date:\' dd of MMMM\'',  minWidth: 135, sort: { priority: 1, direction: 'asc'},
                    cellTemplate: "<span class=\"{{grid.appScope.ansCellStyleClass(row.entity)}}\">{{row.entity.day | date:'dd of MMMM'}}</span>"},
                {name: 'instances', displayName:'Ejecuciónes Fase', minWidth: 135, type: 'number'},
                {name: 'ansOk', displayName:'Rec. En Tiempo', minWidth: 135, filters:[
                    {condition: uiGridConstants.filter.GREATER_THAN,placeholder: 'Mayor que..'},
                    {condition: uiGridConstants.filter.LESS_THAN,placeholder: 'Menor que..'}
                ]},
                {name: 'ansOkPercent', displayName:'% En Tiempo', type: 'number', cellTemplate: "<span class=\"gridCell\">{{row.entity[\"ansOkPercent\"] ? row.entity[\"ansOkPercent\"] + '%' : '' }}</span>", minWidth: 135},
                {name: 'ansKo', displayName:'Rec. Con Retraso', type: 'number', minWidth: 135},
                {name: 'ansKoPercent', displayName:'% Con Retraso', type: 'number', minWidth: 135, cellTemplate: "<span class=\"{{grid.appScope.ansCellStyleClass(row.entity)}}\">{{row.entity[\"ansKoPercent\"] ? row.entity[\"ansKoPercent\"] + '%' : '' }}</span>"},
            ]
        };


        /* TASK DATA GRID */
        $scope.taskGridOpts = {
            enableFiltering: false,
            enableColumnResize: true,
            enablePinning: true,
            enableColumnReordering: true,
            enableRowSelection: true,
            treeRowHeaderAlwaysVisible: false,
            enableHighlighting: true,
            showGridFooter: false,
            showColumnFooter: true,
            columnDefs: [
                {name: 'taskName', displayName:'Tarea', minWidth: 135, grouping: { groupPriority: 0 }, sort: { priority: 0, direction: 'asc'}, pinnedLeft:true,
                    cellTemplate: '<div><div ng-if="!col.grouping || col.grouping.groupPriority === undefined || col.grouping.groupPriority === null || ( row.groupHeader && col.grouping.groupPriority === row.treeLevel )" class="ui-grid-cell-contents" title="TOOLTIP">{{COL_FIELD CUSTOM_FILTERS}}</div></div>'},
                {name: 'day', displayName:'Día', type: 'date', cellFilter: 'date:\' dd of MMMM\'',  minWidth: 135, sort: { priority: 1, direction: 'asc'},
                    cellTemplate: "<span class=\"{{grid.appScope.ansCellStyleClass(row.entity)}}\">{{row.entity.day | date:'dd of MMMM'}}</span>"},

                {name: 'instances', displayName:'Ejecuciónes Fase', type: 'number', minWidth: 135},
                {name: 'ansOk', displayName:'Rec. En Tiempo', type: 'number', minWidth: 135, filters:[
                    {condition: uiGridConstants.filter.GREATER_THAN,placeholder: 'Mayor que..'},
                    {condition: uiGridConstants.filter.LESS_THAN,placeholder: 'Menor que..'}
                ]},
                {name: 'ansOkPercent', displayName:'% En Tiempo', type: 'number', cellTemplate: "<span class=\"gridCell\">{{row.entity[\"ansOkPercent\"] ? row.entity[\"ansOkPercent\"] + '%' : '' }}</span>", minWidth: 135},
                {name: 'ansKo', displayName:'Rec. Con Retraso', type: 'number', minWidth: 135},
                {name: 'ansKoPercent', displayName:'% Con Retraso', type: 'number', cellTemplate: "<span class=\"{{grid.appScope.ansCellStyleClass(row.entity)}}\">{{row.entity[\"ansKoPercent\"] ? row.entity[\"ansKoPercent\"] + '%' : '' }}</span>", minWidth: 135},
            ]
        };


        $scope.loadData = function(){

            $scope.isLoading = true;

            $scope.reset();

            $timeout(function(){

                var previousMonthMoment = moment($scope.dateFilter);
                previousMonthMoment.subtract(1, 'month');

                var promise1 = chartsService.getAnsClaims($scope.dateFilter.year(),$scope.dateFilter.month() + 1);
                var promise2 = chartsService.getAnsClaims(previousMonthMoment.year(), previousMonthMoment.month() + 1);

                $q.all([promise1, promise2]).then(function(data){

                    $scope.isLoading = false;
                    var claimsData = {month: null, previousMonth: null};

                    if(data.length >= 1 && data[0].length>0) claimsData.month = data[0];
                    if(data.length == 2 && data[1].length>0) claimsData.previousMonth = data[1];

                    $scope.claimsData = claimsData;
                    $scope.processData();
                });

            },10);
        };

        $scope.refreshWidget = function(){
            console.log('refreshWidget..' + $scope.reportModeView);
        };

        /** INIT FUNCTION **/
        $scope.init = function(){

            hierarchyService.getProcessDefinitions().then(function(processDefinition){

                if(processDefinition){
                    $scope.processDefinition = processDefinition[0];
                    $scope.loadData();
                }
            });
        };

        $scope.processData = function(){

            if($scope.claimsData.month){

                // Refresh Chart View
                $scope.refreshChartsView();

                // Generate Process Graph Data
                $scope.generateProcessDiagramData();

                // Refresh DataTables View
                $scope.refreshGridsView();
            }
        };

        $scope.refreshChartsView = function(){

            $scope.refreshEvolutionANSChart();

            $scope.refreshTimeLevelsProcessChart();

            $scope.refreshTasksProcessChart();
        };

        $scope.refreshGridsView = function(){

            $scope.refreshProcessGrid();

            $scope.refreshPhasesGrid();

            $scope.refreshTasksGrid();

            $scope.refreshZonesGrid();
        };


        $scope.refreshProcessGrid = function(){

            // Generate Grid Data
            var processData = [];

            angular.forEach($scope.claimsData.month, function (dailyAnsProcessData, index){
                
                var processDayRowData = {
                    day: new Date(dailyAnsProcessData.date),
                    newInstances: dailyAnsProcessData.processData.daily.newInstances,
                    inProcess: dailyAnsProcessData.processData.daily.openInstances,
                    endInstances: dailyAnsProcessData.processData.daily.closedInstances,
                    ansOk: null,
                    ansOkPercent: null,
                    ansKo: null,
                    ansKoPercent: null
                };

                var ansOk = 0, ansKO = 0;
                angular.forEach($scope.processDefinition.timeIntervalLevels, function (timeInterval, timeIntervalId){
                    if(timeInterval.ansOk){
                        ansOk =  ansOk + dailyAnsProcessData.processData.daily[timeIntervalId];
                    }else{
                        ansKO = ansKO + dailyAnsProcessData.processData.daily[timeIntervalId];
                    }
                });

                processDayRowData.ansOk = ansOk;
                processDayRowData.ansKo = ansKO;

                processDayRowData.ansKoPercent = (ansKO/(ansKO+ansOk) * 100).toFixed(1);
                processDayRowData.ansOkPercent = (ansOk/(ansKO+ansOk) * 100).toFixed(1);

                processData.push(processDayRowData)
            });

            // Refresh Grid
            $scope.processGridOpts.data = processData;
        };

        $scope.refreshPhasesGrid = function(){

            // Generate Grid Data
            var phasesData = [];

            // For every day
            angular.forEach($scope.claimsData.month, function (dailyAnsProcessData, index){

                // For every phase
                angular.forEach(dailyAnsProcessData.phasesData.daily, function (phaseDayData, phaseId){

                    var phase = $scope.processDefinition.phases[phaseId];

                    if(phase){

                        var phaseDayRowData = {
                            day: new Date(dailyAnsProcessData.date),
                            phaseId: phaseId,
                            phaseName: phase.name,
                            instances: null,
                            ansOk: null,
                            ansOkPercent: null,
                            ansKo: null,
                            ansKoPercent: null,
                            D0: null,
                            D1: null,
                            D2: null,
                            D3to5: null,
                            D6to10: null,
                            D20: null
                        };

                        var ansOk = 0, ansKO = 0;
                        angular.forEach(phase.timeIntervalLevels, function (timeInterval, timeIntervalId){
                            if(timeInterval.ansOk){
                                ansOk =  ansOk + phaseDayData[timeIntervalId];
                            }else{
                                ansKO = ansKO + phaseDayData[timeIntervalId];
                            }
                        });

                        phaseDayRowData.ansOk = ansOk;
                        phaseDayRowData.ansKo = ansKO;
                        phaseDayRowData.instances = phaseDayRowData.ansOk + phaseDayRowData.ansKo;

                        phaseDayRowData.ansKoPercent = (ansKO/(ansKO+ansOk) * 100).toFixed(1);
                        phaseDayRowData.ansOkPercent = (ansOk/(ansKO+ansOk) * 100).toFixed(1);

                        phasesData.push(phaseDayRowData)
                    }
                });
            });

            // Refresh Grid
            $scope.phasesGridOpts.data = phasesData;
        };

        $scope.refreshTasksGrid = function(){

            // Generate Grid Data
            var taskData = [];

            // For every day
            angular.forEach($scope.claimsData.month, function (dailyAnsProcessData, index){

                // For every phase
                angular.forEach(dailyAnsProcessData.tasksData.daily, function (taskDayData, taskId){

                    var task = $scope.processDefinition.tasks[taskId];

                    if(task){

                        var taskDayRowData = {
                            day: new Date(dailyAnsProcessData.date),
                            taskId: taskId,
                            taskName: task.name,
                            instances: null,
                            ansOk: null,
                            ansOkPercent: null,
                            ansKo: null,
                            ansKoPercent: null,
                            D0: null,
                            D1: null,
                            D2: null,
                            D3to5: null,
                            D6to10: null,
                            D20: null
                        };

                        var ansOk = 0, ansKO = 0;
                        angular.forEach(task.timeIntervalLevels, function (timeInterval, timeIntervalId){
                            if(timeInterval.ansOk){
                                ansOk =  ansOk + taskDayData[timeIntervalId];
                            }else{
                                ansKO = ansKO + taskDayData[timeIntervalId];
                            }
                        });

                        taskDayRowData.ansOk = ansOk;
                        taskDayRowData.ansKo = ansKO;
                        taskDayRowData.instances = taskDayRowData.ansOk + taskDayRowData.ansKo;

                        taskDayRowData.ansKoPercent = (ansKO/(ansKO+ansOk) * 100).toFixed(1);
                        taskDayRowData.ansOkPercent = (ansOk/(ansKO+ansOk) * 100).toFixed(1);

                        taskData.push(taskDayRowData);
                    }
                });
            });

            // Refresh Grid
            $scope.taskGridOpts.data = taskData;
        };

        $scope.refreshZonesGrid = function(){

        };

        $scope.reset = function(){

            $scope.claimsData = {};

            // TODO DIRECTIVE GRAPH Process Diagram
            /*if ($scope.instance){
                $scope.instance.detachEveryConnection();
                $scope.instance.deleteEveryEndpoint();
                $scope.instance = null;
            }*/

            // Claims ANS KO
            $scope.ansKOClaimsCounter = null;
            $scope.ansKOClaimsPercent = null;
            $scope.ansKOClaimsVariation = null;

            // Claims ANS OK
            $scope.ansOKClaimsCounter = null;
            $scope.ansOKClaimsPercent = null;
            $scope.ansOKClaimsVariation = null;

            /* TODO REINICIAR GRAFICAS DASHBAR COUNTERS
            $scope.resetSGV($scope.newClaimsChartElement, $scope.newClaimsChartSVG, $scope.chartCounterHeight);
            $scope.resetSGV($scope.processClaimsChartElement, $scope.processClaimsChartSVG, $scope.chartCounterHeight);
            $scope.resetSGV($scope.closedClaimsChartElement, $scope.closedClaimsChartSVG, $scope.chartCounterHeight);*/

            $scope.resetSGV($scope.ansEvolutionChartElement, $scope.ansEvolutionChartSGV, $scope.ansEvolutionChartHeight);
            $scope.resetSGV($scope.timeLevelsProcessChartElement, $scope.timeLevelsProcessChartSGV, $scope.timeLevelsProcessChartHeight);
            $scope.resetSGV($scope.tasksProcessChartElement, $scope.tasksProcessChartSGV, $scope.tasksProcessChartHeight);

            /* TODO REINICIAR GRAFICAS DASHBAR COUNTERS
            $scope.resetSGV($scope.tasksProcessChartElement, $scope.tasksProcessChartSGV,  $scope.tasksProcessChartHeight);
            $scope.resetSGV($scope.tasksTimeLevelsChartElement, $scope.tasksTimeLevelsChartSGV, $scope.tasksTimeLevelsChartHeight);
            $scope.resetSGV($scope.tasksAnsEvolutionChartElement, $scope.tasksAnsEvolutionChartSGV, $scope.tasksAnsEvolutionChartHeight);
            $scope.resetSGV($scope.phaseTimeLevelsChartElement, $scope.phaseTimeLevelsChartSGV, $scope.phasesTimeLevelsChartHeight);
            $scope.resetSGV($scope.phaseAnsEvolutionChartElement, $scope.phaseAnsEvolutionChartSGV, $scope.phaseAnsEvolutionChartHeight);*/

            $scope.ansEvolutionChartData= null;
            $scope.timeLevelsProcessChartData= null;
            $scope.tasksProcessChartData= null;

            //Grids
            $scope.processGridOpts.data= [];
            $scope.phasesGridOpts.data= [];
            $scope.taskGridOpts.data= [];

            // TODO REINICIAR DIRECTIVA TASK
            $scope.taskSelected = null;

            // TODO REINICIAR DIRECTIVA PHASE
            $scope.phaseSelected = null;
        };


        $scope.resetTaskInfo = function(){

            // Task & Counters
            $scope.modeView=null;

            $scope.ansOKTaskCounter = null;
            $scope.ansOKTaskVariation = null;
            $scope.ansKOTaskCounter = null;
            $scope.ansKOTaskVariation = null;

            // Task Time Levels
            $scope.tasksTimeLevelsChart = null;
            $scope.tasksTimeLevelsChartData = null;

            // Task Time Levels
            $scope.tasksAnsEvolutionChart = null;
            $scope.tasksAnsEvolutionChartData = null;
        };


        $scope.refreshCounters = function(){

            var lastDayOfMonth = $scope.claimsData.month[$scope.claimsData.month.length-1];
            var lastDayOfPreviousMonth = $scope.claimsData.previousMonth[$scope.claimsData.previousMonth.length-1];

            var koClaimsMonth = 0;
            var okClaimsMonth = 0;

            var koClaimsPreviousMonth = 0;
            var okClaimsPreviousMonth = 0;

            angular.forEach($scope.processDefinition.timeIntervalLevels, function (timeInterval, timeIntervalId){

                var acumInstances = lastDayOfMonth.processData.acums[timeIntervalId];
                var acumInstancesPrevious = lastDayOfPreviousMonth.processData.acums[timeIntervalId];

                if(!timeInterval.ansOk){
                    koClaimsMonth = koClaimsMonth + acumInstances;
                    koClaimsPreviousMonth = koClaimsPreviousMonth + acumInstancesPrevious;
                }else{
                    okClaimsMonth = okClaimsMonth + acumInstances;
                    okClaimsPreviousMonth = okClaimsPreviousMonth + acumInstancesPrevious;
                }
            });

            // ANS Counter
            $scope.ansKOClaimsCounter = koClaimsMonth;
            $scope.ansOKClaimsCounter = okClaimsMonth;

            // ANS Variation
            $scope.ansKOClaimsVariation = (((koClaimsMonth - koClaimsPreviousMonth)/koClaimsPreviousMonth) * 100).toFixed(1);
            $scope.ansOKClaimsVariation = (((okClaimsMonth - okClaimsPreviousMonth)/okClaimsPreviousMonth) * 100).toFixed(1);

            // ANS Percent
            $scope.ansKOClaimsPercent = (koClaimsMonth/(koClaimsMonth+okClaimsMonth) * 100).toFixed(1);
            $scope.ansOKClaimsPercent = (okClaimsMonth/(koClaimsMonth+okClaimsMonth) * 100).toFixed(1);
        };

        $scope.generateProcessDiagramData = function(){

            // Generate Diagram Data
            var lastDayOfMonth = $scope.claimsData.month[$scope.claimsData.month.length-1];

            angular.forEach($scope.processDefinition.tasks, function (task, index){

                var ansOK = null, ansKO = null;

                angular.forEach(task.timeIntervalLevels, function (timeIntervalLevel, key){
                    if (timeIntervalLevel.ansOk){
                        ansOK = ansOK + lastDayOfMonth.tasksData.acums[task.id][key];
                    }else{
                        ansKO = ansKO + lastDayOfMonth.tasksData.acums[task.id][key];
                    }
                });

                var avgTaskTimeInSeconds = lastDayOfMonth.tasksData.acums[task.id].avgTimeTask;

                task.ansData = {
                    ansAvgTime: avgTaskTimeInSeconds,
                    numInstances: ansOK + ansKO,
                    instancesAnsOK: ansOK,
                    instancesAnsKO: ansKO
                };
            });
        };

        $rootScope.$on('phaseSelected', function(event, phaseId) {
            $scope.modeView = 'P';
            $scope.phaseSelected = $scope.processDefinition.phases[phaseId];
        });

        $rootScope.$on('taskSelected', function(event, taskId){
            $scope.modeView = 'T';
            $scope.taskSelected = $scope.processDefinition.tasks[taskId];
        });

        $scope.refreshEvolutionANSChart = function(){

            // Generate Chart Data
            $scope.ansEvolutionChartData = [{
                key:'ANS Cumplido',
                color:'#006799',
                values: [
                    //{x: dateMilisseoncs, y: numInstances}
                ]
            },{
                key:'ANS Incumplido',
                color:'#ff9933',
                values: [
                    //{x: dateMilisseoncs, y: numInstances}
                ]
            }];

            // TODO VERIFICAR ESTOS CALCULOS
            angular.forEach($scope.claimsData.month, function (dailyAnsProcessData, index){

                var ansOk = 0, ansKO = 0;
                angular.forEach($scope.processDefinition.timeIntervalLevels, function (timeInterval, timeIntervalId){
                    if(timeInterval.ansOk){
                        ansOk =  ansOk + dailyAnsProcessData.processData.daily[timeIntervalId];
                    }else{
                        ansKO = ansKO + dailyAnsProcessData.processData.daily[timeIntervalId];;
                    }
                });

                $scope.ansEvolutionChartData[0].values.push({x: dailyAnsProcessData.date, y:ansOk});
                $scope.ansEvolutionChartData[1].values.push({x: dailyAnsProcessData.date, y:ansKO});
            });

            // Refresh Chart
            $scope.ansEvolutionChartChart = nv.addGraph(function(){

                var chart = nv.models.multiBarChart()
                    .groupSpacing(0.2)
                    .margin({left: 30, top:2, right:10, bottom:50})
                    .showYAxis(true)
                    .showXAxis(true)
                    .reduceXTicks(false);

                //chart.multibar.hideable(true);
                chart.xAxis
                    .axisLabel("Día del Mes")
                    .tickFormat(function(d){
                        return d3.time.format('%d')(new Date(d));
                    });

                //chart.yAxis
                    //.axisLabel('Nº Reclamaciones')
                    //.tickFormat(d3.format(',.1f'));

                d3.select('#'+$scope.ansEvolutionChartElement+' svg')
                    .datum($scope.ansEvolutionChartData)
                    .transition()
                    .duration(500)
                    .call(chart);

                $scope.addResizeEventListener(function(){chart.update();});

                chart.dispatch.on('stateChange', function(){chart.update();});

                return chart;
            });
        };

        $scope.refreshTimeLevelsProcessChart = function(){

            var lastDayOfMonth = $scope.claimsData.month[$scope.claimsData.month.length-1];
            $scope.timeLevelsProcessChartData = [{
                key: 'Time Interval Series',
                values: [
                    {label: "D0", value: lastDayOfMonth.processData.acums.D0, color: '#1F77B4'},
                    {label: "D1", value: lastDayOfMonth.processData.acums.D1, color: '#2CA02C'},
                    {label: "D2", value: lastDayOfMonth.processData.acums.D2, color: '#FFCC00'},
                    {label: "D3-5", value: lastDayOfMonth.processData.acums.D3to5, color: '#FF9933'},
                    {label: "D6-10", value: lastDayOfMonth.processData.acums.D6to10, color: '#D62728'},
                    {label: "D10-20", value: lastDayOfMonth.processData.acums.D10to20, color: '#9944BB'},
                    {label: "D20", value: lastDayOfMonth.processData.acums.D20, color: '#000000'}
                ]
            }];

            // Refresh Chart
            $scope.timeLevelsProcessChart = nv.addGraph(function(){

                var chart = nv.models.discreteBarChart()
                    .x(function(d) { return d.label })    //Specify the data accessors.
                    .y(function(d) { return d.value })
                    .staggerLabels(false)    //Too many bars and not enough room? Try staggering labels.
                    .tooltips(false)        //Don't show tooltips
                    .showValues(true) //...instead, show the bar value right on top of each bar.
                    .showYAxis(false)
                    .showXAxis(true)
                    .margin({left: 15, top:15, right:15, bottom:25});

                d3.select('#'+$scope.timeLevelsProcessChartElement+' svg')
                    .datum($scope.timeLevelsProcessChartData)
                    .call(chart);

                var eventListener = function(){chart.update();};

                $scope.addResizeEventListener(function(){chart.update();});
                //chart.dispatch.on('stateChange', function(e) { chart.update()});

                return chart;
            });
        };


        $scope.refreshTasksProcessChart = function(){

            // Generate Data
            var lastDayOfMonth = $scope.claimsData.month[$scope.claimsData.month.length-1];
            $scope.tasksProcessChartData = [{
                key: 'Process Tasks',
                values: []
            }];

            var colorScales = {
                blue:['#015b7e', '#8dd8f8'],
                red: ['#fe2200', '#f8e109'],
                green: ['#005500', '#00de01']
            };

            //Color Scales
            var blueScale = chroma.scale(colorScales.blue).domain([0, Object.keys($scope.processDefinition.tasks).length]);
            var colorIndex = 0;
            angular.forEach($scope.processDefinition.tasks, function (task, taskId){

                var totalTask = 0;
                angular.forEach(task.timeIntervalLevels, function (timeInterval, timeIntervalId){
                    totalTask = totalTask + lastDayOfMonth.tasksData.acums[taskId][timeIntervalId];
                });

                $scope.tasksProcessChartData[0].values.push({
                    label: task.name,
                    value: totalTask,
                    color: blueScale(colorIndex).hex()
                });
                colorIndex = colorIndex + 1;
            });

            // Refresh Chart
            $scope.tasksProcessChart = nv.addGraph(function(){

                var chart = nv.models.discreteBarChart()
                    .x(function(d) { return d.label })    //Specify the data accessors.
                    .y(function(d) { return d.value })
                    .staggerLabels(true)    //Too many bars and  not enough room? Try staggering labels.
                    .tooltips(false)        //Don't show tooltips
                    .showValues(true) //...instead, show the bar value right on top of each bar.
                    .showYAxis(false)
                    .showXAxis(true)
                    .margin({left: 15, top:15, right:15, bottom:40});

                d3.select('#'+$scope.tasksProcessChartElement+' svg')
                    .datum($scope.tasksProcessChartData)
                    .call(chart);

                $scope.addResizeEventListener(function(){chart.update();});

                return chart;
            });
        };

        $timeout(function(){
            $scope.init();
        },10);

    });