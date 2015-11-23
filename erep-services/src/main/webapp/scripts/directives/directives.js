'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.directives
 * @description
 * # bbva.or.directives
 * Module with BBVA Directives.
 */
angular.module('bbva.or.directives', [])

    /**
     * @ngdoc directive
     * @name bbva.or.directives.directive:ngBbvaFilter
     * @element div
     * @restrict EA
     * @scope
     * @description
     *
     * `ngBbvaFilter` is a directive which renders a dashboard with all its
     * components. The directive requires a name attribute. The name of the
     * dashboard can be used to store the model.
     */
    .directive('ngBbvaFilter', function($rootScope, logService, hierarchyService) {

        /**
         * @ngdoc method
         * @name bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController#getFamilies
         * @methodOf bbva.or.widgets.FiltroMaestro.controller:FiltroMaestroController
         * @description
         * Method to get the list of families from process hierarchy
         */
        var loadFamilies = function(){
            var options = [];
            angular.forEach($rootScope.processHierachy, function (family, key) {
                options.push({id: family.family, name: family.family});
            }, this);
            $scope.families = options;
        };

        return {
            //restrict: 'E',
            templateUrl: 'partials/directives/ngBbvaFilter.html',
            link: function($scope, $element) {

                console.log('Scope Directive ID: '+ $scope.$id);

                $scope.loadingConfig = false;

                /* Process Hierarchy Controls */
                $scope.families = null;
                $scope.subfamilies = null;
                $scope.processes = null;
                $scope.typeOperations = null;

                /* Date Controls Options */
                $scope.dayDatePickerOptions = {startView:'day',minView:'day'};
                $scope.monthDatePickerOptions = {startView:'month',minView:'month'};

                /** Fire Filter Change Event */
                if($scope.filterOptions && $scope.filterOptions.masterFilter){
                    $scope.$watch('filter', function(newFilterCriterias, oldFilterCriterias){
                        console.log('filter:change');
                        $rootScope.$broadcast('masterFilter:change', newFilterCriterias);
                    }, true);
                }

                $scope.$watch('filter.idSelectedFamily', function(){

                    var subfamiliesAux = hierarchyService.getSubFamiliesOfFamily($scope.filter.idSelectedFamily);
                    var subfamiliesIds = new Array();

                    angular.forEach(subfamiliesAux, function (subFamily, key) {
                        subfamiliesIds.push(subFamily.subfamily);
                    }, this);

                    $scope.subfamilies = subfamiliesIds;
                    $scope.processes = null;

                    if(!$scope.loadingConfig){
                        $scope.filter.idSelectedSubFamily = null;
                        $scope.filter.idSelectedProcess = null;
                    }
                });

                $scope.$watch('filter.idSelectedSubFamily', function(){
                    

                    if($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily){

                        $scope.processes = hierarchyService.getProcessOfSubfamily($scope.filter.idSelectedFamily, $scope.filter.idSelectedSubFamily);

                        if (!$scope.loadingConfig){
                            $scope.filter.idSelectedProcess = null;
                            $scope.selectedProcess = null;
                        }
                    }
                });

                $scope.$watch('filter.idSelectedProcess', function(){

                    if ($scope.filter.idSelectedFamily && $scope.filter.idSelectedSubFamily
                        && $scope.filter.idSelectedProcess) {

                        $scope.typeOperations = hierarchyService.getOperationsOfProcess($scope.filter.idSelectedFamily,
                            $scope.filter.idSelectedSubFamily, $scope.filter.idSelectedProcess);
                    }

                    if(!$scope.loadingConfig) {
                        $scope.filter.idSelectedOperationType = null;
                        $scope.typeOperations = []
                    }

                });

                $scope.$watch('filter.modeTime', function(){
                    if($scope.filter.modeTime && !$scope.loadingConfig){
                        $scope.chartData = {labels:[], datasets: []};
                        $scope.filter.startDate = null;
                        $scope.filter.endDate = null;
                        $scope.startDateText = null;
                        $scope.endDateText = null;
                    }
                });


                $scope.$watch('filter.startDate', function(){
                    $scope.onStartDateSet();
                });

                $scope.$watch('filter.endDate', function(){
                    $scope.onEndDateSet();
                });

                $scope.$watch('filter.startDateMonth', function(){
                    $scope.onStartDateSet();
                });

                $scope.$watch('filter.endDateMonth', function(){
                    $scope.onEndDateSet();
                });

                $scope.initFilter = function(){

                    if($scope.config){

                        if($scope.config.idSelectedFamily){

                            $scope.filter.idSelectedFamily = $scope.config.idSelectedFamily;
                        }
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
                    }
                };

                $scope.getFamilies = function(){
                    var options = [];
                    angular.forEach($rootScope.processHierachy, function (family, key) {
                        options.push({id: family.family, name: family.family});
                    }, this);
                    scope.families = options;
                };

                /* Controller Watchers */
                $scope.$watch('processHierachy', function(){
                    $scope.families = hierarchyService.getFamilies();
                });

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

                $scope.onEndDateSet = function(){

                    console.log('onEndDateSet');

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

               console.log(' ngBbvaFilter >> directive');
            }
        };
    })

    /**
     * @ngdoc directive
     * @name bbva.or.directives.directive:ngBbvaMenu
     * @element div
     * @restrict EA
     * @scope
     * @description
     * `ngBbvaMenu` is a directive which renders a menu application.
     */
    .directive('ngBbvaMenu', function(logService, $rootScope) {

        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaMenu.html'
        }
    })

    /**
     * @ngdoc directive
     * @name bbva.or.directives.directive:ngBbvaFile
     * @element input
     * @scope
     * @description
     * `ngBbvaFile` is a directive which renders a html file element.
     */
    .directive('ngBbvaFile', function () {
        return {
            scope: true,
            link: function (scope, el, attrs){
                el.bind('change', function (event){
                    var files = event.target.files;
                    for (var i = 0;i<files.length;i++) {
                        scope.$emit("fileSelected", { file: files[i] });
                    }
                });
            }
        };
    })

    /**
     * @ngdoc directive
     * @name bbva.or.directives.directive:ngBbvaProcessGraph
     * @element input
     * @scope
     * @description
     * `ngBbvaProcessGraph` is a directive which renders a process graph by definition.
     */
    .directive('ngBbvaProcessGraph', function ($rootScope, $timeout) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBBVAProcessGraph.html',
            scope: {
                process: '='
            },
            link: function ($scope, $element){

                $scope.processGraphUID = new Date().getTime();

                // Observers
                $scope.$watch('process', function(newVal, oldVal){
                    
                    console.log('********************* PROCESS DEF CHANGE ************************');
                    $scope.renderProcessGraph();
                }, true);


                $scope.resetProcessGraph = function(){
                    // Process Diagram
                    if ($scope.instance){
                        $scope.instance.detachEveryConnection();
                        $scope.instance.deleteEveryEndpoint();
                        $scope.instance = null;
                    }
                };

                $scope.renderProcessGraph = function(){

                    $scope.resetProcessGraph();

                    if($scope.process) {

                        $timeout(function(){
                            // Generate Diagram
                            jsPlumb.ready(function () {

                                $scope.instance = jsPlumb.getInstance({
                                    Connector: "StateMachine",
                                    PaintStyle: {lineWidth: 3, strokeStyle: "#ffa500"},
                                    Endpoint: ["Blank", {radius: 20}], //Por defecto para todo
                                    EndpointStyle: {fillStyle: "#ffa500"},
                                    Endpoints: [["Blank", {radius: 0}], ["Blank", {radius: 0}]],
                                    Container: "processGraph",
                                    ConnectionOverlays: [
                                        ["Arrow", {location: 1}]
                                    ]
                                });

                                var basicType = {
                                    connector: "StateMachine",
                                    paintStyle: {strokeStyle: "red", lineWidth: 4},
                                    hoverPaintStyle: {strokeStyle: "blue"},
                                    overlays: [
                                        "Arrow"
                                    ]
                                };

                                $scope.instance.registerConnectionType("basic", basicType);

                                // TODO DRAGGABLE DISABLED
                                //var shapes = jsPlumb.getSelector(".shape");
                                //$scope.instance.draggable(shapes);

                                $scope.instance.batch(function () {
                                    if ($scope.process && $scope.process.processGraph) {

                                            $scope.refreshTaskConnectionsProcessDiagram();

                                        // Tasks Connections

                                    }
                                });
                                //jsPlumb.fire("jsPlumbDemoLoaded", $scope.instance);
                            });
                        },100);

                    }
                };

                $scope.refreshTaskConnectionsProcessDiagram = function(){

                    // Tasks Connections
                    angular.forEach($scope.process.processGraph.tasks, function (task, index){

                        var sourceTaskContainer = angular.element('#process-task-' + task.id);

                        if(sourceTaskContainer && sourceTaskContainer.length > 0){
                            // TO
                            if(task.to && task.to.length>0){

                                angular.forEach(task.to, function (toTask, index){

                                    var targetTaskContainer = angular.element('#process-task-' + toTask);

                                    if(targetTaskContainer && targetTaskContainer.length > 0){

                                        var anchors = $scope.calculateAnchors(task, toTask);

                                        $scope.instance.connect({
                                            source: sourceTaskContainer[0],
                                            target: targetTaskContainer[0],
                                            anchors:[anchors.sourceAnchor, anchors.targetAnchor]
                                        });
                                    }
                                });
                            }

                            // FROM
                            if(task.to && task.to.length > 0){}
                        }
                    });
                };

                $scope.calculateAnchors = function(task, toTask){

                    var sourceAnchor = '', targetAnchor = '';
                    var targetTask = $scope.process.processGraph.tasks[toTask];

                    if(targetTask.position.y < task.position.y){

                        // X Positions
                        if(targetTask.position.x > task.position.x){
                            sourceAnchor = 'TopRight';
                            targetAnchor = 'BottomLeft';
                        }else if(targetTask.position.x < task.position.x){
                            sourceAnchor = 'TopLeft';
                            targetAnchor = 'BottomRight';
                        }else{
                            sourceAnchor = 'TopCenter';
                            targetAnchor = 'BottomCenter';
                        }

                    }else if(targetTask.position.y > task.position.y){

                        if(targetTask.position.x > task.position.x){
                            sourceAnchor = 'BottomRight';
                            targetAnchor = 'TopLeft';
                        }else if(targetTask.position.x < task.position.x){
                            sourceAnchor = 'BottomLeft';
                            targetAnchor = 'TopRight';
                        }else{
                            sourceAnchor = 'BottomCenter';
                            targetAnchor = 'TopCenter';
                        }

                    }else{

                        if(targetTask.position.x > task.position.x){
                            sourceAnchor = 'RightMiddle';
                            targetAnchor = 'LeftMiddle';
                        }else{
                            sourceAnchor = 'LeftMiddle';
                            targetAnchor = 'RightMiddle';
                        }
                    }

                    return {
                        sourceAnchor: sourceAnchor,
                        targetAnchor: targetAnchor
                    };
                };

                $scope.getTask = function(idTask){

                    if($scope.process && $scope.process.tasks)
                        return $scope.process.tasks[idTask];
                    else
                        return null;
                };

                $scope.getTaskPosition = function(idTask){

                    var task = null;
                    if($scope.process && $scope.process.processGraph)
                        task = $scope.process.processGraph.tasks[idTask];

                    if(task)
                        return 'top:' + task.position.y + 'em; left:'+task.position.x+'em;';
                };

                $scope.getPhaseStyle = function(phase){
                    if(phase && phase.position){
                        return 'top:' + phase.position.y + 'em; left:'+phase.position.x+'em; height:' + phase.height+'em; width:'+phase.width + 'em;';
                    }
                };

                $scope.formatAnstime = function(timeInSeconds, timeUnit){

                    /* TODO.. HUMAN ANS TIME UNIT
                     1: seconds
                     2: minutes
                     3: hours
                     4: days
                     */
                    var humanTime = null;

                    if(timeInSeconds && timeUnit){

                        switch (timeUnit){
                            // In Seconds
                            case 1:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asSeconds() + " seg";
                                break;
                            // In Minutes
                            case 2:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asMinutes() + " min";
                                break;
                            // In Hours
                            case 3:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asHours() + " horas";
                                break;
                            // In days
                            case 4:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asDays() + " días";
                                break;
                        }
                    }
                    return humanTime;
                };
                /**
                 *  TODO This functions is the same as formatAnstime, 
                 *  but it evals the length of the entire part in seconds to transform to hour if it is higher than 4
                 **/
                $scope.formatAnstimeEvalLong = function(timeInSeconds, timeUnit){

                    /* TODO.. HUMAN ANS TIME UNIT
                     1: seconds
                     2: minutes
                     3: hours
                     4: days
                     */
                    var humanTime = null;
                  
                    var entirePartLength = timeInSeconds.toString().substring(0,timeInSeconds.toString().indexOf(".")).length;

                    
                    if(timeInSeconds && timeUnit){

                        switch (timeUnit){
                            // In Seconds
                            case 1:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asSeconds().toFixed(2) + " seg";
                                break;
                            // In Minutes
                            case 2:
                            	if (entirePartLength!= null && entirePartLength != undefined && entirePartLength < 4){
                            	
                            		humanTime = moment.duration(timeInSeconds, 'seconds').asMinutes().toFixed(2) + " min";
                                
                            	}else{
				            		humanTime = moment.duration(timeInSeconds, 'seconds').asHours().toFixed(2) + " horas";
				            	}
                                break;
                            // In Hours
                            case 3:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asHours().toFixed(2) + " horas";
                                break;
                            // In days
                            case 4:
                                humanTime = moment.duration(timeInSeconds, 'seconds').asDays().toFixed(2) + " días";
                                break;
                        }
                    }
                    console.log("Function formatAnstimeEvalLong; time seconds: "+ timeInSeconds + " | length entire part: " + entirePartLength + " | Final value: "+humanTime);
                    return humanTime;
                };

                $scope.selectTask = function(taskId){
                    $rootScope.$emit('taskSelected', taskId);
                };

                $scope.selectPhase = function(phaseId){
                    $rootScope.$emit('phaseSelected', phaseId);
                };
            }
        };
    })
    .directive('ngBbvaTask', function ($rootScope, $window, $timeout) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaTask.html',
            scope: {
                task: '=',
                data: '='
            },
            link: function ($scope, $element){

                $scope.UID = new Date().getTime();

                /* Task Counters */
                $scope.ansOKTaskCounter = null;
                $scope.ansOKTaskVariation = null;
                $scope.ansKOTaskCounter = null;
                $scope.ansKOTaskVariation = null;

                /* TimeLevels Chart */
                $scope.tasksTimeLevelsChartHeight = 200;
                $scope.tasksTimeLevelsChartElement = 'mcpe4' + $scope.UID;
                $scope.tasksTimeLevelsChartSGV = 'mcpsvg4' + $scope.UID;
                $scope.tasksTimeLevelsChart = null;
                $scope.tasksTimeLevelsChartData = null;

                /* ANS Chart */
                $scope.tasksAnsEvolutionChartHeight = 200;
                $scope.tasksAnsEvolutionChartElement = 'mcpe5' + $scope.UID;
                $scope.tasksAnsEvolutionChartSGV = 'mcpsvg5' + $scope.UID;
                $scope.tasksAnsEvolutionChart = null;
                $scope.tasksAnsEvolutionChartData = null;

                // Observers
                $scope.$watch('task', function(newVal, oldVal){ $scope.refresh(); }, true);
                $scope.$watch('data', function(newVal, oldVal){ $scope.refresh(); }, true);

                $scope.refresh = function(){
                    if($scope.task && $scope.data.month){
                        $scope.renderTaskInfo();
                    }else{
                        $scope.resetTaskInfo();
                    }
                };

                $scope.resetTaskInfo = function(){

                    // Task & Counters
                    $scope.modeView = null;
                    $scope.taskSelected = null;
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

                $scope.renderTaskInfo = function(){

                    $scope.refreshTaskCounters();

                    $scope.refreshTimeLevelsTaskChart();

                    $scope.refreshAnsEvolutionTaskChart();

                    $timeout(function(){
                        $('html, body').stop().animate({
                            scrollTop: $('#taskInfoElement').offset().top - 160
                        }, 400);
                    },1);
                };

                $scope.refreshTimeLevelsTaskChart = function(){

                    // Generate Chart Data
                    var lastDayOfMonth = $scope.data.month[$scope.data.month.length-1];

                    $scope.tasksTimeLevelsChartData = [{
                        key: 'Time Interval Series',
                        values: [
                            {label: "D0", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D0, color: '#1F77B4'},
                            {label: "D1", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D1, color: '#2CA02C'},
                            {label: "D2", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D2, color: '#FFCC00'},
                            {label: "D3-5", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D3to5, color: '#FF9933'},
                            {label: "D6-10", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D6to10, color: '#D62728'},
                            {label: "D10-20", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D10to20, color: '#9944BB'},
                            {label: "D20", value: lastDayOfMonth.tasksData.acums[$scope.task.id].D20, color: '#000000'}
                        ]
                    }];

                    // Refresh Chart
                    $scope.tasksTimeLevelsChart = nv.addGraph(function(){

                        var chart = nv.models.discreteBarChart()
                            .x(function(d) { return d.label })
                            .y(function(d) { return d.value })
                            .staggerLabels(true)
                            .tooltips(false)
                            .showValues(true)
                            .showYAxis(false)
                            .showXAxis(true)
                            .margin({left: 15, top:15, right:15, bottom:40});

                        d3.select('#'+$scope.tasksTimeLevelsChartElement+' svg')
                            .datum($scope.tasksTimeLevelsChartData)
                            .call(chart);

                        //$scope.addResizeEventListener(function(){chart.update();});
                        $scope.$watch(function(){return $window.innerWidth;}, function(value){

                            chart.update();
                        });

                        return chart;
                    });
                };


                $scope.refreshAnsEvolutionTaskChart = function(){

                    // Generate Chart Data
                    $scope.tasksAnsEvolutionChartData = [{
                        key:'ANS Cumplido',
                        color:'#006799',
                        values: [//{x: dateMilisseoncs, y: numInstances}
                        ]
                    },{
                        key:'ANS Incumplido',
                        color:'#ff9933',
                        values: [//{x: dateMilisseoncs, y: numInstances}
                        ]
                    }];

                    angular.forEach($scope.data.month, function (dailyAnsProcessData, index){

                        var ansOk = 0, ansKO = 0;
                        angular.forEach($scope.task.timeIntervalLevels, function (timeInterval, timeIntervalId){
                            if(timeInterval.ansOk){
                                ansOk =  ansOk + dailyAnsProcessData.tasksData.daily[$scope.task.id][timeIntervalId];
                            }else{
                                ansKO = ansKO + dailyAnsProcessData.tasksData.daily[$scope.task.id][timeIntervalId];
                            }
                        });

                        $scope.tasksAnsEvolutionChartData[0].values.push({x: dailyAnsProcessData.date, y:ansOk});
                        $scope.tasksAnsEvolutionChartData[1].values.push({x: dailyAnsProcessData.date, y:ansKO});
                    });

                    // Refresh Chart
                    $scope.ansEvolutionChartChart = nv.addGraph(function(){

                        var chart = nv.models.multiBarChart()
                            .groupSpacing(0.2)
                            .margin({left: 30, top:2, right:10, bottom:50})
                            .showYAxis(true)
                            .showXAxis(true)
                            .reduceXTicks(false);

                        chart.xAxis
                            //.showMaxMin(true)
                            .axisLabel("Día del Mes")
                            .tickFormat(function(d){return d3.time.format('%d')(new Date(d)); });

                        //chart.yAxis
                        //.axisLabel('Nº Reclamaciones')
                        //.tickFormat(d3.format(',.1f'));

                        d3.select('#'+$scope.tasksAnsEvolutionChartElement+' svg')
                            .datum($scope.tasksAnsEvolutionChartData)
                            .transition()
                            .duration(500)
                            .call(chart);

                        //$scope.addResizeEventListener(function(){chart.update();});
                        $scope.$watch(function(){return $window.innerWidth;}, function(value){
                            chart.update();
                        });

                        chart.dispatch.on('stateChange', function(e) { chart.update()});

                        return chart;
                    });
                };


                $scope.refreshTaskCounters = function(){

                    var koTaskMonth = null;
                    var okTaskMonth = null;

                    var koTaskPreviousMonth = null;
                    var okTaskPreviousMonth = null;

                    var lastDayOfMonth = $scope.data.month[$scope.data.month.length-1];
                    var lastDayOfPreviousMonth = null;

                    if($scope.data.previousMonth){
                        lastDayOfPreviousMonth = $scope.data.previousMonth[$scope.data.previousMonth.length-1];
                    }

                    angular.forEach($scope.task.timeIntervalLevels, function (timeInterval, timeIntervalId){

                        var acumInstances = lastDayOfMonth.tasksData.acums[$scope.task.id][timeIntervalId];
                        var acumInstancesPrevious = null;

                        if(lastDayOfPreviousMonth)
                            acumInstancesPrevious = lastDayOfPreviousMonth.tasksData.acums[$scope.task.id][timeIntervalId];

                        if(!timeInterval.ansOk){
                            koTaskMonth = koTaskMonth + acumInstances;
                            if(acumInstancesPrevious != null)
                                koTaskPreviousMonth = koTaskPreviousMonth + acumInstancesPrevious;
                        }else{
                            okTaskMonth = okTaskMonth + acumInstances;
                            if(acumInstancesPrevious != null)
                                okTaskPreviousMonth = okTaskPreviousMonth + acumInstancesPrevious;
                        }
                    });

                    // ANS Counter
                    $scope.ansOKTaskCounter = koTaskMonth;
                    $scope.ansKOTaskCounter = okTaskMonth;

                    // ANS Percent
                    $scope.ansKOTaskPercent = (koTaskMonth/(koTaskMonth+okTaskMonth) * 100).toFixed(1);
                    $scope.ansOKTaskPercent = (okTaskMonth/(koTaskMonth+okTaskMonth) * 100).toFixed(1);

                    // ANS Variation
                    $scope.ansKOTaskVariation = null;
                    $scope.ansOKTaskVariation = null;

                    if(koTaskMonth != null && koTaskPreviousMonth != null)
                        $scope.ansKOTaskVariation = (((koTaskMonth - koTaskPreviousMonth)/koTaskPreviousMonth) * 100).toFixed(1);

                    if(okTaskMonth != null && okTaskPreviousMonth != null)
                        $scope.ansOKTaskVariation = (((okTaskMonth - okTaskPreviousMonth)/okTaskPreviousMonth) * 100).toFixed(1);
                };
            }
        }
    })
    .directive('ngBbvaPhase', function ($rootScope, $window, $timeout) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaPhase.html',
            scope: {
                phase: '=',
                data: '='
            },
            link: function ($scope, $element){

                $scope.UID = new Date().getTime();

                /* Phase Counters */
                $scope.ansOKPhaseCounter = null;
                $scope.ansOKPhaseVariation = null;
                $scope.ansKOPhaseCounter = null;
                $scope.ansKOPhaseVariation = null;

                /* TimeLevels Charts */
                $scope.phasesTimeLevelsChartHeight = 200;
                $scope.phaseTimeLevelsChartElement = 'ptc' + $scope.UID;
                $scope.phaseTimeLevelsChartSGV = 'ptc' + $scope.UID;
                $scope.phaseTimeLevelsChart = null;
                $scope.phaseTimeLevelsChartData = null;

                /* ANS Charts */
                $scope.phaseAnsEvolutionChartHeight = 200;
                $scope.phaseAnsEvolutionChartElement = 'paec' + $scope.UID;
                $scope.phaseAnsEvolutionChartSGV = 'paec' + $scope.UID;
                $scope.phaseAnsEvolutionChart = null;
                $scope.phaseAnsEvolutionChartData = null;

                // Observers
                $scope.$watch('phase', function(newVal, oldVal){ $scope.refresh(); }, true);
                $scope.$watch('data', function(newVal, oldVal){ $scope.refresh(); }, true);

                $scope.refresh = function(){

                    if($scope.phase && $scope.data.month){
                        $scope.renderPhaseInfo();
                    }else{
                        $scope.resetPhaseInfo();
                    }
                };

                $scope.resetPhaseInfo = function(){

                    // Phase & Counters
                    $scope.phase = null;
                    $scope.ansOKPhaseCounter = null;
                    $scope.ansOKPhaseVariation = null;
                    $scope.ansKOPhaseCounter = null;
                    $scope.ansKOPhaseVariation = null;

                    // Task Time Levels
                    $scope.phaseTimeLevelsChart = null;
                    $scope.phaseTimeLevelsChartData = null;

                    // Task Time Levels
                    $scope.phaseAnsEvolutionChart = null;
                    $scope.phaseAnsEvolutionChartData = null;
                };

                $scope.renderPhaseInfo = function(){

                    $scope.refreshPhaseCounters();

                    $scope.refreshTimeLevelsPhaseChart();

                    $scope.refreshAnsEvolutionPhaseChart();

                    $timeout(function(){
                        $('html, body').stop().animate({
                            scrollTop: $('#phaseInfoElement').offset().top - 160
                        }, 400);
                    },1);
                };

                $scope.refreshPhaseCounters = function(){

                    var lastDayOfMonth = $scope.data.month[$scope.data.month.length-1];
                    var lastDayOfPreviousMonth = null;
                    if($scope.data.previousMonth)
                        lastDayOfPreviousMonth = $scope.data.previousMonth[$scope.data.previousMonth.length-1];

                    var koPhaseMonth = null;
                    var okPhaseMonth = null;

                    var koPhasePreviousMonth = null;
                    var okPhasePreviousMonth = null;

                    angular.forEach($scope.phase.timeIntervalLevels, function (timeInterval, timeIntervalId){

                        var acumInstances = lastDayOfMonth.phasesData.acums[$scope.phase.id][timeIntervalId];
                        var acumInstancesPrevious = null;

                        if(lastDayOfPreviousMonth)
                            acumInstancesPrevious = lastDayOfPreviousMonth.phasesData.acums[$scope.phase.id][timeIntervalId];

                        if(!timeInterval.ansOk){
                            koPhaseMonth = koPhaseMonth + acumInstances;
                            if(acumInstancesPrevious)
                                koPhasePreviousMonth = koPhasePreviousMonth + acumInstancesPrevious;
                        }else{
                            okPhaseMonth = okPhaseMonth + acumInstances;
                            if(acumInstancesPrevious)
                                okPhasePreviousMonth = okPhasePreviousMonth + acumInstancesPrevious;
                        }
                    });

                    // ANS Counter
                    $scope.ansKOPhaseCounter = koPhaseMonth;
                    $scope.ansOKPhaseCounter = okPhaseMonth;

                    // ANS Variation
                    $scope.ansKOPhaseVariation = null;
                    $scope.ansOKPhaseVariation = null;

                    if(okPhasePreviousMonth!=null && koPhasePreviousMonth!=null){
                        $scope.ansKOPhaseVariation = (((koPhaseMonth - koPhasePreviousMonth)/koPhasePreviousMonth) * 100).toFixed(1);
                        $scope.ansOKPhaseVariation = (((okPhaseMonth - okPhasePreviousMonth)/okPhasePreviousMonth) * 100).toFixed(1);
                    }
                };


                $scope.refreshTimeLevelsPhaseChart = function(){

                    // Generate Chart Data
                    var lastDayOfMonth = $scope.data.month[$scope.data.month.length-1];

                    $scope.phaseTimeLevelsChartData = [{
                        key: 'Time Interval Series',
                        values: [
                            {label: "D0", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D0, color: '#1F77B4'},
                            {label: "D1", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D1, color: '#2CA02C'},
                            {label: "D2", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D2, color: '#FFCC00'},
                            {label: "D3-5", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D3to5, color: '#FF9933'},
                            {label: "D6-10", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D6to10, color: '#D62728'},
                            {label: "D10-20", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D10to20, color: '#9944BB'},
                            {label: "D20", value: lastDayOfMonth.phasesData.acums[$scope.phase.id].D20, color: '#000000'}
                        ]
                    }];

                    // Refresh Chart
                    $scope.phaseTimeLevelsChart = nv.addGraph(function(){

                        var chart = nv.models.discreteBarChart()
                            .x(function(d) { return d.label })
                            .y(function(d) { return d.value })
                            .staggerLabels(true)
                            .tooltips(false)
                            .showValues(true)
                            .showYAxis(false)
                            .showXAxis(true)
                            .margin({left: 15, top:15, right:15, bottom:40});

                        d3.select('#'+$scope.phaseTimeLevelsChartElement+' svg')
                            .datum($scope.phaseTimeLevelsChartData)
                            .call(chart);

                        //$scope.addResizeEventListener(function(){chart.update();});
                        $scope.$watch(function(){return $window.innerWidth;}, function(value){
                            console.log('CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC');
                            chart.update();
                        });

                        return chart;
                    });
                };

                $scope.refreshAnsEvolutionPhaseChart = function(){

                    // Generate Chart Data
                    $scope.phaseAnsEvolutionChartData = [{
                        key:'ANS Cumplido',
                        color:'#006799',
                        values: [//{x: dateMilisseoncs, y: numInstances}
                        ]
                    },{
                        key:'ANS Incumplido',
                        color:'#ff9933',
                        values: [//{x: dateMilisseoncs, y: numInstances}
                        ]
                    }];

                    angular.forEach($scope.data.month, function (dailyAnsProcessData, index){

                        var ansOk = 0, ansKO = 0;
                        angular.forEach($scope.phase.timeIntervalLevels, function (timeInterval, timeIntervalId){
                            if(timeInterval.ansOk){
                                ansOk =  ansOk + dailyAnsProcessData.phasesData.daily[$scope.phase.id][timeIntervalId];
                            }else{
                                ansKO = ansKO + dailyAnsProcessData.phasesData.daily[$scope.phase.id][timeIntervalId];
                            }
                        });

                        $scope.phaseAnsEvolutionChartData[0].values.push({x: dailyAnsProcessData.date, y:ansOk});
                        $scope.phaseAnsEvolutionChartData[1].values.push({x: dailyAnsProcessData.date, y:ansKO});
                    });

                    // Refresh Chart
                    $scope.phaseAnsEvolutionChart = nv.addGraph(function(){

                        var chart = nv.models.multiBarChart()
                            .groupSpacing(0.2)
                            .margin({left: 30, top:2, right:10, bottom:50})
                            .showYAxis(true)
                            .showXAxis(true)
                            .reduceXTicks(false);

                        chart.xAxis
                            //.showMaxMin(true)
                            .axisLabel("Día del Mes")
                            .tickFormat(function(d){return d3.time.format('%d')(new Date(d)); });

                        //chart.yAxis
                        //.axisLabel('Nº Reclamaciones')
                        //.tickFormat(d3.format(',.1f'));

                        d3.select('#'+$scope.phaseAnsEvolutionChartElement+' svg')
                            .datum($scope.phaseAnsEvolutionChartData)
                            .transition()
                            .duration(500)
                            .call(chart);

                        //$scope.addResizeEventListener(function(){chart.update();});
                        $scope.$watch(function(){return $window.innerWidth;}, function(value){
                            chart.update();
                        });
                        chart.dispatch.on('stateChange', function(e) { chart.update()});

                        return chart;
                    });
                };
            }
        }
    })
    .directive('ngBbvaDashboardCounter', function ($rootScope, $window) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaDashboardCounter.html',
            scope: {
                label: '@',
                type: '@', // Types Counters: ['CLAIMS_NEW','CLAIMS_IN_PROCES','CLAIMS_CLOSED']
                data: '='
            },
            link: function ($scope, $element){

                $scope.UID = new Date().getTime();

                $scope.counterChartElement = 'cceid' + $scope.UID;
                $scope.counterChartElementSVG = 'ccsvgid' + $scope.UID;
                $scope.counterChartHeight = 80;

                $scope.counterValue = null;
                $scope.counterVariation = null;
                $scope.counterChart = null;
                $scope.counterChartData = null;

                // Counter Observers
                $scope.$watch('data', function(newVal, oldVal){

                    if($scope.data.month){
                        // Refresh
                        $scope.refreshCounter();
                    }else{
                        // Reset
                        $scope.resetCount();
                    }
                }, true);

                $scope.resetCount = function(){

                    $scope.counterValue = null;
                    $scope.counterVariation = null;
                    $scope.counterChartData = null;
                    $scope.counterChart = null;

                    d3.select('#'+$scope.counterChartElement+' svg').remove();

                    var svg = d3.select('#'+$scope.counterChartElement).append("svg")
                        .attr("id",$scope.counterChartElementSVG)
                        .attr("height",$scope.counterChartHeight);

                };

                $scope.processCounterData = function(){

                    
                    var lastDayOfMonth = $scope.data.month[$scope.data.month.length-1];

                    // Counter Value & Variation
                    $scope.counterValue = $scope.getValueByCounterType(lastDayOfMonth);

                    if($scope.data.previousMonth){
                        var lastDayOfPreviousMonth = $scope.data.previousMonth[$scope.data.previousMonth.length-1];
                        $scope.counterVariation = $scope.getVariationByCounterType(lastDayOfMonth,lastDayOfPreviousMonth);
                    }

                    // Chart Data
                    var chartSerie = {key: $scope.label, values:[]};

                    angular.forEach($scope.data.month, function (dailyAnsProcessData, index){
                        chartSerie.values.push({
                            x: dailyAnsProcessData.date,
                            y: $scope.getInstancesByCounterType(dailyAnsProcessData)
                        })
                    });

                    $scope.counterChartData = [chartSerie];
                };


                $scope.getValueByCounterType = function(lastDayOfMonth){

                    var instances = null;
                    switch ($scope.type){
                        case 'CLAIMS_NEW': // New Claims
                            instances = lastDayOfMonth.processData.acums.newInstances;
                            break;
                        case 'CLAIMS_IN_PROCES': // Claims in Process
                        	console.log("MIRA! - lastDayOfMonth.processData.acums.openInstances =  " +lastDayOfMonth.processData.acums.openInstances);
                            instances = lastDayOfMonth.processData.acums.openInstances;
                            break;
                        case 'CLAIMS_CLOSED': // Closed Claims
                            instances = lastDayOfMonth.processData.acums.closedInstances;
                            break;
                    }
                    return instances;
                };

                $scope.getVariationByCounterType = function(lastDayOfMonth,lastDayOfPreviousMonth){

                    var instances = null;
                    switch ($scope.type){
                        case 'CLAIMS_NEW':
                            instances =  (((lastDayOfMonth.processData.acums.newInstances - lastDayOfPreviousMonth.processData.acums.newInstances)
                                                /lastDayOfPreviousMonth.processData.acums.newInstances) * 100).toFixed(1);
                            break;
                        case 'CLAIMS_IN_PROCES':
                            instances = (((lastDayOfMonth.processData.acums.newInstances - lastDayOfPreviousMonth.processData.acums.openInstances)
                                                /lastDayOfPreviousMonth.processData.acums.newInstances) * 100).toFixed(1);
                            break;
                        case 'CLAIMS_CLOSED':
                            instances = (((lastDayOfMonth.processData.acums.newInstances - lastDayOfPreviousMonth.processData.acums.closedInstances)
                                                /lastDayOfPreviousMonth.processData.acums.newInstances) * 100).toFixed(1);
                            break;
                    }
                    return instances;
                };

                $scope.getInstancesByCounterType = function(dailyAnsProcessData){

                    console.log('ngBbvaDashboardCounter.getInstancesByCounterType()..');
                    var instances = null;
                    switch ($scope.type){
                        case 'CLAIMS_NEW': // New Claims
                            instances = dailyAnsProcessData.processData.daily.newInstances
                            break;
                        case 'CLAIMS_IN_PROCES': // Claims in Process
                            instances = dailyAnsProcessData.processData.daily.openInstances;
                            break;
                        case 'CLAIMS_CLOSED': // Closed Claims
                            instances = dailyAnsProcessData.processData.daily.closedInstances;
                            break;
                    }
                    return instances;
                };


                $scope.refreshCounter = function(){

                    console.log('ngBbvaDashboardCounter.refreshCounter()..');
                    // Generate Counter Data
                    $scope.processCounterData();

                    // Counter Chart Refresh
                    $scope.counterChart = nv.addGraph(function(){

                        var chart = nv.models.multiBarChart()
                            .barColor(['#006799'])
                            .margin({left: 10, top:2, right:10, bottom:5})
                            .showLegend(false)
                            .reduceXTicks(false)
                            .showControls(false)
                            .showYAxis(false)
                            .showXAxis(false)
                            .groupSpacing(0.2);


                        //chart.xAxis.tickFormat(d3.format(',f'));
                        //chart.yAxis.tickFormat(d3.format(',.1f'));

                        chart.xAxis.tickFormat(function(d){
                            var da = new Date(d);
                            console.log(da);
                            return d3.time.format('%d/%m/%Y')(da);
                        });

                        d3.select('#'+$scope.counterChartElement+' svg')
                            .datum($scope.counterChartData)
                            .transition()
                            .duration(500)
                            .call(chart);

                        var resizeChart = function(){ console.log('2222222222222'); chart.update()};

                        //chart.dispatch.on('stateChange', resizeChart);
                        //nv.utils.windowResize(resizeChart);
                        /*$scope.$on('$destroy', function() {
                            
                            window.removeEventListener('resize', resizeChart);
                        });*/

                        angular.element($window).bind('resize', function () {
                           console.log('11111111111111111');
                            resizeChart();
                        });


                        return chart;
                    });
                };
            }
        };
    }).directive('ngBbvaTimeSelection', function ($rootScope, $window) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaTimeSelection.html',
            link: function ($scope, $element) {

                $scope.$watch('moment', function(newValue, oldValue) {
                    console.log('TimeSelection Event: Change..');
                    if(moment.isMoment($scope.moment))
                        $scope.$emit('timeSelectionChangeEvent', {dateFilter: $scope.moment});
                }, true);


                $scope.moment = moment().subtract(3, 'month');
                $scope.monthText = $scope.moment.format('MMMM');
                $scope.year = $scope.moment.year();

                $scope.yearClass = function(year){
                    if($scope.year)
                        return year == $scope.year ? 'active': '';
                };

                $scope.monthClass = function(month){
                    if(moment.isMoment($scope.moment))
                        return month == $scope.moment.month() ? 'active': '';
                };

                $scope.selectMonth = function(month){
                    $scope.moment.month(month);
                    $scope.monthText = $scope.moment.format('MMMM');
                };

                $scope.nextMonth = function(){
                    $scope.moment.add(1, 'month');
                    $scope.monthText = $scope.moment.format('MMMM');
                    $scope.year = $scope.moment.year();
                };

                $scope.previousMonth = function(){
                    $scope.moment.subtract(1, 'month');
                    $scope.monthText = $scope.moment.format('MMMM');
                    $scope.year = $scope.moment.year();
                };

                $scope.selectYear = function(year){
                    $scope.moment.year(year);
                    $scope.year = $scope.moment.year();
                };
            }
    }})
    .directive('ngBbvaGrid', function ($rootScope, $window) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaGrid.html',
            scope: {
                name: '@',
                class: '@',
                options: '='
            },
            link: function ($scope, $element){

                $scope.ansCellStyleClass = function(rowEntity){
                    var ansCell = "gridCell ";
                    if(rowEntity && rowEntity.ansKoPercent && rowEntity.ansKoPercent > 0){
                        if(rowEntity.ansKoPercent<40){
                            ansCell = ansCell + "cellYellow";
                        }else{
                            ansCell = ansCell + "cellRed";
                        }
                    }
                    return ansCell;
                };
            }
        };
    })
    //TODO VAMOS A HACER ESTA DIRECTIVA?
    .directive('ngBbvaAlert', function ($rootScope, $http,$timeout, $templateCache, logService) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaAlert.html',
            scope: {
                alert: '='
            },
            link: function ($scope, $element){

            }
        };
    })
    .directive('ngBbvaTimeFilter', function ($rootScope, $http,$timeout, $templateCache, logService) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngTimeFilter.html',
            scope:{
                options: '='
            },
            link: function ($scope, $element){

                $scope.tmSlider = document.getElementById('timeFilterSlider');
                $scope.tmSliderUpValue = '';
                $scope.tmSliderDownValue = '';

                $scope.startLimit = new Date('2012');
                $scope.endLimit = moment().subtract(1, 'month').toDate();

                noUiSlider.create($scope.tmSlider, {
                    start: [new Date('2013').getTime(), new Date('2014').getTime()],
                    connect: true,
                    behaviour: 'drag',
                    range: {
                        min: $scope.startLimit.getTime(),
                        max: $scope.endLimit.getTime()
                    }
                    /*pips: {
                        mode: 'range',
                        density: 3
                    }
                    format: wNumb({
                        decimals: 0
                    })*/
                    /* range: {
                        'min': 0,
                        'max': 100
                    },
                    pips: {
                        mode: 'positions',
                        values: [0,25,50,75,100],
                        density: 4
                    } */
                });

                // Value PopUps
                debugger;
                var tipHandles = $scope.tmSlider.getElementsByClassName('noUi-handle'),
                    tooltips = [];

                for ( var i = 0; i < tipHandles.length; i++ ){
                    tooltips[i] = document.createElement('div');
                    tipHandles[i].appendChild(tooltips[i]);
                }

                tooltips[0].className += 'noUi-handle-tooltip';
                tooltips[0].innerHTML = '<span></span>';
                tooltips[0] = tooltips[0].getElementsByTagName('span')[0];

                tooltips[1].className += 'noUi-handle-tooltip';
                tooltips[1].innerHTML = '<span></span>';
                tooltips[1] = tooltips[1].getElementsByTagName('span')[0];


                $scope.weekdays = [
                    "Domingo", "Lunes", "Martes",
                    "Miercoles", "Jueves", "Viernes",
                    "Sabado"
                ];

                $scope.months = [
                    "January", "February", "March",
                    "April", "May", "June", "July",
                    "August", "September", "October",
                    "November", "December"
                ];

                $scope.formatDate = function(date){
                    return moment(date).format('L');
                };


                $scope.tmSlider.noUiSlider.on('update', function(values, control){

                    var auxDate = $scope.formatDate(new Date(+values[control]));

                    if(control == 1){
                        $scope.tmSliderUpValue = auxDate;
                    }else{
                        $scope.tmSliderDownValue = auxDate;
                    }
                    if(!$scope.$$phase){
                        $scope.$apply();
                    }
                    tooltips[control].innerHTML = auxDate;

                }, this);

                $scope.timeFilterModes = [
                    {id:'D', name:'Días'},
                    {id:'W', name:'Semanas'},
                    {id:'M', name:'Meses'},
                    {id:'Y', name:'Años'}
                ];
                $scope.timeMode = $scope.timeFilterModes[0];
                $scope.timeInterval = 1;

                $scope.$watch('timeMode', function(newValue, oldValue){
                    $scope.timeInterval = 1;
                }, true);

                $scope.setTimeMode = function(mode){
                    $scope.timeMode = mode;
                };

                $scope.incrementTimeInterval = function() {

                    $scope.timeInterval = ++$scope.timeInterval;
                };
                $scope.decrementTimeInterval = function(){

                    if($scope.timeInterval > 1)
                        $scope.timeInterval = --$scope.timeInterval;
                };
            }
        };
    })
    .directive('ngBbvaMap', function ($rootScope, $http,$timeout, $templateCache, $compile, logService) {
        return {
            restrict: 'EA',
            templateUrl: 'partials/directives/ngBbvaMap.html',
            scope: {
                options: '='
            },
            link: function ($scope, $element){

                $scope.isFullScreen = false;
                $scope.olMapContainerId = 'bbvaMap' + new Date().getTime();
                $scope.olTileLayers = [];
                $scope.olFeatureLayers = {};
                $scope.olOverlaysLayers = {};
                $scope.overlaysLayers = {};
                $scope.overlaysCharts = [];

                // Map Elements Overlay Info
                $scope.scopePopupList = [];
                $scope.featuresClickedList = [];

                // Map Selections
                $scope.mapSelections = [];

                /* $scope.layerVector = new ol.layer.Vector({
                    source: new ol.source.Vector({
                        loader: function(extent) {
                            
                            $.ajax('http://www.ign.es/wfs-inspire/unidades-administrativas',{
                                type: 'GET',
                                data: {
                                    service: 'WFS',
                                    version: '1.1.0',
                                    request: 'GetFeature',
                                    typename: 'au:AdministrativeBoundary',
                                    srsname: 'EPSG:3857',
                                    bbox: extent.join(',') + ',EPSG:3857'
                                }
                            }).done(function(response) {
                                
                                var formatWFS = new ol.format.WFS();
                                $scope.sourceVector.addFeatures(formatWFS.readFeatures(response));
                            });
                        }
                    }),
                    style: new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'rgba(0, 0, 255, 1.0)',
                            width: 2
                        })
                    })
                });

                 var layers = [
                   new ol.layer.Tile({
                        source: new ol.source.MapQuest({layer: 'sat'})
                    }),
                    new ol.layer.Tile({
                        //extent: [-13884991, 2870341, -7455066, 6338219],
                        source: new ol.source.TileWMS({
                            url: 'http://www.ign.es/wms-inspire/ign-base',
                            params: {'LAYERS': 'IGNBaseTodo', 'TILED': true},
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        //extent: [-13884991, 2870341, -7455066, 6338219],
                        source: new ol.source.TileWMS({
                            url: 'http://www.ign.es/wfs-inspire/unidades-administrativas',
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        source: new ol.source.TileJSON({
                            url: 'http://api.tiles.mapbox.com/v3/mapbox.world-light.jsonp'
                        })
                    }),
                    new ol.layer.Tile({
                        source: new ol.source.MapQuest({layer: 'sat'})
                    }),
                    $scope.layerVector,
                    new ol.layer.Vector({
                        source: new ol.source.Vector({
                            url: 'provincias.json',
                            format: new ol.format.TopoJSON()
                        }),
                        style: function(feature, resolution) {
                            return [new ol.style.Style({
                                fill: new ol.style.Fill({
                                    color: 'rgba(255, 255, 255, 0.6)'
                                }),
                                stroke: new ol.style.Stroke({
                                    color: '#cb226d',
                                    width: 1
                                })
                            })];
                        }
                    })
                ];*/

                // Directive Observers
                $scope.$watch('options', function(newValue, oldValue){

                    logService.info('ngBbvaMap > Event > Change > options');

                    if($scope.options != null){
                        // Init Map
                        $scope.initMap();
                    }
                }, true);

                /* OL3 Layers Groups */
                $scope.generateGroupLayer = function(layerGroup){

                    var olLayerGroup = null;
                    if(layerGroup && layerGroup.layers && layerGroup.layers.length > 0){

                        var olLayers = [];

                        angular.forEach(layerGroup.layers, function(layer, key){
                            var olLayer = $scope.generateFeatureLayer(layer);
                            olLayers.push(olLayer)
                        });

                        olLayerGroup = new ol.layer.Group({
                            name: layerGroup.name,
                            visible: layerGroup.visible,
                            layers: olLayers
                        });
                    }
                    return olLayerGroup;
                };

                $scope.addGroupsLayers = function(){

                    logService.info('ngBbvaMap>addGroupsLayers(): ...');

                    if($scope.options.layerGroups && $scope.options.layerGroups.length > 0){

                        angular.forEach($scope.options.layerGroups, function(layerGroup, key){

                            var olGroupLayer = $scope.generateGroupLayer(layerGroup);

                            if(olGroupLayer){
                                $scope.olMap.addLayer(olGroupLayer);
                                $scope.olFeatureLayers[olGroupLayer.get('name')] = olGroupLayer;
                            }
                        });

                    }else{
                        logService.warn('ngBbvaMap>addFeatureLayers(): Config Warn. In map options not exist: <<featuresLayers>>');
                    }
                };


                /* OL3 Map Types */
                //$scope.options.mapTypes = [{}];
                $scope.generateTileLayer = function(tileLayer){

                    var sourceTileLayer = null;

                    if(tileLayer.sourceProperties){
                        sourceTileLayer = new tileLayer.source.prototype.constructor(tileLayer.sourceProperties);
                    }else{
                        sourceTileLayer = new tileLayer.source.prototype.constructor();
                    }

                    return new ol.layer.Tile({
                        id: tileLayer.id,
                        name: tileLayer.name,
                        source: sourceTileLayer,
                        visible: tileLayer.visible
                    });
                };

                $scope.addTileLayers = function(){

                    if($scope.options.tileLayers && $scope.options.tileLayers.length > 0){
                        logService.info('ngBbvaMap>addMapTypesToMap(): ...');
                        angular.forEach($scope.options.tileLayers, function (tileLayer, key){

                            var olTileLayer = $scope.generateTileLayer(tileLayer);

                            $scope.olMap.addLayer(olTileLayer);
                            $scope.olTileLayers.push(olTileLayer);
                        });
                    }else{
                        logService.warn('ngBbvaMap>addTileLayers(): Config Error. In map options not exist: <<tileLayers>>');
                    }
                };

                /* OL3 Map Features */
                $scope.generateFeature = function(mapEntity, featuresLayer){

                    var featureOL3 = new ol.Feature({
                        entity: mapEntity,
                        featureType: featuresLayer.featureType,
                        overlayTemplate: featuresLayer.overlayTemplate,
                        geometry: new ol.geom.Point(ol.proj.transform([mapEntity.long, mapEntity.lat], 'EPSG:4326', 'EPSG:3857')),
                        population: 4000,
                        rainfall: 500
                    });
                    featureOL3.setId(mapEntity.id? mapEntity.id:mapEntity.name);

                    // Feature Icon
                    $scope.addIconFeature(featureOL3, featuresLayer);

                    return featureOL3;
                };

                $scope.addIconFeature = function(feature, featuresLayer){

                    if(featuresLayer.featureIcon){
                        feature.setStyle(new ol.style.Style({
                            image: new ol.style.Icon(({
                                anchor: [0.5, 1],
                                src: featuresLayer.featureIcon
                            }))
                        }));
                    }
                };

                $scope.generateFeatureLayer = function(featuresLayer){

                    var layer = null;
                    if(featuresLayer.source){

                        var sourceProps = {};
                        if(featuresLayer.source.url){
                            sourceProps.url = featuresLayer.source.url;
                        }

                        if(featuresLayer.source.format && featuresLayer.source.format.type){

                            var formatProps = featuresLayer.source.format.argProperties ? featuresLayer.source.format.argProperties : {};

                            sourceProps.format = new ol.format[featuresLayer.source.format.type](formatProps);
                        }

                        var sourceVector = new ol.source.Vector(sourceProps);

                        if(featuresLayer.source.data){

                            angular.forEach(featuresLayer.source.data, function (mapEntity, key){

                                var olFeature = $scope.generateFeature(mapEntity, featuresLayer);
                                if(featuresLayer.id === 'LG_UO_UT'){
                                     //TODO ESTO POR QUE ESTA???:... QUE TENIAS EN MENTE?..
                                }
                                sourceVector.addFeature(olFeature);
                            });
                        }


                        if(featuresLayer.source.clustering){

                            var clusterSource = new ol.source.Cluster({
                                distance: 60,
                                source: sourceVector
                            });

                            layer = new ol.layer.Vector({
                                name: featuresLayer.name,
                                visible: featuresLayer.visible,
                                metaData: featuresLayer.metaData,
                                source: clusterSource,
                                style: function(feature, resolution) {

                                    var size = feature.get('features').length;
                                    if(size > 1){
                                        return [new ol.style.Style({
                                            image: new ol.style.Icon(({
                                                anchor: [0.5, 0.5],
                                                src: featuresLayer.featureIcon
                                            })),
                                            text: new ol.style.Text({
                                                text: size.toString(),
                                                offsetY: -10,
                                                fill: new ol.style.Fill({color: '#FFF'}),
                                                stroke: new ol.style.Stroke({color: '#FFF'})
                                            })
                                        })];

                                    }else{

                                        return [(feature.get('features')[0]).getStyle()];
                                    }
                                }
                            });

                        }else{

                            // TODO FALTA METER LOS ESTILOS POR CONFIGURACION
                            
                            layer = new ol.layer.Vector({
                                name: featuresLayer.name,
                                visible: featuresLayer.visible,
                                metaData: featuresLayer.metaData,
                                source: sourceVector
                            });

                            $scope.addStyleToLayer(featuresLayer.style, layer);
                        }

                        if(featuresLayer.minResolution){ layer.setMinResolution(featuresLayer.minResolution); }
                        if(featuresLayer.maxResolution){ layer.setMaxResolution(featuresLayer.maxResolution);}

                    }else{
                        logService.warn('ngBbvaMap>generateFeatureLayer(): Config Error. In feature layer not exist: <<source>>');
                    }
                    return layer;
                };

                $scope.addStyleToLayer = function(styleConfig, layer){

                    var layerStyle = null,
                        layerStroke = null,
                        layerFill = null;

                    if(styleConfig){

                        if(styleConfig.stroke){
                            layerStroke = new ol.style.Stroke({
                                width: styleConfig.stroke.width ? styleConfig.stroke.width : 1,
                                color: styleConfig.stroke.color ? styleConfig.stroke.color :'#4fadc2'
                            });
                        }

                        if(styleConfig.fill){
                            layerFill = new ol.style.Fill({
                                color: styleConfig.fill.color? styleConfig.fill.color : 'transparent'
                            });
                        }

                        if(layerStroke && layerFill){
                            layerStyle = new ol.style.Style({
                                stroke: layerStroke,
                                fill: layerFill
                            });
                        }else if(layerFill){
                            layerStyle = new ol.style.Style({
                                fill: layerFill
                            });
                        }else if(layerStroke){
                            layerStyle = new ol.style.Style({
                                stroke: layerStroke
                            });
                        }

                        layer.setStyle(layerStyle);
                    }

                };

                $scope.addFeatureLayers = function(){
                   
                   if($scope.options.featuresLayers && $scope.options.featuresLayers.length > 0){
                       logService.info('ngBbvaMap>addFeatureLayers(): ...');

                       angular.forEach($scope.options.featuresLayers, function (featureLayer, key){
                            var olFeatureLayer = $scope.generateFeatureLayer(featureLayer);
                            $scope.olMap.addLayer(olFeatureLayer);
                            $scope.olFeatureLayers[olFeatureLayer.get('name')] = olFeatureLayer;
                        });

                    }else{
                        logService.warn('ngBbvaMap>addFeatureLayers(): Config Warn. In map options not exist: <<featuresLayers>>');
                    }
                };


                $scope.addEventsToMap = function(){

                    /* OL Map Events */
                    $scope.olMap.on('click', function(evt) {

                        var featuresGroups = new Array();
                        this.forEachFeatureAtPixel(evt.pixel, function(feature, layer){
                            featuresGroups.push(feature);
                        });
                        if(featuresGroups.length > 0){
                            $scope.showMapElementsOverlay(featuresGroups);
                        }
                    });

                    $scope.olMap.on('pointermove', function(evt){

                    });

                    $scope.olMap.getView().on('change:resolution', function(evt){
                        
                        var newMapResolution = evt.target.getResolution();
                        $scope.updateOverlayVisibilityByResolution(newMapResolution);
                    });
                };

                $scope.isFeatureClicked = function(feature){

                    if ($scope.featuresClickedList.indexOf(feature.getId()) == -1) {
                        $scope.featuresClickedList.push(feature.getId());
                        return false;
                    }else{
                        return true;
                    }
                };

                $scope.isFeatureClicked = function(feature){

                    if ($scope.featuresClickedList.indexOf(feature.getId()) == -1) {
                        $scope.featuresClickedList.push(feature.getId());
                        return false;
                    } else {
                        return true;
                    }
                };

                $scope.createOverlay = function (coordinates){

                    if(coordinates){

                        var div = document.createElement("div");
                        div.setAttribute("id", new Date().getTime());
                        document.getElementById($scope.olMapContainerId).appendChild(div);

                        var overlayMap = new ol.Overlay({
                            element: div,
                            stopEvent: true
                        });

                        overlayMap.setPosition(coordinates);
                        $scope.olMap.addOverlay(overlayMap);

                        return overlayMap;
                    }
                };


                $scope.createPopover = function (popUpScope, overlayMap){

                    //PopOver Template Content
                    if(!$templateCache.get('mapOverlay.html')){
                        $templateCache.put('mapOverlay.html', '<map-overlay></map-overlay>');
                    }

                    //popover
                    $(overlayMap).popover({
                        'placement': 'bottom',
                        'animation': true,
                        'html': true,
                        'content': $compile($templateCache.get("mapOverlay.html"))(popUpScope)
                    });
                    $(overlayMap).popover('show');
                };

                $scope.createScopeFeaturePopUp = function (overlay, feature) {

                    var popupScope = $scope.$new(true);
                    popupScope.overlay = overlay;
                    popupScope.entity = feature.get('entity');
                    popupScope.overlayTemplate = feature.get('overlayTemplate');

                    $scope.scopePopupList.push(popupScope);

                    popupScope.close = function () {

                        $(popupScope.overlay.overlayMap).popover('destroy');
                        $(popupScope.overlay.overlayMap.id).remove();
                        angular.element(document.getElementById(popupScope.overlay.overlayMap.id).parentNode).remove();
                        $scope.featuresClickedList.splice($scope.featuresClickedList.indexOf(popupScope.overlay.features[0].featureInfo.id),1);
                        $scope.scopePopupList.splice($scope.scopePopupList.indexOf(popupScope),1);
                        popupScope.$destroy();
                    };

                    return popupScope;
                };


                $scope.showFeatureOverlay = function(feature){

                    //open layers overlay
                    var overlayMap = $scope.createOverlay(feature.getGeometry().getCoordinates()).getElement();

                    //object with the popup info
                    var featureAux = {
                        feature     : feature,
                        featureInfo : feature.get('entity')
                    };

                    var overlay = {
                        features   : new Array(),
                        overlayMap : overlayMap
                    };

                    overlay.features.push(featureAux);

                    //popup scope
                    var popupScope = $scope.createScopeFeaturePopUp(overlay, feature);

                    //popover
                    $scope.createPopover(popupScope, overlayMap);

                    //refresh scope
                    popupScope.$apply();
                };


                $scope.createScopeOverlayPopUp = function (overlay, overlayOverlay){

                    var popupScope = $scope.$new(true);
                    popupScope.overlay = overlay;
                    popupScope.overlayMap = overlayOverlay;
                    popupScope.entity = overlay.data;
                    popupScope.overlayTemplate = overlay.metaData.overlayTemplate;

                    $scope.scopePopupList.push(popupScope);

                    popupScope.close = function(){

                        $(popupScope.overlay).popover('destroy');
                        $(popupScope.overlayMap.id).remove();
                        angular.element(document.getElementById(popupScope.overlayMap.id).parentNode).remove();
                        //$scope.featuresClickedList.splice($scope.featuresClickedList.indexOf(popupScope.overlay.features[0].featureInfo.id),1);
                        $scope.scopePopupList.splice($scope.scopePopupList.indexOf(popupScope),1);
                        popupScope.$destroy();
                    };
                    return popupScope;
                };


                $scope.showOverlayPopUp = function(olOverlay){

                    // open layers overlay
                    var overlayMap = $scope.createOverlay(olOverlay.getPosition()).getElement();

                    // popup scope
                    var popupScope = $scope.createScopeOverlayPopUp(olOverlay, overlayMap);

                    // popover
                    $scope.createPopover(popupScope, overlayMap);

                    // refresh scope
                    popupScope.$apply();
                };


                $scope.showMapElementsOverlay = function(featuresGroups){

                    var features;
                    if(featuresGroups[0].getProperties().features){
                        features = featuresGroups[0].getProperties().features;
                    }

                    if(features){
                        if(features.length > 1){
                            // Multiple Map Elements
                            $scope.zoomToCoordinates(features[0].getGeometry().getCoordinates(), 0.5);

                        }else{
                            // One Map Element
                            if(!$scope.isFeatureClicked (features[0])){
                                $scope.showFeatureOverlay(features[0]);
                            }
                        }
                    }
                };


                $scope.addMapInteractions = function(){

                    // Hover Interaction
                    $scope.mapHoverInteraction = new ol.interaction.Select({
                        condition: ol.events.condition.pointerMove
                    });

                    /*$scope.mapHoverInteraction.on('click', function(e){
                        debugger;
                        logService.info('ngBbvaMap > Event: Features Hover: Length=' + e.target.getFeatures().getLength());
                        logService.info('ngBbvaMap > Event: Features Selected: Length=' + e.target.getFeatures().getLength());
                        logService.info('ngBbvaMap  >Event: Features Deselected: Length=' + e.deselected.length);
                    });
                    $scope.olMap.addInteraction($scope.mapHoverInteraction);*/

                    // Mouse Wheel Interaction
                    $scope.mapMouseWheelZoomInteraction = new ol.interaction.MouseWheelZoom({});
                    $scope.mapMouseWheelZoomInteraction.on('active', function(e){ });
                    $scope.olMap.addInteraction($scope.mapMouseWheelZoomInteraction);

                    // Click Interaction
                    $scope.selectClick = new ol.interaction.Select({
                        condition: ol.events.condition.click
                        /*style: new ol.style.Style({
                            stroke: new ol.style.Stroke({
                                color: '#FF8800',
                                width: 2
                            })
                        })*/
                    });

                    $scope.selectClick.getFeatures().on('change:length', function(evt){

                        var selections = [];
                        var selectedFeatures = evt.target.getArray();

                        if(selectedFeatures.length !== 0){
                            angular.forEach(selectedFeatures, function (selectedFeature, key){
                                selections.push({
                                    id: selectedFeature.getId(),
                                    type: selectedFeature.get('type')
                                });
                            }, this);
                        }
                        $scope.mapSelections = selections;

                    });

                    $scope.$watch('mapSelections', function(){

                        if($scope.mapInitiated){
                            $rootScope.$broadcast('map:selectedFeatures', $scope.mapSelections);
                        }
                    });


                    $scope.selectClick.on('click', function(e){
                        debugger;
                        logService.info('ngBbvaMap > Event: Features Hover: Length=' + e.target.getFeatures().getLength());
                        logService.info('ngBbvaMap > Event: Features Selected: Length=' + e.target.getFeatures().getLength());
                        logService.info('ngBbvaMap  >Event: Features Deselected: Length=' + e.deselected.length);
                    });
                    $scope.olMap.addInteraction($scope.selectClick)
                };

                $scope.initMap = function(){

                    logService.info('ngBbvaMap>initMap()...');

                    if(document.fullscreenEnabled || document.webkitFullscreenEnabled
                        || document.mozFullScreenEnabled ||document.msFullscreenEnabled){

                        $scope.isFullScreenAvaible = true;
                    }else{
                        $scope.isFullScreenAvaible = false;
                    }

                    $scope.olTileLayers = [];
                    $scope.olFeatureLayers = {};
                    $scope.overlaysLayers = angular.copy($scope.options.overlaysLayers);

                    // Map Init Config
                    var initCenter = null,
                        initZoom = 8;

                    if($scope.options.center){
                        initCenter = ol.proj.transform([$scope.options.center.lon, $scope.options.center.lat], 'EPSG:4326', 'EPSG:3857');
                    }else{
                        initCenter = ol.proj.transform([-3.21, 39], 'EPSG:4326', 'EPSG:3857');
                    }

                    if($scope.options.zoom){
                        initZoom = $scope.options.zoom;
                    }

                    // OL3 Map Instance
                    $scope.olMap = new ol.Map({
                        controls: [
                           /*new ol.control.MousePosition({
                                coordinateFormat: ol.coordinate.createStringXY(4),
                                projection: 'EPSG:4326',
                                // comment the following two lines to have the mouse position
                                // be placed within the map.
                                className: 'custom-mouse-position',
                                target: document.getElementById('mouse-position'),
                                undefinedHTML: '&nbsp;'
                            })*/
                        ],
                        layers:[ ],
                        target: $scope.olMapContainerId,
                        view: new ol.View({
                            center: initCenter,
                            zoom: initZoom
                        })
                    });

                    $scope.addEventsToMap();

                    // Tile Layers
                    $scope.addTileLayers();

                    // Groups Layers
                    $scope.addGroupsLayers();

                    // Features Layers
                    $scope.addFeatureLayers();

                    // Overlays Layers
                    $scope.addOverlaysLayers();

                    // Map Interactions
                    $scope.addMapInteractions();

                    $scope.mapInitiated = true;
                };

                // OL OVERLAYS LAYERS
                $scope.addOverlayContainer = function(overlayData, overlaysLayer){

                    // Chart Container
                    var elem = document.createElement('div');
                    elem.setAttribute('id', 'olov' + overlaysLayer.metaData.overlayType + overlayData.name);
                    elem.setAttribute('class', 'mapOverlay');

                    elem.addEventListener("mousewheel", function(evt){

                        var coord = $scope.olMap.getEventCoordinate(evt);

                        var delta = evt.deltaY;
                        if(delta>0){
                            $scope.zoomToCoordinates(coord, 2);
                        }else{
                            $scope.zoomToCoordinates(coord, 0.5);
                        }
                    }, false);

                    // OVERLAY NAME

                    return elem;
                };

                $scope.generateOverlaysLayer = function(overlaysLayer){

                    var olOverlays = null;
                    if(overlaysLayer.metaData && overlaysLayer.data){

                        olOverlays = {};
                        angular.forEach(overlaysLayer.data, function (overlayData, key){

                            var elem = $scope.addOverlayContainer(overlayData, overlaysLayer);

                            var olOverlay = new ol.Overlay({
                                element: elem,
                                position: ol.proj.transform([overlayData.long, overlayData.lat], 'EPSG:4326', 'EPSG:3857'),
                                positioning: 'center-center'
                            });

                            olOverlay.metaData =  overlaysLayer.metaData;
                            olOverlay.data = overlayData;

                            olOverlays[olOverlay.data.name] = olOverlay;
                            ///olOverlays.push(olOverlay);

                        }, this);
                    }
                    return olOverlays;
                };

                $scope.addOverlaysToMap = function(overlayLayer){

                    if(overlayLayer){

                        var olOverlayLayer = $scope.olOverlaysLayers[overlayLayer.metaData.overlayType];

                        if(olOverlayLayer){

                            angular.forEach(olOverlayLayer, function (olOverlay, key){

                                $scope.olMap.addOverlay(olOverlay);

                                var overayMustBeVisible = $scope.isOverlayVisibilityByResolution(overlayLayer, $scope.olMap.getView().getResolution());

                                if(overayMustBeVisible){
                                    $scope.renderOverlayContent(olOverlay);
                                }
                            }, this);

                            $scope.olMap.render();
                        }
                    }
                };

                /* metaData.overlayType: 'ANS',
                    metaData: {
                         infoType: 'C', // C = CHART
                        chartType: 'P', // P = Pie Chart,
                        chartSeries: [{
                        name: 'En tiempo',
                        keyProperty : 'ansOK'
                    },{
                        name: 'Con Retraso',
                        keyProperty : 'ansKO'
                    }]
                }, */

                $scope.resetOverlayContent = function(olOverlay){
                    debugger;
                    var overlayEle = olOverlay.get('element');
                    while (overlayEle.firstChild){
                        overlayEle.removeChild(overlayEle.firstChild);
                    }
                };

                $scope.renderOverlayContent = function(olOverlay){

                    //$scope.resetOverlayContent(olOverlay);
                    if(!olOverlay.get('isRender')){
                        if(olOverlay.metaData && olOverlay.data){
                            // Charts
                            if(olOverlay.metaData.infoType == 'C'){

                                //$timeout(function(){
                                $scope.renderOverlayChart(olOverlay);
                                //},100);
                            }
                        }
                        olOverlay.set('isRender', true);
                    }
                };

                $scope.prepareOverlayChart = function(olOverlay){

                    var idOverlayContainer = olOverlay.getElement().getAttribute('id');
                    var overlayContainer = document.getElementById(idOverlayContainer);

                    var divTitle = document.createElement("div");
                    divTitle.setAttribute('class', 'mapOverlayTitle');
                    //TODO divTitle.innerHTML = olOverlay.metaData.overlayType +'-'+olOverlay.data.description;
                    divTitle.innerHTML = '<span>'+olOverlay.data.description+'</span>';
                    overlayContainer.appendChild(divTitle);

                    // SVG Chart
                    var svg = document.createElementNS('http://www.w3.org/2000/svg','svg');
                    svg.setAttributeNS(null,'height','120px');
                    svg.setAttributeNS(null,'width','120px');
                    svg.setAttributeNS(null,'style','overflow: visible;');
                    overlayContainer.appendChild(svg);
                };

                $scope.renderOverlayChart = function(olOverlay){

                    $scope.prepareOverlayChart(olOverlay);

                    switch (olOverlay.metaData.chartType) {
                        case "P":  // Pie Chart
                            $scope.renderOverlayPieChart(olOverlay);
                            break;
                        case "M":  // Multibar Chart
                            $scope.renderOverlayMultiBarChart(olOverlay);
                            break;
                    }
                };

                /**
                 * metaData: {
                        infoType: 'C', // C = CHART
                        chartType: 'P', // P = Pie Chart,
                        chartSeries: [{
                            name: 'En tiempo',
                            keyProperty : 'ansOK'
                        },{
                            name: 'Con Retraso',
                            keyProperty : 'ansKO'
                        }]
                    },
                 data: [{
                        name: 'ANS_1',
                        lat: 42.85,
                        long: -8,
                        ansOK: 50,
                        ansKO: 20
                    },{
                        name: 'ANS_2',
                        lat: 41.87,
                        long: 1.5,
                        ansOK: 30,
                        ansKO: 10
                    }]
                 * @param olOverlay
                 */
                $scope.renderOverlayPieChart = function(olOverlay){

                    // Generate Pie Data
                    var chartData = [];
                    angular.forEach(olOverlay.metaData.chartSeries, function(serieData){
                        chartData.push({
                            label: serieData.name,
                            value: olOverlay.data[serieData.keyProperty]
                        });
                    });

                    // Generate Chart
                    var overlayPieChart = nv.addGraph(function() {

                        var chart = nv.models.pieChart()
                            .x(function(d) { return d.label })
                            .y(function(d) { return d.value })
                            .tooltips(false)
                            .margin({left: 2, top:2, right:2, bottom:2})
                            //.labelThreshold(.3)
                            .labelType("percent")
                            .showLabels(true)
                            .showLegend(false);

                        var idOverlayContainer = olOverlay.getElement().getAttribute('id');

                        chart.pie.dispatch.on("elementClick", function(e){
                            $scope.showOverlayPopUp(olOverlay);
                        });

                        d3.select('#'+ idOverlayContainer +' svg')
                            .datum(chartData)
                            .transition()
                            .duration(500)
                            .call(chart);

                        return chart;
                    });

                    $scope.overlaysCharts.push(overlayPieChart);
                };


                $scope.renderOverlayMultiBarChart = function(olOverlay){

                    // Generate Pie Data
                    var chartData = {
                        key: olOverlay.name,
                        values: []
                    };

                    angular.forEach(olOverlay.metaData.chartSeries, function(serieData){
                        chartData.values.push({
                            label: serieData.name,
                            value: olOverlay.data[serieData.keyProperty]
                        });
                    });

                    // Generate Chart
                    var overlayBarChart = nv.addGraph(function(){

                        var chart = nv.models.discreteBarChart()
                            .x(function(d) { return d.label })
                            .y(function(d) { return d.value })
                            .tooltips(false)
                            .margin({left: 2, top:22 , right:2, bottom:2})
                            .staggerLabels(true)
                            .tooltips(false)
                            .showYAxis(false)
                            .showXAxis(true)
                            .showValues(true);

                        /*chart.xAxis
                            .axisLabel('Tipos de Operación')
                            .axisLabelDistance(5);
                        chart.yAxis
                            .axisLabel('Nº Altas');*/

                        var idOverlayContainer = olOverlay.getElement().getAttribute('id');

                        d3.select('#'+ idOverlayContainer +' svg')
                            .datum([chartData])
                            .transition()
                            .duration(3000)
                            .call(chart);
                        return chart;

                    });
                };

                $scope.addOverlaysLayers = function(){

                    if($scope.options.overlaysLayers && $scope.options.overlaysLayers.length > 0){

                        logService.info('ngBbvaMap > addOverlaysLayers(): ...');

                        angular.forEach($scope.options.overlaysLayers, function (overlayLayer, key){
                            
                            var olOverlays = $scope.generateOverlaysLayer(overlayLayer);

                            if(olOverlays){

                                $scope.olOverlaysLayers[overlayLayer.metaData.overlayType] = olOverlays;

                                if(overlayLayer.visible){
                                    $scope.addOverlaysToMap(overlayLayer);
                                }
                            }
                        });
                    }else{
                        logService.warn('ngBbvaMap>addFeatureLayers(): Config Warn. In map options not exist: <<featuresLayers>>');
                    }
                };


                $scope.isOverlayVisibilityByResolution = function(overlayLayer, mapResolution){

                    var overayMustBeVisible = true;

                    if(overlayLayer.minResolution && overlayLayer.minResolution > mapResolution){
                        overayMustBeVisible = false;
                    }
                    if(overlayLayer.maxResolution && overlayLayer.maxResolution < mapResolution){
                        overayMustBeVisible = false;
                    }
                    return overayMustBeVisible;
                };


                $scope.updateOverlayVisibilityByResolution = function(mapResolution){
                    
                    angular.forEach($scope.overlaysLayers, function (overlayLayer, key){

                        if(overlayLayer.visible){

                            var overayMustBeVisible = $scope.isOverlayVisibilityByResolution(overlayLayer, mapResolution);

                            var olOverlaysLayer = $scope.olOverlaysLayers[overlayLayer.metaData.overlayType];
                            var olOverlay = olOverlaysLayer[Object.keys(olOverlaysLayer)[0]];

                            var isOverlaysOfLayerVisible = olOverlay.getMap()!=null ? true : false;

                            if(overayMustBeVisible && !isOverlaysOfLayerVisible){
                                $scope.addOverlaysToMap(overlayLayer);

                            }else if(!overayMustBeVisible && isOverlaysOfLayerVisible){
                                $scope.removeOverlaysToMap(overlayLayer);
                            }
                        }
                        //overlayLayer.visible = overayMustBeVisible;

                    },this);
                };

                $scope.toogleOverlayLayerVisibility = function(overlayLayer){
                    
                    overlayLayer.visible = !overlayLayer.visible;

                    if(overlayLayer.visible){
                        $scope.addOverlaysToMap(overlayLayer);
                    }else{
                        $scope.removeOverlaysToMap(overlayLayer);
                    }
                };

                $scope.removeOverlaysToMap = function(overlayLayer){

                    angular.forEach($scope.olOverlaysLayers[overlayLayer.metaData.overlayType], function (overlay, key){
                        
                        $scope.olMap.removeOverlay(overlay);

                        /*var myEl = angular.element( document.querySelector( '#divID' ) );
                        myEl.remove();
                        sSASaS?:A*/
                    });
                    $scope.olMap.render();
                };

                /* TILE LAYERS */
                $scope.showTileLayer = function(tileLayer){

                    tileLayer.setVisible(true);
                    angular.forEach($scope.olTileLayers, function(tileLayerAux,key){
                        if(tileLayerAux.get('id') !== tileLayer.get('id')){
                            tileLayerAux.setVisible(false);
                        }
                    }, this);
                };

                $scope.hideTileLayer = function(tileLayer){
                    logService.info('ngBbvaMap>hideTileLayer()..');
                    angular.forEach($scope.olTileLayers, function(tileLayerAux,key){
                        if(tileLayerAux.get('id') === tileLayer.get('id')) {
                            tileLayer.setVisible(false);
                        }
                    }, this);
                };

                $scope.toogleTileLayer = function(tileLayer){
                    logService.info('ngBbvaMap>toogleTileLayer()..');
                    if(!tileLayer.getVisible()){
                        $scope.showTileLayer(tileLayer);
                    }else{
                        $scope.hideTileLayer(tileLayer);
                    }
                };

                $scope.toogleFullScreen = function(){

                    $scope.isFullScreen = !$scope.isFullScreen;

                    var mapContainer = document.getElementById($scope.olMapContainerId);

                    if($scope.isFullScreen){
                        // Go Full Screen
                        if (mapContainer.requestFullscreen){ mapContainer.requestFullscreen();}
                        else if (mapContainer.webkitRequestFullscreen) {mapContainer.webkitRequestFullscreen();}
                        else if (mapContainer.mozRequestFullScreen) {mapContainer.mozRequestFullScreen();}
                        else if (mapContainer.msRequestFullscreen) {mapContainer.msRequestFullscreen();}
                    }else{
                        // Exit Full Screen
                        if (document.exitFullscreen) { document.exitFullscreen(); }
                        else if (document.webkitExitFullscreen) { document.webkitExitFullscreen(); }
                        else if (document.mozCancelFullScreen) { document.mozCancelFullScreen(); }
                        else if (document.msExitFullscreen) { document.msExitFullscreen(); }
                    }
                };

                $scope.zoomIn = function(){
                    console.log('Map: Zoom In...');
                    $scope.setResolution(0.5);
                };

                $scope.zoomOut = function(){
                    console.log('Map: Zoom Out...');
                    $scope.setResolution(2);
                };

                $scope.setResolution = function(factor){
                    var mapResolution = $scope.olMap.getView().getResolution();
                    var panAnimation = ol.animation.pan({duration: 500, source: $scope.olMap.getView().getCenter()});
                    var zoomAnimation = ol.animation.zoom({duration: 500, resolution: mapResolution});
                    $scope.olMap.beforeRender(panAnimation , zoomAnimation);
                    $scope.olMap.getView().setResolution(mapResolution * factor);
                };

                $scope.zoomToCoordinates = function(location, zoomFactor){
                    
                    if(location){

                        if(!zoomFactor)
                            zoomFactor = 2;

                        var panAnimation = ol.animation.pan({
                            duration: 500,
                            source: $scope.olMap.getView().getCenter()
                        });
                        var zoomAnimation = null;
                        if(zoomFactor){
                            zoomAnimation = ol.animation.zoom({duration: 500, resolution: $scope.olMap.getView().getResolution()});
                        }
                        if(panAnimation && zoomAnimation){
                            $scope.olMap.beforeRender(panAnimation, zoomAnimation);
                        }else{
                            $scope.olMap.beforeRender(panAnimation);
                        }
                        $scope.olMap.getView().setCenter(location);
                        $scope.olMap.getView().setResolution($scope.olMap.getView().getResolution() * zoomFactor);
                    }
                };


                $scope.photoMap = function(){

                    var element = document.getElementById($scope.olMapContainerId);
                    html2canvas(element).then(function(canvas){
                        //document.body.appendChild(canvas);
                        var exportContainer = document.getElementById('exportMap' + $scope.olMapContainerId);
                        exportContainer.href = canvas.toDataURL('image/png');
                        exportContainer.click();
                    });
                    $scope.olMap.renderSync();
                };
            }
        };
    })
    .directive('mapOverlay', function($compile, $templateCache) {

        return {
            restrict: 'E',
            replace: true,
            link : function (scope, element, attrs) {

                var el = $compile($templateCache.get(scope.overlayTemplate))(scope);
                element.html(el);
            }
        };
    });