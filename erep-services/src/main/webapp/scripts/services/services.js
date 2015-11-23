'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.services
 * @description
 * # bbva.or.services
 *
 * Module of Services for Operational Reporting Application.
 */
angular.module('bbva.or.services', []);


/**
 * @ngdoc service
 * @name bbva.or.services.logService
 * @description
 * Log Service for application log messages.
 */
angular.module('bbva.or.services').service('logService', function($log, $rootScope) {

    /**
     * @ngdoc method
     * @name bbva.or.services.logService#info
     * @methodOf bbva.or.services.logService
     * @description
     *
     * Put informational message in console.
     *
     * @param {string} message the message.
     * @param {object} data other additional information.
     */
    function info(message, data){
        if($rootScope.appConfig.logs.enabledLog){
            $log.info($rootScope.appConfig.logs.logPrefix + message + " || INFOR-DATA: " + data);
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.logService#warn
     * @methodOf bbva.or.services.logService
     * @description
     *
     * Put warning message in console.
     *
     * @param {string} message the message.
     * @param {object} data other additional information.
     */
    function warn(message, data){
        if($rootScope.appConfig.logs.enabledLog)
            $log.warn($rootScope.appConfig.logs.logPrefix + message + " || WARN-DATA: " + data);
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.logService#error
     * @methodOf bbva.or.services.logService
     * @description
     *
     * Put error message in console.
     *
     * @param {string} message the message.
     * @param {object} data other additional information.
     */
    function error(message, data){
        if($rootScope.appConfig.logs.enabledLog)
            $log.error($rootScope.appConfig.logs.logPrefix + message + " || ERROR-DATA: " + data);
    }

    /* Public API */
    return({
        info: info,
        warn: warn,
        error: error
    });
});


/**
 * @ngdoc service
 * @name bbva.or.services.userService
 * @description
 * Menu Service for Application.
 */
angular.module('bbva.or.services').service('userService', function($log, $rootScope){

    /**
     * @ngdoc method
     * @name bbva.or.services.logService#info
     * @methodOf bbva.or.services.logService
     * @description
     * Put informational message in console.
     */
    function loadUserMenu(){


    }

    /* Public API */
    return({
        loadUserMenu: loadUserMenu
    });
});



/**
 * @ngdoc service
 * @name bbva.or.services.restService
 * @description
 * Service for generate URL End-Point Rest services for the application.
 */
angular.module('bbva.or.services').service('restService', function($log, $rootScope, logService) {

    /**
     * @ngdoc method
     * @name bbva.or.services.restService#generateServiceEndPoint
     * @methodOf bbva.or.services.restService
     * @description
     *
     * Method to generate URL End-Point by End-Point id.
     *
     * @param {string} endPoint the End-Point id.
     */
    function generateServiceEndPoint(endPoint){

        if(endPoint){
            var endPointServiceURL= $rootScope.appConfig.services.protocol + '://' +
                                    $rootScope.appConfig.services.machine + ':' +
                                    $rootScope.appConfig.services.port + '/' +
                                    $rootScope.appConfig.services.endPointServicesBasePath + '/' +
                                    endPoint;
            return endPointServiceURL;
        }else{
            return null;
        }
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.restService#getService
     * @methodOf bbva.or.services.restService
     * @description
     *
     * Method to get a Rest Service configuration by service id.
     *
     * @param {string} serviceId the id of the service.
     */
    function getService(serviceId){
        return $rootScope.appConfig.services.list[serviceId];
    }

    /* Public API */
    return({
        generateServiceEndPoint: generateServiceEndPoint,
        getService: getService
    });
});


/**
 * @ngdoc service
 * @name bbva.or.services.hierarchyService
 * @description
 * Service for process hierarchy of application.
 */
angular.module('bbva.or.services').service('hierarchyService', function( $rootScope, $http, $q, restService, logService) {

    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getProcessHierachy
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the process hierarchy of application.
     */
    function getProcessHierachy(){

        var serviceId = 'processHierachy';
        var service = restService.getService(serviceId);

        if(service){
            var request = $http({
                method: service.method,
                url: restService.generateServiceEndPoint(service.endPoint)
            });
            return(request.then(handleSuccess,handleError));
        }else{
            logService.error('Service Error: The service not exist: '+ serviceId);
            return null;
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getProcessDefinitions
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the process definitions.
     */
    function getProcessDefinitions(){

        // MODO LOCAL return EREP_PROCESS_DEFS[0];

        var serviceId = 'processDefinitions';
        var service = restService.getService(serviceId);

        if(service){
            var request = $http({
                method: service.method,
                url: restService.generateServiceEndPoint(service.endPoint)
            });
            return(request.then(handleSuccess,handleError));

        }else{
            logService.error('Service Error: The service not exist: '+ serviceId);
            return null;
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getFamilies
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the list of families from process hierarchy
     */
    function getFamilies(){
        return $rootScope.processHierachy;
    };


    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getFamily
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get a family by id.
     * @param {string} idFamily The id of the family.
     */
    function getFamily(idFamily){
        var family = null;
        angular.forEach($rootScope.processHierachy, function (familyAux, key) {
            if(familyAux.family == idFamily){
                family = familyAux;
            }
        }, this);
        return family;
    };


    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService:FiltroMaestroController#getSubFamiliesOfFamily
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the list of subfamilies of a family from process hierarchy.
     * @param {string} idFamily The if of Family.
     */
    function getSubFamiliesOfFamily(idFamily){

        var family = this.getFamily(idFamily);

        if(family != null){
            return family.subfamilies;
        }else{
            return null;
        }
    };

    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService:FiltroMaestroController#getSubFamiliesOfFamily
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the list of subfamilies of a family from process hierarchy.
     * @param {string} idFamily The id of the family of subfamily.
     * @param {string} idSubFamily The id of the subfamily.
     */
    function getSubFamily(idFamily, idSubFamily){

        var subFamily = null;
        var family = this.getFamily(idFamily);
        if(family!=null){
            angular.forEach(family.subfamilies, function (subFamilyAux, key){
                if(subFamilyAux.subfamily == idSubFamily){
                    subFamily = subFamilyAux;
                }
            }, this);
        }
        return subFamily;
    };


    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getProcessOfSubfamily
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the list of process of a subfamily from process hierarchy.
     * @param {string} idFamily The id of the family of subfamily.
     * @param {string} idSubFamily The id of the subfamily.
     */
    function getProcessOfSubfamily(idFamily, idSubFamily){

        var subFamily = this.getSubFamily(idFamily, idSubFamily);

        if(subFamily != null){
            return subFamily.processes;
        }else{
            return null;
        }
    };


    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getProcess
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the a process from process hierarchy.
     * @param {string} idFamily The id of the family of subfamily.
     * @param {string} idSubFamily The id of the subfamily.
     * @param {string} idProcess The id of the subfamily.
     */
    function getProcess(idFamily, idSubFamily, idProcess){

        var process = null;
        var subFamily = this.getSubFamily(idFamily, idSubFamily);

        if(subFamily != null && subFamily.processes){
            angular.forEach(subFamily.processes, function (processAux, key){
                if (processAux.process == idProcess){
                    process = processAux;
                }
            }, this);
        }
        return process;
    };


    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#getOperationsOfProcess
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to get the list of operations of a process from process hierarchy.
     * @param {string} idFamily The id of the family of subfamily.
     * @param {string} idSubFamily The id of the subfamily.
     * @param {string} idProcess The id of the subfamily.
     */
    function getOperationsOfProcess(idFamily, idSubFamily, idProcess){
        var process = this.getProcess(idFamily, idSubFamily, idProcess);
        if(process != null){
            return process.operationtypes;
        }else{
            return null;
        }
    };


    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#handleError
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to handle the errors during loading the process hierarchy of application.
     * @param {object} response The response of the request.
     * @return {string} The error message.
     */
    function handleError( response ) {
        logService.error('Service Error: Response: '+ response);
        if (!angular.isObject(response.data) || !response.data.message){
            return( $q.reject( 'An unknown error occurred.' ) );
        }
        return( $q.reject( response.data.message));
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.hierarchyService#handleSuccess
     * @methodOf bbva.or.services.hierarchyService
     * @description
     * Method to handle loading the process hierarchy of application in the success case
     * @param {object} response The response of the request.
     * @return {object|Array} The response data of the request.
     */
    function handleSuccess(response){
        return(response.data);
    }

    /* Public API */
    return({
        getProcessHierachy: getProcessHierachy,
        getFamilies: getFamilies,
        getFamily: getFamily,
        getSubFamiliesOfFamily: getSubFamiliesOfFamily,
        getSubFamily: getSubFamily,
        getProcessOfSubfamily: getProcessOfSubfamily,
        getOperationsOfProcess: getOperationsOfProcess,
        getProcess: getProcess,
        getProcessDefinitions: getProcessDefinitions
    });

});


/**
 * @ngdoc service
 * @name bbva.or.services.chartsService
 * @description
 * Service for process hierarchy of application.
 */
angular.module('bbva.or.services').service('chartsService', function($rootScope, $http, $q, restService, logService){

    /**
     * @ngdoc method
     * @name bbva.or.services.chartsService#getOperationsByProcess
     * @methodOf bbva.or.services.chartsService
     * @description
     * Method to get new process instances in a time interval and grouping by operation type.
     * @param {string} family The family of the process.
     * @param {string} subfamily The subfamily of the process.
     * @param {string} process The process of the process.
     * @param {string} timeMode The time mode of time interval: [In Months, In days]
     * @param {string} startDate The init date for time interval.
     * @param {string} endDate The end date for time interval.
     * @returns {*} The result of the request.
     */
    function getOperationsByProcess(family, subfamily , process, timeMode, startDate, endDate){

        var serviceId = 'operationsByProcess';
        var service = restService.getService(serviceId);

        var endPointServiceURL = restService.generateServiceEndPoint(service.endPoint) + '/' +
        family + '/' +
        subfamily + '/' +
        process + '/' +
        timeMode + '/' +
            (startDate ? startDate : '') + '/' +
            (endDate ? endDate: '') ;

        if(service){
            var request = $http({
                method: service.method,
                url: endPointServiceURL
            });
            return(request.then(handleSuccess,handleError));
        }else{
            logService.error('Service Error: The service not exist: '+ serviceId);
            return null;
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.chartsService#getANSComplianceByProcess
     * @methodOf bbva.or.services.chartsService
     * @description
     * Method to get ANS Compliance for a process in a time interval and grouping by operation type.
     * @param {string} family The family of the process.
     * @param {string} subfamily The subfamily of the process.
     * @param {string} process The process of the process.
     * @param {string} startDate The init date for time interval.
     * @param {string} endDate The end date for time interval.
     * @returns {*} The result of the request.
     */
    function getANSComplianceByProcess(family, subfamily , process, startDate, endDate){  // StartDate & EndDate in milliseconds

        var serviceId = 'processANSCompliance';
        var service = restService.getService(serviceId);

        var endPointServiceURL = restService.generateServiceEndPoint(service.endPoint) + '/' +
            family + '/' +
            subfamily + '/' +
            process + '/' +
            (startDate ? startDate : '') + '/' +
            (endDate ? endDate: '') ;

        if(service){
            var request = $http({
                method: service.method,
                url: endPointServiceURL
            });
            return(request.then(handleSuccess,handleError));
        }else{
            logService.error('Service Error: The service not exist: '+ serviceId);
            return null;
        }
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.chartsService#getANSComplianceByOperationType
     * @methodOf bbva.or.services.chartsService
     * @description
     * Method to get ANS Compliance for a operation type in a time interval.
     * @param {string} family The family of the process.
     * @param {string} subfamily The subfamily of the process.
     * @param {string} process The process of the process.
     * @param {string} startDate The init date for time interval in milliseconds.
     * @param {string} endDate The end date for time interval in milliseconds.
     * @returns {*} The result of the request.
     */
    function getANSComplianceByOperationType(family, subfamily , process, operationType, startDate, endDate){

        var serviceId = 'operationTypeANSCompliance';
        var service = restService.getService(serviceId);

        var endPointServiceURL = restService.generateServiceEndPoint(service.endPoint) + '/' +
            family + '/' +
            subfamily + '/' +
            process + '/' +
            operationType + '/' +
            (startDate ? startDate : '') + '/' +
            (endDate ? endDate: '') ;

        if(service){
            var request = $http({
                method: service.method,
                url: endPointServiceURL
            });
            return(request.then(handleSuccess,handleError));
        }else{
            logService.error('Service Error: The service not exist: '+ serviceId);
            return null;
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.chartsService#getAnsClaims
     * @methodOf bbva.or.services.chartsService
     * @description
     * Method to load ANS data of Claims Process.
     * @param {object} year The year.
     * @return {string} month The month.
     */
    function getAnsClaims(year, month){

        var serviceId = 'ansClaims';
        var service = restService.getService(serviceId);

        var endPointServiceURL = restService.generateServiceEndPoint(service.endPoint) + '/' + year + '/' + month;

        if(service){
            var request = $http({
                method: service.method,
                url: endPointServiceURL
            });
            return(request.then(handleSuccess,handleError));
        }else{
            logService.error('Service Error: The service not exist: '+ serviceId);
            return null;
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.chartsService#handleError
     * @methodOf bbva.or.services.chartsService
     * @description
     * Method to handle the errors during loading data.
     * @param {object} response The response of the request.
     * @return {string} The error message.
     */
    function handleError( response ) {
        logService.error('Service Error: Response: '+ response);
        if (!angular.isObject(response.data) || !response.data.message) {
            return( $q.reject( 'An unknown error occurred.' ) );
        }
        return( $q.reject( response.data.message ) );
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.chartsService#handleSuccess
     * @methodOf bbva.or.services.chartsService
     * @description
     * Method to handle loading data in the success case.
     * @param {object} response The response of the request.
     * @return {object|Array} The response data of the request.
     */
    function handleSuccess(response){
        return(response.data);
    }

    /* Public API */
    return({
      getOperationsByProcess: getOperationsByProcess,
      getANSComplianceByProcess: getANSComplianceByProcess,
      getANSComplianceByOperationType: getANSComplianceByOperationType,
      getAnsClaims: getAnsClaims
    });
});

/**
 * @ngdoc service
 * @name bbva.or.services.exportService
 * @description
 * Service for export charts & data to PDF/CSV
 */
angular.module('bbva.or.services').service('exportService', function(logService){

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#exportReportToPDF
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to export to PDF the report information.
     * @param {object} reportData The report data to export.
     * @example
     *
     *  var reportData = {
                //Config
                name: 'Informe Altas Procesos Por Tipo de Operacion',
                config:{
                    orientation: 'portrait', // 'landscape'
                    unit: 'pt',
                    format: 'a4',
                    compress: false
                },
                params: [
                    {name: 'Familia', value: 'XXX'},
                    ....
                ],
                charts: [
                    {name: 'Grafica de Sectores', idElement: $scope.pieChartElementSVG, type: 'SVG', flexible: false, minWidth: 1, maxHeight: 150},
                    ...
                ],
                tables: [{
                    name: 'Tabla: Altas por Tipo de Operacion y '+timeLineLabel,
                    columns: columnsTable,
                    data: dataTableArray
                }]
            };
     *
     */
    function exportReportToPDF(reportData){

        // Export Report Charts
        exportReportCharts(reportData);

        // Export Report DataTables
        exportReportDataTables(reportData);
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#exportReportToCSV
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to export to CSV the report information.
     * @param {object} reportData The report data to export.
     */
    function exportReportToCSV(reportData){

        var csv = null;

        if(reportData && reportData.tables && reportData.tables.length > 0){
            var dataURL = '';

            for(var i=0; i<reportData.tables.length; i++) {
                dataURL += addTableToCSV(reportData.tables[i]);
            }
            csv = 'data:text/csv;charset=utf-8;base64,' + btoa(dataURL);

            /* Download CSV*/
            downloadCSV(csv, reportData.name);
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addTableToCSV
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add to CSV a report data table.
     * @param {object} table The report data to export.
     * @return {object} The csv table data.
     */
    function addTableToCSV(table){

        /* Init CSV Table */
        var csvTable = {data: ''};

        /* Columns Headers */
        addColumnsHeadersToCSV(csvTable, table.columns);

        /* Columns Data Rows */
        addDataRowsToCSV(csvTable, table.data);

        return csvTable.data;
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#downloadCSV
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to download the CSV generate for a report.
     * @param {object} csv The csv data.
     * @param {string} name The csv data.
     */
    function downloadCSV(csv, name){

        var downloadLink = document.createElement('a');
        downloadLink.href = csv;
        downloadLink.target = '_blank';
        downloadLink.download = name+'.csv';

        downloadLink.click();
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addSeparationLineToCSV
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add new data line in CSV.
     * @param {object} csv The csv data file.
     */
    function addSeparationLineToCSV(csv){
        csv.data += '\n';
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addColumnsHeadersToCSV
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add columns headers in CSV.
     * @param {object} csv The csv data file.
     * @param {Array} columnsHeaders The list of columns headers.
     */
    function addColumnsHeadersToCSV(csv, columnsHeaders){

      var fieldSeparator = ';',
        textField = '"',
        regExpTesto = /(")/g,
        regExp = /[";]/;


      var headersRow='';
        for(var i=0; i<columnsHeaders.length; i++) {
            var columnHeader = columnsHeaders[i];

            if(headersRow!==''){
                headersRow += fieldSeparator;
            }

            if (regExp.test(columnHeader.title)) {
                columnHeader.title = textField + columnHeader.title.replace(regExpTesto, '$1$1') + textField;
            }
            headersRow += columnHeader.title;
        }
        csv.data = csv.data + headersRow;
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addDataRowsToCSV
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add data rows in CSV.
     * @param {object} csv The csv data file.
     * @param {Array} dataRows The list of data rows for csv.
     */
    function addDataRowsToCSV(csv, dataRows){

      var fieldSeparator = ';',
          textField = '"',
          regExpTesto = /(")/g,
          regExp = /[";]/;

        var dataRow = null;

        addSeparationLineToCSV(csv);

        for(var i=0; i<dataRows.length; i++) {

            dataRow='';
            var row = dataRows[i];
            for (var key in row){
                var propertyValue = row[key];
                if (regExp.test(propertyValue)){
                    propertyValue = textField + propertyValue.replace(regExpTesto, '$1$1') + textField;
                }
                dataRow += propertyValue + fieldSeparator;
            }
            csv.data += dataRow;
            addSeparationLineToCSV(csv);
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#exportReportDataTables
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to export to PDF data tables of a report.
     * @param {object} reportData The report data.
     * @return {object} The result object with the PDF.
     */
    function exportReportDataTables(reportData){

        // Report Configuration
        reportData.config.orientation = 'landscape';
        var docPDF = configureReport(reportData);

        // Report Head
        addReportHead(docPDF, reportData);

        // Report Params
        addReportParams(docPDF, reportData);

        // Report Tables
        addReportTables(docPDF, reportData);

        //Download PDF
        docPDF.save('Datos:' + reportData.name + '.pdf');

        return docPDF;
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#configureReport
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to configure the PDF for a report.
     * @param {report} report The report data.
     * @return {object} The object with the configure PDF.
     */
    function configureReport(report){

        var docPDF;
        if(report.config){
            /* jshint ignore:start */

            docPDF = new jsPDF(report.config.orientation, report.config.unit,
                report.config.format, report.config.compress);
            /* jshint ignore:end */
        }else{
          /* jshint ignore:start */
            docPDF = new jsPDF('p', 'px', 'a4', false);
          /* jshint ignore:end */
        }

        /* Default Aparence */
        docPDF.setFontSize(14);
        docPDF.setDrawColor(0);
        docPDF.setFillColor(255, 255, 255);

        /* Page Size */
        docPDF.xMax = docPDF.internal.pageSize.width;
        docPDF.yMax = docPDF.internal.pageSize.height;

        /* Init Position  */
        docPDF.xInitPosition = 25;
        docPDF.yInitPosition = 25;

        /* Position Indicators */
        docPDF.xPosition = docPDF.xInitPosition;
        docPDF.yPosition = docPDF.yInitPosition;

        return docPDF;
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#configureReport
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add report head to PDF for a report.
     * @param {object} docPDF The object with the configure PDF.
     * @param {object} reportData The report data.
     */
    function addReportHead(docPDF, reportData){

        var logoBBVA = {
            weight: 143,
            height: 59,
            data: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAI8AAAA7CAYAAABCONnwAAAFy0lEQVR42u2cPY/UMBCGI3r+Cx0SHSVItPwGSnoKWkSFRAOiQKKjQaJBokW6AlHeDziJigpR0YadkERZZz7esZ1d525Gsu5jkziJH/udGdvbdXde9EgJC0utC3jCAp6wgCcs4AkLeAKesIAnLOAJC3jCAp6wGw7P7cfve6SEhZ0Mnu7h2+JC9T59c9F/vfoN1fnq8+X/kbKw3rtPPg7XSo3ugz7T6qD7LTGql5776LqH+rj7Ift0cbU+nnmPdNyNgsfbKLXgGcr91wMoS3B//fnbP3j+ZfhMOod+ljQUPSd3fakDDceDHZHu/0bCQy/UAqgqPGOdaa8d6qDGVQAiwHIais4ZRrbltQ/Po11vOL7S+9sPPFMDpMU4T+vVEDzeOhMYaAQYZEKBhz5HpZaVoASeEsnacvQ5GzzUY6hRlmXudUqD0nG58NBzpHVSsWBYQgtJ16FIDQ5J1nTt8Xepw6CShXa+3cCzbIxlYXtfAl0WPKMPM/W8tF7J15jOXcIwS5f0fIbUSJK1gvJwneU9pzYc75RhrfPtAh7L+9ekQXuZHng8zmoKjyldY6N7pIu95uE6kp8yR34ZQUAt6WoSHq0ha408LmgZGaotXcO9L2TSkqxVSD8+HwJULenqqLIW4aHPucbJ9nkAeGbpWF6DibhQ6fJENzOM0zUNyVr5R6NUsnmijaKu7tbL7z1STg2P1Dhab64BzyoJKITes2wY0oVIhDdEl46foFBHn4rS1SQ87Msx/J0a8EijnQTsapQyckSuEF2RPSmomOpCorAa0tUkPJK/A41WmfCwI4mR8DMThqB0cSG6livijl8+m5n/cUrqbuCRnNbi6QkDnhWwwMhhpRXQkJ2TIOleEYmjn5bfU0O6moKHdVgdmdFceAa5WkY6oGMp3a8n28x2FkWy0OPNHJASye0SnqH3Mw0xza5bAOXAo82U0//hlAKYYDSzxOO1JODmkN4A1Iy6KkhXM/CwkQ7T+NlzWwI8s5PMnTc2pAbuPBIo9WrStcoXGZKCHm9KV4W5ruZkS/UjDD8kBx76mwCwpifMsDlDuiQnXYJ1BgKUODNhWChdTUZbVg5FOr80VF9JAuj8ItIl3e9qdECyykk9qsRpiczChGF378Nlj5RTJwnphViz8mlDbpJhBqYczKhLaCRJgiQYvBI3S+pG0tU9evejSXiQpQ9pg5TCY4IgTHhKSU1rVWJxVtnwx8z7KkwYNgvP7Mw6YKgBDxJ+c6OIKl3M8tQqC78An0X15YD5wl3K1ixdxoOv1tkUwqOlDLTRwStdWVllxhGHR9INpKt5eNQGSTLPVeExlpmKqQYw28wt/DLXKifXnvJfVkGWquZI1+7hWQ65J4FHednqeYf7mnq4tPCr1lrlLTYXXD/ZSpZVnkS2FHjUUH+xuGslccaWnZy1yqdYYdg0PKrDvBE8psOswINEXUeL7j1rlYGdJaXFK13NwmOG6hvJFrq1Rh0ljHvmdoRqa5WlpbHuosGTMde12yThVtGWKVnGdVTpMuTMcz1u65JWkKkKr3R1z7797JHSwkpCa86oyvSE1UuNHoom5lDJojwct7Z5eu50CxFXPE63R7qag4f+D738ihnmed8WuP3Y2lLj8lGUEJ2d4xvhzcnLmKOPU7rOAs+UnyFQqMGp0N+efdfSGhZrdpuOmSIe+t3cMZrxcqUJTO8WHe/aZiiKBHbyomCeDZ6cfeqWw5q1Vx31TRy7QM1lGuBI5s1CI9GrKV2OZRrng6fSt1a44ckpGduHIelC1gk5stAI1Ag8qHTtBx5jucIm8IwNl+NjrHaAOpeoetc2u6Cu9G0au4CHHphGm6I1zBt/K5no7Co+mOtLm4zjPdJV+lU2m8ODTNhZBQEmbTACraROApCuU7othc6fAgGtLqkeenbunBr3hb77s8ETdv0t4AnLh4eGaKSEhQU8YQFPWMATFvCEBTwBT1jAExbwhAU8YQFPWNiR/QM1T98Kb4vycAAAAABJRU5ErkJggg=='
        };
        //Logo
        docPDF.addImage(logoBBVA.data, 'png', docPDF.xInitPosition, docPDF.yInitPosition, logoBBVA.weight/2, logoBBVA.height/2);

        //Operational Reporting Title
        docPDF.yPosition = docPDF.yPosition + 10;
        docPDF.setTextColor(13,86,159);
        docPDF.setFontType('bold');
        docPDF.text(110,docPDF.yPosition, 'Reporting Operacional');

        //Report Name
        docPDF.yPosition = docPDF.yPosition + 15;
        docPDF.setTextColor(51,154,204);
        docPDF.setFontSize(12);
        docPDF.text(110,docPDF.yPosition, reportData.name);

        //Separator Line
        docPDF.yPosition = docPDF.yPosition + 10;
        docPDF.setDrawColor(13,86,159);
        docPDF.setLineWidth(1);
        docPDF.line(docPDF.xInitPosition, docPDF.yPosition, docPDF.xMax - docPDF.xInitPosition, docPDF.yPosition);

        docPDF.yPosition = docPDF.yPosition + 20;
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addReportParams
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add report parameters to PDF for a report.
     * @param {object} docPDF The object with the configure PDF.
     * @param {object} reportData The report data.
     */
    function addReportParams(docPDF, reportData){

        docPDF.xPosition = docPDF.xInitPosition;

        if(reportData && reportData.params && reportData.params.length>0){

            docPDF.setFontSize(10);

            for(var i=0; i<reportData.params.length; i++){

                var param = reportData.params[i];

                if(param.value && param.value!=''){

                    //Param Name
                    docPDF.setTextColor(13,86,159);
                    docPDF.text(docPDF.xPosition,docPDF.yPosition, param.name + ':');

                    //Param Value
                    docPDF.xPosition = docPDF.xPosition + 68;
                    docPDF.setTextColor(51,154,204);
                    docPDF.text(docPDF.xPosition,docPDF.yPosition, param.value ? param.value : '');

                    // Inc.Y Position
                    var aux = i+1;
                    if((aux%2) === 0){
                        docPDF.xPosition = docPDF.xInitPosition;
                        docPDF.yPosition = docPDF.yPosition + 15;
                    }else{
                        docPDF.xPosition = docPDF.xMax/2;
                    }
                }
            }
            docPDF.yPosition = docPDF.yPosition + 15;

        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addReportCharts
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add SVG report charts to PDF for a report.
     * @param {object} docPDF The object with the configure PDF.
     * @param {object} reportData The report data.
     */
    function addReportCharts(docPDF, reportData){

        if(reportData.charts && reportData.charts.length > 0){

            var exportDivElement = document.getElementById('exportDiv');

            for(var i=0; i<reportData.charts.length; i++){

                var chartConfig = reportData.charts[i];

                // Chart SVG
                var svg = document.getElementById(chartConfig.idElement);

                if(svg) {

                    var svgAux = svg.cloneNode(true);

                    exportDivElement.appendChild(svgAux);

                    var png = convertSVGtoPNG(svgAux);

                    var chartImageSize = calculateChartImageSize(png, chartConfig, docPDF.xMax, docPDF.xInitPosition);

                    var addNewPage = addPageIfNecessary(docPDF, chartImageSize.height);

                    if(addNewPage){
                        addReportHead(docPDF, reportData);
                        addReportParams(docPDF, reportData);
                    }

                    // Chart Name
                    docPDF.yPosition = docPDF.yPosition + 5;
                    docPDF.setTextColor(13,86,159);
                    docPDF.text(docPDF.xInitPosition,docPDF.yPosition, chartConfig.name);
                    docPDF.yPosition = docPDF.yPosition + 5;

                    //Separator Line
                    docPDF.setDrawColor(13,86,159);
                    docPDF.setLineWidth(1);
                    docPDF.line(docPDF.xInitPosition, docPDF.yPosition, docPDF.xMax-docPDF.xInitPosition, docPDF.yPosition);
                    docPDF.yPosition = docPDF.yPosition + 5;

                    docPDF.addImage(png.data, 'png', docPDF.xInitPosition, docPDF.yPosition, chartImageSize.width,
                                        chartImageSize.height, undefined, 'none');

                    docPDF.yPosition = docPDF.yPosition + chartImageSize.height + 10;
                }
            }

            //Remove SGV Report Aux
            while (exportDivElement.hasChildNodes()) {
                exportDivElement.removeChild(exportDivElement.firstChild);
            }
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#calculateChartImageSize
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to calculate image size for a chart by configuration.
     * @param {object} pngChart The chart image.
     * @param {object} chartConfig The chart image export configuration.
     * @param {object} maxSize The max size width of the report.
     * @return {object} size The final size for a chart image.
     */
    function calculateChartImageSize(pngChart, chartConfig, maxSize){

        var size = {height: pngChart.height, width: pngChart.width};
        var maxChartWidth, newChartHeight, newChartWidth;

        if(chartConfig.size){
            size = chartConfig.size;

        }else if(chartConfig.flexible){
            // Stretch-Shrink Chart to Max Width
            maxChartWidth = maxSize + 10;
            newChartHeight =  pngChart.height * (maxChartWidth/pngChart.width);
            size = {height: newChartHeight, width: maxChartWidth};

        }else if(chartConfig.minWidth){
            // Stretch-Shrink Chart to Min Width
            newChartWidth = maxSize * chartConfig.minWidth;
            newChartHeight =  pngChart.height * (newChartWidth/pngChart.width);
            size = {height: newChartHeight, width: newChartWidth};
        }

        // Validate Max Height
        if(chartConfig.maxHeight && size.height > chartConfig.maxHeight){

            // Stretch Chart to Max Height
            newChartHeight = chartConfig.maxHeight;
            newChartWidth =  pngChart.width * (chartConfig.maxHeight/pngChart.height);
            size = {height: newChartHeight, width: newChartWidth};

        }else if(size.height > 350){

            // Stretch Chart to Max Height
            newChartHeight = 350;
            newChartWidth =  pngChart.width * (350/pngChart.height);

            size = {height: newChartHeight, width: newChartWidth};
        }
        return size;
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addReportTables
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add report data tables to PDF.
     * @param {object} docPDF The object with the configure PDF.
     * @param {object} reportData The chart image export configuration.
     */
    function addReportTables(docPDF, reportData){

        if(reportData.tables && reportData.tables.length > 0){

            docPDF.yPosition = docPDF.yPosition + 5;

            for(var i=0; i<reportData.tables.length; i++) {

                var table = reportData.tables[i];

                // Table Name
                docPDF.yPosition = docPDF.yPosition + 2;
                docPDF.setTextColor(13,86,159);
                docPDF.text(docPDF.xInitPosition,docPDF.yPosition, table.name);
                docPDF.yPosition = docPDF.yPosition + 5;

                // Table Data
                var optionsDataTable = {
                    padding: 2, // Vertical cell padding
                    fontSize: 8,
                    lineHeight: 15,
                    margins: { horizontal: 25, top: docPDF.yPosition, bottom: 25}, // How much space around the table
                    extendWidth: true // If true, the table will span 100% of page width minus horizontal margins.
                };

                docPDF.autoTable(table.columns, table.data, optionsDataTable);
            }
        }
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addPageIfNecessary
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add new page to the report if is necessary.
     * @param {object} docPDF The object with the configure PDF.
     * @param {object} newElementHeight The height of the new element to add.
     */
    function addPageIfNecessary(docPDF, newElementHeight){

        var yFinalPosition = docPDF.yPosition + newElementHeight + 25; //Margin-Bottom = 25
        if(yFinalPosition > docPDF.yMax){

            docPDF.xPosition = docPDF.xInitPosition;
            docPDF.yPosition = docPDF.yInitPosition;
            docPDF.addPage();

            return true;
        }
        return false;
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#addStylesToSVGElements
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to add all computed styles to svg.
     * @param {object} svgElement The sgv element node.
     */
    function addStylesToSVGElements(svgElement){

        var child;

        for (var i = 0; i < svgElement.childNodes.length; i++){

            child = svgElement.childNodes[i];

            addStylesToSVGElements(child);

            var elementComputedCssStyles = null;

            if(child.nodeName == 'rect'){
                child.cssText ="fill-opacity: 1;" + child.parentNode.parentNode.cssText;
            }else{
                if(child.nodeType == 1){
                    elementComputedCssStyles = child;
                }else{
                    if(child.parentElement.nodeType == 1){
                        elementComputedCssStyles = child.parentElement;
                    }
                }
                if(elementComputedCssStyles){
                    var cssStyle = window.getComputedStyle(elementComputedCssStyles);
                    if(cssStyle && cssStyle.cssText){
                        elementComputedCssStyles.style.cssText = cssStyle.cssText;
                    }
                }
            }
        }
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#convertSVGtoPNG
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to convert a SVG to PNG.
     * @param {object} svgElement The SVG element node.
     * @return {object} The result png data.
     */
    function convertSVGtoPNG(svgElement) {

      var ctx, canvas, svgData, img;

      addStylesToSVGElements(svgElement);

      svgData = '<svg xmlns="http://www.w3.org/2000/svg" width="' + svgElement.offsetWidth +
          '" height="' + svgElement.offsetHeight + '">' + svgElement.innerHTML + '</svg>';

      img = new Image();
      img.src = 'data:image/svg+xml,' + encodeURIComponent(svgData);

      // Draw the SVG image to a canvas
      canvas = document.createElement('canvas');
      canvas.width = svgElement.offsetWidth;
      canvas.height = svgElement.offsetHeight;
      ctx = canvas.getContext('2d');
      ctx.drawImage(img, 0, 0);

      return {
          data: canvas.toDataURL('image/png'),
          width: svgElement.offsetWidth/2,
          height: svgElement.offsetHeight/2
      };
    }

    /**
     * @ngdoc method
     * @name bbva.or.services.exportService#exportReportCharts
     * @methodOf bbva.or.services.exportService
     * @description
     * Method to export report charts to PDF.
     * @param {object} reportData The report data.
     * @return {object} The object with the PDF.
     */
    function exportReportCharts(reportData){

      // Report Configuration
      reportData.config.orientation = 'portrait';
      var docPDF = configureReport(reportData);

      // Report Head
      addReportHead(docPDF, reportData);

      // Report Params
      addReportParams(docPDF, reportData);

      // Report Charts
      addReportCharts(docPDF, reportData);

      //Download PDF
      docPDF.save('Graficas:' + reportData.name + '.pdf');

      return docPDF;
    }

    /* Public API */
    return({
      exportReportToPDF: exportReportToPDF,
      exportReportToCSV: exportReportToCSV
    });
});

/**
 * @ngdoc service
 * @name bbva.or.services.dashboardConfigService
 * @description
 * Service to load/save the dashboard configuration pages od the user.
 */
angular.module('bbva.or.services').service("dashboardConfigService", function($rootScope, $http, $q, $location, localStorageService, restService, logService){

    // TODO EN ESTE METODO SE DEBEN CARGAR LAS CONFIGURACION DEL DASHBOARD PARA EL USUARIO EN FUNCION DEL GRUPO/DASHBOARD DESDE EL SERVIDOR

    /**
     * @ngdoc method
     * @name bbva.or.services.dashboardConfigService#getMainDashboardConfig
     * @methodOf bbva.or.services.dashboardConfigService
     * @description
     * Method to load dashboard configuration by ID
     * @return {object} The dashboard configuration
     */
    function getApplicationDashboardConfig(){

        var appDashboard = null;

        if($rootScope.appConfig.dynamicDashboard && $rootScope.user.dashboard){
            appDashboard = $rootScope.user.dashboard;
        }

        if(appDashboard==null){

            if($rootScope.appConfig.defaultDashboard){
                appDashboard = $rootScope.appConfig.defaultDashboard;
            }else{
                appDashboard = {
                    title:'Dashboad X',
                    structure: '6-6',
                    configurable: false,
                    rows: [{columns: [{styleClass: 'col-md-12',widgets: []}]},{columns:[{styleClass: 'col-md-6', widgets: []},{styleClass: 'col-md-6', widgets: []}]}]
                }
            }
        }

        return appDashboard;

        /*
         TODO MODELO DE SERVIDOR.. ESTO ES POR SI SE HACE LA CARGA DEL DASHBOARD POR USUARIO Y BAJO DEMANDA
         var dashboardModel = localStorageService.get(dashboardID);
         if(dashboardModel == null){
         var serviceId = 'getDashboardPageConfig';
         var service = restService.getService(serviceId);

         if (service){
         var request = $http({
         method: service.method,
         url: restService.generateServiceEndPoint(service.endPoint)
         });
         return (request.then(handleSuccess, handleError));
         } else {
         logService.error('Service Error: The service not exist: ' + serviceId);
         return null;
         }
         }*/
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.dashboardConfigService#getUserDashboardConfig
     * @methodOf bbva.or.services.dashboardConfigService
     * @description
     * Method to load user dashboard configuration by ID
     * @param {number|string} dashboardID The id of the dashboard.
     * @return {object} The dashboard configuration
     */
    function getUserDashboardConfig(dashboardID){
        
        var model = localStorageService.get(dashboardID+"-"+$rootScope.user.id);

        // Search in Dashboard Pages Configs
        if(model==null) {
            if ($rootScope.user && $rootScope.user.dashboardPages) {
                angular.forEach($rootScope.user.dashboardPages, function (dashboardPage, key) {

                    if (dashboardPage.id == dashboardID) {
                        model = dashboardPage;
                    }
                });
            }
        }

        // Search in User Page Configs
        if(model==null) {
            if($rootScope.user && $rootScope.user.userPages){
                angular.forEach($rootScope.user.userPages, function (userPage, key){
                    if (userPage.id == dashboardID){
                        model = userPage;
                    }
                });
            }
        }

        return model;

        /*
         CARGA DESDE SERVIDOR
         var model = localStorageService.get(name);
         if(!model){

         var serviceId = 'getDashboardPageConfig';
         var service = restService.getService(serviceId);

         if(service){
         var request = $http({
         method: service.method,
         url: restService.generateServiceEndPoint(service.endPoint)
         });
         return(request.then(handleSuccess,handleError));
         }else{
         logService.error('Service Error: The service not exist: '+ serviceId);
         return null;
         }
         }else{
            return model;
         }*/
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.dashboardConfigService#updateDashboardConfig
     * @methodOf bbva.or.services.dashboardConfigService
     * @description
     * Method to update the user dashboard configuration.
     * @param {object} page The dashboard page id.
     * @param {object} model The new configuration of the dashboard page.
     */
    function updateDashboardConfig(page, model){

        // Guardamos la pagina en local
        var pageUID = model.id ? model.id : page;
        localStorageService.set(pageUID + "-" + $rootScope.user.id, model);

        //TODO GUARDAR LA CONFIGURACIÓN DE LA PAGINA EN EL SERVIDOR
        /* var serviceId = 'updateDashboardPageConfig';
        var service = restService.getService(serviceId);
        if(service){
            var request = $http({
                method: service.method,
                url: restService.generateServiceEndPoint(service.endPoint),
                data:{
                    user: user.id,
                    page: page,
                    model: model
                }
            });
            return(request.then(handleSuccess,handleError));
        }else{
            logService.error('Service Error: The service not exist. ID: '+ serviceId);
            return false;
        }*/

        //Actualizamos las paginas del usuario
        if(model.userPage){

            if(!$rootScope.user.userPages){
                $rootScope.user.userPages = [];
            }
            var existPage = false;
            angular.forEach($rootScope.user.userPages, function(userPage, key){
                if(userPage.id == model.id){
                    existPage = true;
                }
            });

            if(!existPage){
                $rootScope.user.userPages.push(model);
            }

            localStorageService.set('user.'+$rootScope.user.id , $rootScope.user);
        }
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.dashboardConfigService#removeUserDashboard
     * @methodOf bbva.or.services.dashboardConfigService
     * @description
     * Method to remove the user dashboard.
     * @param {object} model The dashboard configuration model.
     */
    function removeUserDashboard(model){

        //Borramos de local
        localStorageService.remove(model.id + "-" + $rootScope.user.id);

        //TODO BORRAMOS EN EL SERVIDOR LA PAGINA DEL USUARIO
        /* var serviceId = 'updateDashboardPageConfig';
         var service = restService.getService(serviceId);
         if(service){
         var request = $http({
         method: service.method,
         url: restService.generateServiceEndPoint(service.endPoint),
         data:{
         user: user.id,
         page: page,
         model: model
         }
         });
         return(request.then(handleSuccess,handleError));
         }else{
         logService.error('Service Error: The service not exist. ID: '+ serviceId);
         return false;
        }*/

        //Actualizamos las paginas del usuario
        if($rootScope.user.userPages){

            var userPagesAux = [];
            angular.forEach($rootScope.user.userPages, function(userPage, key) {
                if(userPage.id != model.id){
                    userPagesAux.push(userPage);
                }
            });

            $rootScope.user.userPages = userPagesAux;

            localStorageService.set('user.'+$rootScope.user.id , $rootScope.user);

            $location.path('/dashboard');
        }
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.dashboardConfigService#handleError
     * @methodOf bbva.or.services.dashboardConfigService
     * @description
     * Method to handle the errors during the request.
     * @param {object} response The response of the request.
     * @return {string} The error message.
     */
    function handleError( response ) {
        logService.error('Service Error: Response: '+ response);
        if (!angular.isObject(response.data) || !response.data.message){
            return( $q.reject( 'An unknown error occurred.' ) );
        }
        return( $q.reject( response.data.message));
    }


    /**
     * @ngdoc method
     * @name bbva.or.services.dashboardConfigService#handleSuccess
     * @methodOf bbva.or.services.dashboardConfigService
     * @description
     * Method to handle loading the process hierarchy of application in the success case
     * @param {object} response The response of the request.
     * @return {object|Array} The response data of the request.
     */
    function handleSuccess(response){
        return(response.data);
    }

    /* Public API */
    return({
        getApplicationDashboardConfig: getApplicationDashboardConfig,
        getUserDashboardConfig: getUserDashboardConfig,
        updateDashboardConfig: updateDashboardConfig,
        removeUserDashboard: removeUserDashboard
    });

});
