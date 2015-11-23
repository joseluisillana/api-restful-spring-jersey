'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.widgets.ProcessInstancesMonitor
 * @description
 * Module Report: "Process Monitor"
 */
angular.module('bbva.or.widgets.ProcessInstancesMonitor', ['bbva.or.db.provider'])
  .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('ProcessInstancesMonitorWidget', {
                title: 'Monitor de Procesos',
                description: 'Muestra un informe con la información mas relevante de los procesos.',
                previewImg : 'images/widgets/w2.png',
                controller: 'ProcessInstancesMonitorController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/ProcessInstancesMonitor/widget.html',
                edit: {
                    templateUrl: 'partials/widgets/ProcessInstancesMonitor/edit.html',
                    reload: true,
                    controller: 'ProcessInstancesMonitorEditController'
                }
            });
    }]).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.ProcessInstancesMonitor.controller:ProcessInstancesMonitorController
     * @description
     * Controller for Module Report: "Process Monitor"
     */
    controller('ProcessInstancesMonitorController', function($http, $rootScope, $scope, $modal, config, chartsService, exportService){

        $scope.containers = {
            newInstances:{id:'newProcessInstancesContainer'},
            endInstances:{id:'endProcessInstancesContainer'}
        };

        /* Prepare ChartData Familias */
        var newInstances = {
            total: 0,
            data:[
                //{"index":0.3, "value":17436920, "fill":"#006799", "label":"WebMd Health"},
            ]
        };

        var endInstances = {
            total: 0,
            data:[
                //{"index":0.3, "value":17436920, "fill":"#006799", "label":"WebMd Health"},
            ]
        };

        var processInstancesExampleData = [
            {
                name: 'Familia 1',
                newInstances: 600,
                // TODO instancesInProcess: 1800,
                endInstances: 200,
                ansCounterInstances: {
                    ansOK: 200,
                    ansWARN: 200,
                    ansKO: 200
                },
                //Subfamilies
                subfamilies:[
                    {
                        name: 'Subfamilia 1.1',
                        newInstances: 100,
                        endInstances: 600,
                        ansCounterInstances: {
                            ansOK: 50,
                            ansWARN: 125,
                            ansKO: 25
                        },
                        //Process
                        processes: [
                            {
                                name: 'Proceso 1.1.1',
                                newInstances: 400,
                                endInstances: 200,
                                ansCounterInstances: {
                                    ansOK: 50,
                                    ansWARN: 125,
                                    ansKO: 25
                                },
                                //Operation Types
                                operationTypes: [
                                    {
                                        name: 'Tipo Operacion 1.1.1.1',
                                        newInstances: 200,
                                        endInstances: 200,
                                        ansCounterInstances: {
                                            ansOK: 50,
                                            ansWARN: 125,
                                            ansKO: 25
                                        }
                                    },
                                    {
                                        name: 'Tipo Operacion 1.1.1.2',
                                        newInstances: 100,
                                        endInstances: 200,
                                        ansCounterInstances: {
                                            ansOK: 50,
                                            ansWARN: 125,
                                            ansKO: 25
                                        }
                                    },
                                    {
                                        name: 'Tipo Operacion 1.1.1.3',
                                        newInstances: 100,
                                        endInstances: 200,
                                        ansCounterInstances: {
                                            ansOK: 50,
                                            ansWARN: 125,
                                            ansKO: 25
                                        }
                                    }
                                ]
                            },
                            {
                                name: 'Proceso 1.1.2',
                                newInstances: 100,
                                endInstances: 200,
                                ansCounterInstances: {
                                    ansOK: 50,
                                    ansWARN: 125,
                                    ansKO: 25
                                },
                                operationTypes: []
                            },
                            {
                                name: 'Proceso 1.1.3',
                                newInstances: 100,
                                endInstances: 200,
                                ansCounterInstances: {
                                    ansOK: 50,
                                    ansWARN: 125,
                                    ansKO: 25
                                },
                                operationTypes: []
                            }
                        ]
                    },
                    {
                        name: 'Subfamilia 1.1',
                        newInstances: 100,
                        endInstances: 600,
                        ansCounterInstances: {
                            ansOK: 50,
                            ansWARN: 50,
                            ansKO: 100
                        },
                        processes: []
                    },
                    {
                        name: 'Subfamilia 1.3',
                        newInstances: 100,
                        endInstances: 600,
                        ansCounterInstances: {
                            ansOK: 50,
                            ansWARN: 50,
                            ansKO: 100
                        },
                        processes: []
                    }
                ]
            },

            {
                name: 'Familia 2',
                newInstances: 150,
                endInstances: 300,
                ansCounterInstances: {
                    ansOK: 200,
                    ansWARN: 50,
                    ansKO: 50
                },
                subfamilies:[]
            },{
                name: 'Familia 3',
                newInstances: 250,
                endInstances: 150,
                ansCounterInstances: {
                    ansOK: 200,
                    ansWARN: 50,
                    ansKO: 50
                },
                subfamilies:[]
            }
            //ansEstimate: 1200,
            //ansAverage: 1500,
        ];

        angular.forEach(processInstancesExampleData, function(family, index){

            // New Instances
            newInstances.total += family.newInstances;
            newInstances.data.push({
                value:family.newInstances,
                label:family.name + 1})

            newInstances.data.push({
                value:family.newInstances + 100,
                label:family.name+ 2})

            newInstances.data.push({
                value:family.newInstances +200,
                label:family.name+ 3})

            newInstances.data.push({
                value:family.newInstances +300,
                label:family.name+ 4})

            // End Instances
            endInstances.total += family.endInstances;
            endInstances.data.push({
                value:family.endInstances,
                label:family.name +1})

            endInstances.data.push({
                value:family.endInstances + 100,
                label:family.name+2})

            endInstances.data.push({
                value:family.endInstances + 200,
                label:family.name+3})

            endInstances.data.push({
                value:family.endInstances + 300,
                label:family.name+4})

        }, this);

        //Order Chart Data
        newInstances.data.sort(function(a, b){
            return b.value -a.value;
        });

        endInstances.data.sort(function(a, b){
            return b.value -a.value;
        });

        var colorScales = {
            blue:['#015b7e', '#8dd8f8'],
            red: ['#fe2200', '#f8e109'],
            green: ['#005500', '#00de01']
        };

        //Color Scales
        var blueScale = chroma.scale(colorScales.blue).domain([0, newInstances.data.length]);
        angular.forEach(newInstances.data, function(family, index){
            family.index =  (index+1)/10;
            family.fill = blueScale(index);
        }, this);

        var greenScale = chroma.scale(colorScales.green).domain([0, newInstances.data.length]);
        angular.forEach(endInstances.data, function(family, index){
            family.index =  (index+1)/10;
            family.fill = greenScale(index);
        }, this);

        var chartData = {
            "newProcess":[
                {"index":0.3, "value":17436920, "fill":"#006799", "label":"WebMd Health"},
                {"index":0.4, "value":10884799, "fill":"#494551", "label":"Livestrong.com"},
                {"index":0.5, "value":10257432, "fill":"#564965", "label":"Everyday Health"},
                {"index":0.6, "value":6110024,  "fill":"#574270", "label":"About.com"},
                {"index":0.7, "value":3895612,  "fill":"#4D2D77", "label":"Drugs.com"},
                {"index":0.8, "value":3414585,  "fill":"#684E88", "label":"Alliance Health"},
                {"index":0.9, "value":3099372,  "fill":"#846F9D", "label":"Lifescript.com"},
                {"index":1,   "value":2897520,  "fill":"#A494B7", "label":"Health.com"},
                {"index":1.1, "value":2772679,  "fill":"#CBC3D6", "label":"Health Grades"},
                {"index":1.2, "value":2636126,  "fill":"#F1EFF4", "label":"Healthline"},
                {"index":0.3, "value":17436920, "fill":"#006799", "label":"WebMd Health"},
                {"index":0.4, "value":10884799, "fill":"#494551", "label":"Livestrong.com"},
                {"index":0.5, "value":10257432, "fill":"#564965", "label":"Everyday Health"},
                {"index":0.6, "value":6110024,  "fill":"#574270", "label":"About.com"},
                {"index":0.7, "value":3895612,  "fill":"#4D2D77", "label":"Drugs.com"},
                {"index":0.8, "value":3414585,  "fill":"#684E88", "label":"Alliance Health"},
                {"index":0.9, "value":3099372,  "fill":"#846F9D", "label":"Lifescript.com"},
                {"index":1,   "value":2897520,  "fill":"#A494B7", "label":"Health.com"},
                {"index":1.1, "value":2772679,  "fill":"#CBC3D6", "label":"Health Grades"},
                {"index":1.2, "value":2636126,  "fill":"#F1EFF4", "label":"Healthline"}
            ],
            "barCircleWeb":[
                {"index":0.3, "value":31588490, "fill":"#006799", "label":"WebXMD Health"},
                {"index":0.4, "value":26260662, "fill":"#403437", "label":"Everyday Health"},
                {"index":0.5, "value":24263463, "fill":"#53363C", "label":"Livestrong.com"},
                {"index":0.6, "value":12795112, "fill":"#5E2C3A", "label":"About.com Health Section"},
                {"index":0.7, "value":11959, "fill":"#660E34", "label":"Healthline"},
                {"index":0.8, "value":10408917, "fill":"#7D3A4D", "label":"HealthGrades"},
                {"index":0.9, "value":10317462, "fill":"#96606B", "label":"Yahoo! Health"},
                {"index":1,   "value":9765589,  "fill":"#B28A91", "label":"Lifescript.com"},
                {"index":1.1, "value":7734964,  "fill":"#D3BCBF", "label":"Health.com"},
                {"index":1.2, "value":7504000 , "fill":"#EDE4E5", "label":"Drugs.com"},
                {"index":1.3, "value":7504000 , "fill":"#EDE4E5", "label":"Drugs.com"},
                {"index":1.4, "value":7504000 , "fill":"#EDE4E5", "label":"Drugs.com"},
                {"index":1.5, "value":7504000 , "fill":"#EDE4E5", "label":"Drugs.com"}
            ]
        };

        function drawBarCircleChart(data,target,values,labels){
            var w = 362,
                h = 362,
                size = data[0].value * 1.15,
                radius = 200,
                sectorWidth = .1,
                radScale = 25,
                sectorScale = 1.45,
                target = d3.select(target),
                valueText = d3.select(values),
                labelText = d3.select(labels);


            var arc = d3.svg.arc()
                .innerRadius(function(d,i){return (d.index/sectorScale) * radius + radScale; })
                .outerRadius(function(d,i){return ((d.index/sectorScale) + (sectorWidth/sectorScale)) * radius + radScale; })
                .startAngle(Math.PI)
                .endAngle(function(d) { return Math.PI + (d.value / size) * 2 * Math.PI; });

            var path = target.selectAll("path")
                .data(data);

            //TODO: seperate color and index from data object, make it a pain to update object order
            path.enter().append("svg:path")
                .attr("fill",function(d,i){return d.fill})
                .attr("stroke","#D1D3D4")
                .transition()
                .ease("elastic")
                .duration(1000)
                .delay(function(d,i){return i*100})
                .attrTween("d", arcTween);

            valueText.selectAll("tspan").data(data).enter()
                .append("tspan")
                .attr({
                    x:50,
                    y:function(d,i){return i*14},
                    "text-anchor":"end"
                })
                .text(function(d,i){return data[i].value});

            labelText.selectAll("tspan").data(data).enter()
                .append("tspan")
                .attr({
                    x:0,
                    y:function(d,i){return i*14}
                })
                .text(function(d,i){return data[i].label});

            function arcTween(b) {
                var i = d3.interpolate({value: 0}, b);
                return function(t) {
                    return arc(i(t));
                };
            }
        }

        // Animation Queue
        //setTimeout(function(){drawBarCircleChart(chartData.barCircleWeb,"#circleBar-web-chart","#circleBar-web-values","#circleBar-web-labels")},500);
        //setTimeout(function(){drawBarCircleChart(chartData.barCircleMobile,"#circleBar-mobile-chart","#circleBar-mobile-values","#circleBar-mobile-labels")},800);

        // Text Animations
        d3.select("#circleBar-web-icon")
            .transition()
            .delay(500)
            .duration(500)
            .attr("opacity","1");

        d3.select("#circleBar-web-text")
            .transition()
            .delay(750)
            .duration(500)
            .attr("opacity","1");

        /* ALTAS */
        var newProcessInstancesContainer = $('#newProcessInstancesContainer'),
            width = 500,
            height = 750;


        var svg = d3.select('#newProcessInstancesContainer').append("svg")
                .attr('width', '100%')
                .attr('height', '100%')
                .attr('viewBox','0 0 '+Math.min(width,height) +' '+Math.min(width,height))
                .attr('preserveAspectRatio','xMinYMin'),
                    // Chart Group
                    chartGroupG = svg.append("g").attr("id", "newProcessInstancesGroup"),
                    // Chart
                    chartG = chartGroupG.append("g").attr("id", "newProcessInstancesChart").attr("transform", "translate(260,200)"),
                    // ClipPath
                    clippathG = chartGroupG.append("clippath").attr("id", "newProcessInstancesClipPath"),
                        clippathLabelsG = clippathG.append("rect").attr("id", "newProcessInstancesClipPathLabels").attr("x", "205").attr("y", "215").attr("width", "180").attr("height", "0"),
                    //Legend
                    chartLegendG = chartGroupG.append("g").attr("id", "newProcessInstancesLegend").attr("clip-path", "url(#newProcessInstancesClipPath)"),
                         //Legend Values
                        legendValues = chartGroupG.append("text").attr("id", "newProcessInstancesLegendValues").attr("transform","translate(230,250)"),
                         //Legend Labels
                        legendLabels = chartGroupG.append("text").attr("id", "newProcessInstancesLegendLabels").attr("transform", "translate(290,250)");


        setTimeout(function(){drawBarCircleChart(newInstances.data,
                                                    "#newProcessInstancesChart",
                                                    "#newProcessInstancesLegendValues",
                                                    "#newProcessInstancesLegendLabels")},500);

        d3.select("#newProcessInstancesClipPathLabels")
            .transition()
            .delay(600)
            .duration(1250)
            .attr("height","150");


        /* BAJAS */
        var endProcessInstancesContainer = $('#endProcessInstancesContainer'),
            width = 500,
            height = 750;

        var svg = d3.select('#endProcessInstancesContainer').append("svg")
                .attr('width', '100%')
                .attr('height', '100%')
                .attr('viewBox','0 0 '+Math.min(width,height) +' '+Math.min(width,height))
                .attr('preserveAspectRatio','xMinYMin'),
        // Chart Group
            chartGroupG = svg.append("g")
                .attr("id", "endProcessInstancesGroup"),
            chartG = chartGroupG.append("g")
                .attr("id", "endProcessInstancesChart")
                .attr("transform", "translate(260,200)"),
            chartLegendG = chartGroupG.append("g")
                .attr("id", "endProcessInstancesLegend"),
            legendValues = chartGroupG.append("text")
                .attr("id", "endProcessInstancesLegendValues")
                .attr("transform", "translate(230,250)"),
            legendLabels = chartGroupG.append("text")
                .attr("id", "endProcessInstancesLegendLabels")
                .attr("transform", "translate(290,250)");


        setTimeout(function(){drawBarCircleChart(endInstances.data,
            "#endProcessInstancesChart",
            "#endProcessInstancesLegendValues",
            "#endProcessInstancesLegendLabels")},500);


        function arcTween(transition, newAngle){
            transition.attrTween("d", function(d){
                var interpolate = d3.interpolate(d.endAngle, newAngle);
                return function(t){
                    d.endAngle = interpolate(t);
                    text.text(Math.round((d.endAngle/τ)*100)+'%');
                    return arc(d);
                };
            });
        }

        /* Exportacion PDF & CSV */
        $scope.prepareReportDataToExport = function(){

            // Generate PDF
            var timeLine = "[ "+$scope.startDateText+" - "+$scope.endDateText+" ]";

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
                    {name: 'Familia', value: $scope.selectedFamily.family},
                    {name: 'SubFamilia', value: $scope.selectedSubFamily.subfamily},
                    {name: 'Proceso', value: $scope.selectedProcess.process},
                    //{name: 'Tipo de Operacion', value: $scope.selectedTypeOperation},
                    {name: 'Tiempo', value: timeLine}
                ],
                charts: [
                    {name: 'Grafica de Barras', idElement:'multibarChartSGV', type: 'SVG', minWidth: 1, maxHeight: 200},
                    {name: 'Evolución de Cumplimiento ANS del Tipo de Operacion: '+ $scope.selectedTypeOperation,
                                idElement:'lineChartSGV', type: 'SVG', flexible: true},
                ],
                tables: [{
                    name: 'Tabla: Evolucion del Cumplimiento ANS del Tipo de Operacion: '+ $scope.selectedTypeOperation,
                    columns: columnsTable,
                    data: dataTableArray
                }]
            };

            return reportData;
        };

        /* Exportacion a PDF */
        $scope.exportPDF = function(){

            // Prepare Report Data
            var reportData = $scope.prepareReportDataToExport();

            // Export Service
            exportService.exportReportToPDF(reportData);
        };

        /* Exportacion a CSV */
        $scope.exportCSV = function(){

            // Prepare Report Data
            var reportData = $scope.prepareReportDataToExport();

            // Export Service
            exportService.exportReportToCSV(reportData);
        };

        $scope.closeModal = function(){
            $scope.modalInstance.close();
        };

    }).controller('ProcessInstancesMonitorEditController', function($scope){



    });
