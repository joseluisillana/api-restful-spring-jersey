'use strict';

/**
 * @ngdoc overview
 * @name  bbva.or.commons
 * @description
 * Module with the application common components
 */
angular.module('bbva.or.commons', []);



angular.module('bbva.or.commons').

    /**
     * @ngdoc controller
     * @name  bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * # BBVAChartBaseController
     * Base Controller for all chart widget controllers.
     */
    controller('BBVAChartBaseController', function($rootScope, $scope, $modal, logService, exportService){

    /**
     * @ngdoc property
     * @name bbva.or.commons.property:BBVAChartBaseController#UID
     * @propertyOf bbva.or.commons.controller:BBVAChartBaseController
     * @property {number} UID Universal identifier for controller instance.
     * @description
     * {number} UID Universal identifier for controller instance.
     **/
    $scope.UID = new Date().getTime();

    /**
     * @ngdoc property
     * @name bbva.or.commons.property:BBVAChartBaseController#resizeEventListeners
     * @propertyOf bbva.or.commons.controller:BBVAChartBaseController
     * @property {Array} resizeEventListeners the list of registered listeners for events resizing of the window.
     * @description {Array} resizeEventListeners the list of registered listeners for events resizing of the window.
     **/
    $scope.resizeEventListeners = [];

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#addResizeEventListener
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to registry a listener function to execute in resize events.
     * @param {Function} eventListenerFx Function to execute in resize events.
     */
    $scope.addResizeEventListener = function(eventListenerFx){
        $scope.resizeEventListeners.push(eventListenerFx);
        nv.utils.windowResize(eventListenerFx);
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#removeAllResizeEventListeners
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to remove a registered listener function.
     * @param {Function} eventListenerFx Listener function to remove.
     */
    $scope.removeAllResizeEventListeners = function(eventListenerFunction){
        angular.forEach($scope.resizeEventListeners, function (eventListenerFx, key){
            window.removeEventListener('resize', eventListenerFx);
        }, this);
        $scope.resizeEventListeners = [];
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#toogleFilter
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to show/hide report filters.
     */
    $scope.toogleFilter = function(){
        $scope.config.isFilterVisible = !$scope.config.isFilterVisible;
        $scope.resizeCharts();
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#resizeCharts
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to resize the charts of a report  to max width available.
     */
    $scope.resizeCharts = function(){
        angular.forEach($scope.resizeEventListeners, function (eventListenerFx){
            setTimeout(eventListenerFx, 0);
        }, this);
    };


    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#resetSGV
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to resize the charts of a report  to max width available.
     * @param {string} idContainer Id of container element of SVG chart.
     * @param {string} idSGV Id of SVG chart.
     * @param {number} svgHeight The height for the new chart SVG.
     */
    $scope.resetSGV = function(idContainer, idSGV, svgHeight){

        d3.select('#'+idContainer+' svg').remove();

        var svg = null;
        if(svgHeight){
            svg =   d3.select('#'+idContainer).append("svg")
                .attr("id",idSGV)
                .attr("height",svgHeight);
        }else{
            svg =   d3.select('#'+idContainer).append("svg")
                .attr("id",idSGV);
        }
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#exportPDF
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to export to PDF the report data.
     */
    $scope.exportPDF = function(){
        debugger;
        // Prepare Report Data
        if($scope.prepareReportDataToExport != undefined){

            var reportData = $scope.prepareReportDataToExport();

            // Export Service
            setTimeout(exportService.exportReportToPDF(reportData), 0);

        }else{
            logService.error('Export Error. The "prepareReportDataToExport" is not defined for report: '
                                + $scope.$parent.model.type);
        }
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#exportCSV
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to export to CSV the report data.
     */
    $scope.exportCSV = function(){

        if($scope.prepareReportDataToExport != undefined){

            // Prepare Report Data
            var reportData = $scope.prepareReportDataToExport();

            // Export Service
            exportService.exportReportToCSV(reportData);

        }else{
            logService.error('Export Error. The "prepareReportDataToExport" is not defined for report: '
            + $scope.$parent.model.type);
        }
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#openModal
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to show user message in a modal window.
     */
    $scope.openModal = function(message){

        var modalScope = $scope.$new();
        modalScope.message = message;

        $scope.modalInstance = $modal.open({
            templateUrl: 'templates/modal.html',
            scope: modalScope,
            windowClass: 'center-modal',
            size: 'sm'
        });
    };

    /**
     * @ngdoc method
     * @name bbva.or.commons.controller:BBVAChartBaseController#closeModal
     * @methodOf bbva.or.commons.controller:BBVAChartBaseController
     * @description
     * Method to close user message modal window.
     */
    $scope.closeModal = function(){
        $scope.modalInstance.close();
    };

    $rootScope.$on('$routeChangeStart', function(event, next, current){
        $scope.removeAllResizeEventListeners();
    });
});