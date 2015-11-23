//'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.widgets.ProcessHierarchy
 * @description
 * Module Report: "Visor Familias de Procesos"
 */
angular.module('bbva.or.widgets.ProcessHierarchy', ['bbva.or.db.provider'])

    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('ProcessHierarchyWidget', {
                title: 'Visor de Familias de Procesos',
                description: 'Muestra un informe con las altas, finalizacion, ans de procesos.',
                previewImg : 'images/widgets/w1.png',
                controller: 'ProcessHierarchyController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/ProcessHierarchy/widget.html',
                edit: {
                    templateUrl: 'partials/widgets/ProcessHierarchy/edit.html',
                    reload: true,
                    controller: 'ProcessHierarchyEditController'
                }
            });
    }]).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.ProcessHierarchy.controller:ProcessHierarchyController
     * @description
     * Controller for Module Report: "Visor Familias de Procesos"
     */
    controller('ProcessHierarchyController', function($http, $rootScope, $scope, $modal, config, chartsService, exportService){

        $scope.nodeHeight = 20;

        $scope.nodeTypes = {
            ROOT: 'R',
            FAMILY: 'F',
            SUBFAMILY: 'S',
            PROCESS: 'P',
            OPERATION_TYPE: 'OT'
        };

        $scope.processHierachyTreeData = {name: 'Reporting Operacional', type: $scope.nodeTypes.ROOT, children:[]};

        angular.forEach($rootScope.processHierachy, function (family, key){

            var familyTreeItem = {name: family.family, type: $scope.nodeTypes.FAMILY, children:[]};

            angular.forEach(family.subfamilies, function (subFamily, key){

                var subFamilyTreeItem = {name:subFamily.subfamily, type: $scope.nodeTypes.SUBFAMILY, children:[]};

                angular.forEach(subFamily.processes, function(process, key){

                    var processTreeItem = {name: process.process, type: $scope.nodeTypes.PROCESS, children:[]};

                    angular.forEach(process.operationtypes, function(operationType, key){

                        var operationTypeTreeItem = {name: operationType, type: $scope.nodeTypes.OPERATION_TYPE};
                        processTreeItem.children.push(operationTypeTreeItem);

                    }, this);

                    subFamilyTreeItem.children.push(processTreeItem);

                }, this);

                familyTreeItem.children.push(subFamilyTreeItem);

            }, this);

            $scope.processHierachyTreeData.children.push(familyTreeItem);

        }, this);

        // Calculate total nodes, max label length
        var totalNodes = 0;
        var maxLabelLength = 0;

        // variables for drag/drop
        var selectedNode = null;
        var draggingNode = null;

        // panning variables
        var panSpeed = 200;
        var panBoundary = 20; // Within 20px from edges will pan when dragging.

        // Misc. variables
        var i = 0;
        var duration = 1750;
        var root;

        // size of the diagram
        var viewerWidth = 100;
        var viewerHeight = 55;

        var tree = d3.layout.tree()
            .size([viewerHeight, viewerWidth]);

        // define a d3 diagonal projection for use by the node paths later on.
        var diagonal = d3.svg.diagonal()
            .projection(function(d) {
                return [d.y, d.x];
            });

        // A recursive helper function for performing some setup by walking through all nodes
        function visit(parent, visitFn, childrenFn) {
            if (!parent) return;

            visitFn(parent);

            var children = childrenFn(parent);
            if (children) {
                var count = children.length;
                for (var i = 0; i < count; i++) {
                    visit(children[i], visitFn, childrenFn);
                }
            }
        }

        // Call visit function to establish maxLabelLength
        visit($scope.processHierachyTreeData, function(d) {
            totalNodes++;
            maxLabelLength = Math.max(d.name.length, maxLabelLength);
        }, function(d) {
            return d.children && d.children.length > 0 ? d.children : null;
        });


        // sort the tree according to the node names

        function sortTree() {
            tree.sort(function(a, b) {
                return b.name.toLowerCase() < a.name.toLowerCase() ? 1 : -1;
            });
        }
        // Sort the tree initially incase the JSON isn't in a sorted order.
        sortTree();

        // TODO: Pan function, can be better implemented.

        function pan(domNode, direction) {
            var speed = panSpeed;
            if (panTimer) {
                clearTimeout(panTimer);
                translateCoords = d3.transform(svgGroup.attr("transform"));
                if (direction == 'left' || direction == 'right') {
                    translateX = direction == 'left' ? translateCoords.translate[0] + speed : translateCoords.translate[0] - speed;
                    translateY = translateCoords.translate[1];
                } else if (direction == 'up' || direction == 'down') {
                    translateX = translateCoords.translate[0];
                    translateY = direction == 'up' ? translateCoords.translate[1] + speed : translateCoords.translate[1] - speed;
                }
                scaleX = translateCoords.scale[0];
                scaleY = translateCoords.scale[1];
                scale = zoomListener.scale();
                //svgGroup.transition().attr("transform", "translate(" + translateX + "," + translateY + ")scale(" + scale + ")");
                d3.select(domNode).select('g.node').attr("transform", "translate(" + translateX + "," + translateY + ")");
                zoomListener.scale(zoomListener.scale());
                zoomListener.translate([translateX, translateY]);
                panTimer = setTimeout(function() {
                    pan(domNode, speed, direction);
                }, 50);
            }
        }

        // Define the zoom function for the zoomable tree

        function zoom() {
            svgGroup.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
        }


        // define the zoomListener which calls the zoom function on the "zoom" event constrained within the scaleExtents
        var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);

        function initiateDrag(d, domNode) {
            draggingNode = d;
            d3.select(domNode).select('.ghostCircle').attr('pointer-events', 'none');
            d3.selectAll('.ghostCircle').attr('class', 'ghostCircle show');
            d3.select(domNode).attr('class', 'node activeDrag');

            svgGroup.selectAll("g.node").sort(function(a, b) { // select the parent and sort the path's
                if (a.id != draggingNode.id) return 1; // a is not the hovered element, send "a" to the back
                else return -1; // a is the hovered element, bring "a" to the front
            });
            // if nodes has children, remove the links and nodes
            if (nodes.length > 1) {
                // remove link paths
                links = tree.links(nodes);
                nodePaths = svgGroup.selectAll("path.link")
                    .data(links, function(d) {
                        return d.target.id;
                    }).remove();
                // remove child nodes
                nodesExit = svgGroup.selectAll("g.node")
                    .data(nodes, function(d) {
                        return d.id;
                    }).filter(function(d, i) {
                        if (d.id == draggingNode.id) {
                            return false;
                        }
                        return true;
                    }).remove();
            }

            // remove parent link
            parentLink = tree.links(tree.nodes(draggingNode.parent));
            svgGroup.selectAll('path.link').filter(function(d, i) {
                if (d.target.id == draggingNode.id) {
                    return true;
                }
                return false;
            }).remove();

            dragStarted = null;
        }

        // define the baseSvg, attaching a class for styling and the zoomListener
        var baseSvg = d3.select("#tree-container").append("svg")
            .attr("width", viewerWidth)
            .attr("height", viewerHeight)
            .attr("class", "overlay")
            .call(zoomListener);

        var defs = baseSvg.append('svg:defs'),
            linearGradient1 = defs.append('svg:linearGradient')
                .attr('id', 'bbva-gradient-yellow')
                .attr('x1', 0)
                .attr('y1', 0)
                .attr('x2', 0)
                .attr('y2', 1),
                linearGradient1Stop1 = linearGradient1.append('svg:stop')
                    .attr('offset', '0%')
                    .attr('stop-color', '#fbb450'),
                linearGradient1Stop2 = linearGradient1.append('svg:stop')
                    .attr('offset', '100%')
                    .attr('stop-color', '#f89406'),

            linearGradient2 = defs.append('svg:linearGradient')
                .attr('id', 'bbva-gradient-blueN1')
                .attr('x1', 0)
                .attr('y1', 0)
                .attr('x2', 0)
                .attr('y2', 1),
            linearGradient2Stop1 = linearGradient2.append('svg:stop')
                .attr('offset', '0%')
                .attr('stop-color', '#339ACC'),
            linearGradient2Stop2 = linearGradient2.append('svg:stop')
                .attr('offset', '100%')
                .attr('stop-color', '#006799'),

            linearGradient3 = defs.append('svg:linearGradient')
                .attr('id', 'bbva-gradient-blueN2')
                .attr('x1', 0)
                .attr('y1', 0)
                .attr('x2', 0)
                .attr('y2', 1),
            linearGradient3Stop1 = linearGradient3.append('svg:stop')
                .attr('offset', '0%')
                .attr('stop-color', '#0065ba'),
            linearGradient3Stop1 = linearGradient3.append('svg:stop')
                .attr('offset', '100%')
                .attr('stop-color', '#005aa6');


        ;

        /* <svg height="150" width="400">
            <defs>
                <linearGradient id="grad2" x1="0%" y1="0%" x2="0%" y2="100%">
                    <stop offset="0%" style="stop-color:rgb(255,255,0);stop-opacity:1" />
                    <stop offset="100%" style="stop-color:rgb(255,0,0);stop-opacity:1" />
                </linearGradient>
            </defs>
            <ellipse cx="200" cy="70" rx="85" ry="55" fill="url(#grad2)" />
        </svg> */

        // Define the drag listeners for drag/drop behaviour of nodes.
        dragListener = d3.behavior.drag()
            .on("dragstart", function(d) {
                if (d == root) {
                    return;
                }
                dragStarted = true;
                nodes = tree.nodes(d);
                d3.event.sourceEvent.stopPropagation();
                // it's important that we suppress the mouseover event on the node being dragged. Otherwise it will absorb the mouseover event and the underlying node will not detect it d3.select(this).attr('pointer-events', 'none');
            })
            .on("drag", function(d) {
                if (d == root) {
                    return;
                }
                if (dragStarted) {
                    domNode = this;
                    initiateDrag(d, domNode);
                }

                // get coords of mouseEvent relative to svg container to allow for panning
                relCoords = d3.mouse($('svg').get(0));
                if (relCoords[0] < panBoundary) {
                    panTimer = true;
                    pan(this, 'left');
                } else if (relCoords[0] > ($('svg').width() - panBoundary)) {

                    panTimer = true;
                    pan(this, 'right');
                } else if (relCoords[1] < panBoundary) {
                    panTimer = true;
                    pan(this, 'up');
                } else if (relCoords[1] > ($('svg').height() - panBoundary)) {
                    panTimer = true;
                    pan(this, 'down');
                } else {
                    try {
                        clearTimeout(panTimer);
                    } catch (e) {

                    }
                }

                d.x0 += d3.event.dy;
                d.y0 += d3.event.dx;
                var node = d3.select(this);
                node.attr("transform", "translate(" + d.y0 + "," + d.x0 + ")");
                updateTempConnector();
            }).on("dragend", function(d) {
                if (d == root) {
                    return;
                }
                domNode = this;
                if (selectedNode) {
                    // now remove the element from the parent, and insert it into the new elements children
                    var index = draggingNode.parent.children.indexOf(draggingNode);
                    if (index > -1) {
                        draggingNode.parent.children.splice(index, 1);
                    }
                    if (typeof selectedNode.children !== 'undefined' || typeof selectedNode._children !== 'undefined') {
                        if (typeof selectedNode.children !== 'undefined') {
                            selectedNode.children.push(draggingNode);
                        } else {
                            selectedNode._children.push(draggingNode);
                        }
                    } else {
                        selectedNode.children = [];
                        selectedNode.children.push(draggingNode);
                    }
                    // Make sure that the node being added to is expanded so user can see added node is correctly moved
                    expand(selectedNode);
                    sortTree();
                    endDrag();
                } else {
                    endDrag();
                }
            });

        function endDrag() {
            selectedNode = null;
            d3.selectAll('.ghostCircle').attr('class', 'ghostCircle');
            d3.select(domNode).attr('class', 'node');
            // now restore the mouseover event or we won't be able to drag a 2nd time
            d3.select(domNode).select('.ghostCircle').attr('pointer-events', '');
            updateTempConnector();
            if (draggingNode !== null) {
                update(root);
                centerNode(draggingNode);
                draggingNode = null;
            }
        }

        // Helper functions for collapsing and expanding nodes.
        function collapse(d){
            if(d.children){
                d._children = d.children;
                d._children.forEach(collapse);
                d.children = null;
            }
        }

        function expand(d) {
            if (d._children) {
                d.children = d._children;
                d.children.forEach(expand);
                d._children = null;
            }
        }

        var overCircle = function(d) {
            selectedNode = d;
            updateTempConnector();
        };
        var outCircle = function(d) {
            selectedNode = null;
            updateTempConnector();
        };

        // Function to update the temporary connector indicating dragging affiliation
        var updateTempConnector = function() {
            var data = [];
            if (draggingNode !== null && selectedNode !== null) {
                // have to flip the source coordinates since we did this for the existing connectors on the original tree
                data = [{
                    source: {
                        x: selectedNode.y0,
                        y: selectedNode.x0
                    },
                    target: {
                        x: draggingNode.y0,
                        y: draggingNode.x0
                    }
                }];
            }
            var link = svgGroup.selectAll(".templink").data(data);

            link.enter().append("path")
                .attr("class", "templink")
                .attr("d", d3.svg.diagonal())
                .attr('pointer-events', 'none');

            link.attr("d", d3.svg.diagonal());

            link.exit().remove();
        };

        // Function to center node when clicked/dropped so node doesn't get lost when collapsing/moving with large amount of children.
        function centerNode(source) {
            scale = zoomListener.scale();
            x = -source.y0;
            y = -source.x0;
            x = x * scale + viewerWidth / 2;
            y = y * scale + viewerHeight / 2;
            d3.select('g').transition()
                .duration(duration)
                .attr("transform", "translate(" + x + "," + y + ")scale(" + scale + ")");
            zoomListener.scale(scale);
            zoomListener.translate([x, y]);
        }

        // Toggle children function

        function toggleChildren(d) {
            if (d.children) {
                d._children = d.children;
                d.children = null;
            } else if (d._children) {
                d.children = d._children;
                d._children = null;
            }
            return d;
        }

        // Toggle children on click.
        function click(d) {
            if (d3.event.defaultPrevented) return; // click suppressed
            d = toggleChildren(d);
            update(d);
            centerNode(d);
        }

        function update(source) {
            // Compute the new height, function counts total children of root node and sets tree height accordingly.
            // This prevents the layout looking squashed when new nodes are made visible or looking sparse when nodes are removed
            // This makes the layout more consistent.
            var levelWidth = [1];
            var childCount = function(level, n) {

                if (n.children && n.children.length > 0) {
                    if (levelWidth.length <= level + 1) levelWidth.push(0);

                    levelWidth[level + 1] += n.children.length;
                    n.children.forEach(function(d) {
                        childCount(level + 1, d);
                    });
                }
            };

            childCount(0, root);
            var newHeight = d3.max(levelWidth) * $scope.nodeHeight; // 25 pixels per line
            tree = tree.size([newHeight, viewerWidth]);

            // Compute the new tree layout.
            var nodes = tree.nodes(root).reverse(),
                links = tree.links(nodes);

            // Set widths between levels based on maxLabelLength.
            nodes.forEach(function(d) {
                d.y = (d.depth * (maxLabelLength * 10)); //maxLabelLength * 10px
                // alternatively to keep a fixed scale one can set a fixed depth per level
                // Normalize for fixed-depth by commenting out below line
                // d.y = (d.depth * 500); //500px per level.
            });

            // Update the nodes…
            node = svgGroup.selectAll("g.node")
                .data(nodes, function(d) {
                    return d.id || (d.id = ++i);
                });

            // Enter any new nodes at the parent's previous position.
            var nodeEnter = node.enter().append("g")
                .call(dragListener)
                .attr("class", "node")
                .attr("transform", function(d){
                    return "translate(" + source.y0 + "," + source.x0 + ")";
                })
                .on('click', click);

            nodeEnter.append("rect")
                .attr('class', 'node')
                .attr("y", function(d){
                    //return d.children || d._children ? -10 : 10;
                    return -10;
                })
                .attr("width", 150)
                .attr("height", $scope.nodeHeight)
                .style("fill", function(d){

                    return d.type && d.type=="F" ? "url(#bbva-gradient-blueN1)" : "url(#bbva-gradient-yellow)";
                });

            nodeEnter.append("text")
                .attr("x", function(d) {
                    return d.children || d._children ? -10 : 10;
                })
                .attr("dy", ".35em")
                .attr('class', 'nodeText')
                .attr("text-anchor", function(d) {
                    return d.children || d._children ? "end" : "start";
                })
                .text(function(d) {
                    return d.name;
                })
                .style("fill-opacity", 0);

            // phantom node to give us mouseover in a radius around it
            nodeEnter.append("circle")
                .attr('class', 'ghostCircle')
                .attr("r", 30)
                .attr("opacity", 0.2) // change this to zero to hide the target area
                .style("fill", "red")
                .attr('pointer-events', 'mouseover')
                .on("mouseover", function(node) {
                    overCircle(node);
                })
                .on("mouseout", function(node) {
                    outCircle(node);
                });

            // Update the text to reflect whether node has children or not.
            node.select('text')
                .attr("x", function(d){
                    return 10;
                })
                .style("fill", function(d) {
                    return  "#FFFFFF";
                })
                .attr("text-anchor", function(d) {
                    //return d.children || d._children ? "end" : "start";
                    return "start";
                })
                .text(function(d){
                    return d.name;
                });

            // Change the circle fill depending on whether it has children and is collapsed
            /* node.select("circle.nodeCircle")
                .attr("r", 4.5)
                .style("fill", function(d) {
                    //TODO esto es lo que cambia cuando se abre/cierra un nodo
                    //return d._children ? "lightsteelblue" : "#fff";
                });*/

            // Transition nodes to their new position.
            var nodeUpdate = node.transition()
                .duration(duration)
                .attr("transform", function(d) {
                    return "translate(" + d.y + "," + d.x + ")";
                });

            // Fade the text in
            nodeUpdate.select("text")
                .style("fill-opacity", 1);

            // Transition exiting nodes to the parent's new position.
            var nodeExit = node.exit().transition()
                .duration(duration)
                .attr("transform", function(d) {
                    return "translate(" + source.y + "," + source.x + ")";
                })
                .remove();

            nodeExit.select("circle")
                .attr("r", 0);

            nodeExit.select("text")
                .style("fill-opacity", 0);

            // Update the links…
            var link = svgGroup.selectAll("path.link")
                .data(links, function(d) {
                    return d.target.id;
                });

            // Enter any new links at the parent's previous position.
            link.enter().insert("path", "g")
                .attr("class", "link")
                .attr("d", function(d) {
                    var o = {
                        x: source.x0 + 150,
                        y: source.y0 +30
                    };
                    return diagonal({
                        source: o,
                        target: o
                    });
                });

            // Transition links to their new position.
            link.transition()
                .duration(duration)
                .attr("d", diagonal);

            // Transition exiting nodes to the parent's new position.
            link.exit().transition()
                .duration(duration)
                .attr("d", function(d) {
                    var o = {
                        x: source.x,
                        y: source.y
                    };
                    return diagonal({
                        source: o,
                        target: o
                    });
                })
                .remove();

            // Stash the old positions for transition.
            nodes.forEach(function(d) {
                d.x0 = d.x;
                d.y0 = d.y;
            });
        }

        // Append a group which holds all nodes and which the zoom Listener can act upon.
        var svgGroup = baseSvg.append("g");

        // Define the root
        root =  $scope.processHierachyTreeData;
        root.x0 = viewerHeight / 2;
        root.y0 = 0;

        // Layout the tree initially and center on the root node.
        update(root);
        centerNode(root);









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
    }).

    /**
     * @ngdoc controller
     * @name bbva.or.widgets.ProcessHierarchy.controller:ProcessHierarchyEditController
     * @description
     * Edit Controller for Module Report: "Visor Familias de Procesos"
     */
    controller('ProcessHierarchyEditController', function($scope){


    });
