'use strict';


/**
 * @ngdoc overview
 * @name bbva.or.db
 * @description
 * # bbva.or.db
 * Module with main dashboard components.
 */
angular.module('bbva.or.db', ['bbva.or.db.provider', 'ui.bootstrap', 'ui.sortable'])
    .value('adfTemplatePath', 'templates/')
    .value('rowTemplate', '<bbva-dashboard-row row="row" ng-repeat="row in column.rows" edit-mode="{{editMode}}" sortable-options="sortableOptions"></bbva-dashboard-row>')
    .value('columnTemplate', '<bbva-dashboard-column column="column" ng-repeat="column in row.columns" edit-mode="{{editMode}}" sortable-options="sortableOptions"></bbva-dashboard-column>')
    .value('adfVersion', '<<adfVersion>>');



/**
 * @ngdoc directive
 * @name bbva.or.db.directive:bbvaDashboardColumn
 * @element div
 * @restrict EA
 * @scope
 * @description
 *
 * `bbvaDashboardColumn` is a directive which renders a dashboard column with all its
 * components.
 */
angular.module('bbva.or.db')
    .directive('bbvaDashboardColumn', function ($compile, adfTemplatePath, rowTemplate) {

      function stringToBoolean(string){
        switch(string !== null ? string.toLowerCase() : null){
          case 'true': case 'yes': case '1': return true;
          case 'false': case 'no': case '0': case null: return false;
          default: return Boolean(string);
        }
      }

      return {
        restrict: 'E',
        replace: true,
        scope: {
          column: '=',
          editMode: '@',
          sortableOptions: '='
        },
        templateUrl: adfTemplatePath + 'dashboard-column.html',
        link: function ($scope, $element, $attr) {
          // pass edit mode
          $attr.$observe('editMode', function (value) {
            $scope.editMode = stringToBoolean(value);
          });

          if (angular.isDefined($scope.column.rows) && angular.isArray($scope.column.rows)) {
            // be sure to tell Angular about the injected directive and push the new row directive to the column
            $compile(rowTemplate)($scope, function (cloned) {
              $element.append(cloned);
            });
          }
        }
      };
    });



/**
 * @ngdoc directive
 * @name bbva.or.db.directive:bbvaDashboardRow
 * @element div
 * @restrict EA
 * @scope
 * @description
 *
 * `bbvaDashboardRow` is a directive which renders a dashboard row with all its
 * components.
 */
angular.module('bbva.or.db')
    .directive('bbvaDashboardRow', function ($compile, adfTemplatePath, columnTemplate) {


      function stringToBoolean(string) {
        switch (string !== null ? string.toLowerCase() : null) {
          case 'true': case 'yes': case '1': return true;
          case 'false': case 'no': case '0': case null: return false;
          default: return Boolean(string);
        }
      }
      return {
        restrict: 'E',
        replace: true,
        scope: {
          row: '=',
          editMode: '@',
          sortableOptions: '='
        },
        templateUrl: adfTemplatePath + 'dashboard-row.html',
        link: function ($scope, $element, $attr) {
          // pass edit mode
          $attr.$observe('editMode', function (value) {
            $scope.editMode = stringToBoolean(value);
          });

          if (angular.isDefined($scope.row.columns) && angular.isArray($scope.row.columns)) {
            $compile(columnTemplate)($scope, function (cloned) {
              $element.append(cloned);
            });
          }
        }
      };
    });

/**
 * @ngdoc directive
 * @name bbva.or.db.directive:ngBbvaWidget
 * @element div
 * @restrict EA
 * @scope
 * @description
 *
 * `ngBbvaWidget` is a directive which renders a report widget.
 */
angular.module('bbva.or.db')
    .directive('ngBbvaWidget', function($log, $modal, dashboard, adfTemplatePath) {

      function stringToBoolean(string){
        switch(angular.isDefined(string) ? string.toLowerCase() : null){
          case 'true': case 'yes': case '1': return true;
          case 'false': case 'no': case '0': case null: return false;
          default: return Boolean(string);
        }
      }

      function preLink($scope, $element, $attr){
        var definition = $scope.definition;
        if (definition) {
          var w = dashboard.widgets[definition.type];
          if (w) {
            // pass title
            if (!definition.title){
              definition.title = w.title;
            }

            // pass edit mode
            $attr.$observe('editMode', function(value){
              $scope.editMode = stringToBoolean(value);
            });

            // pass copy of widget to scope
            $scope.widget = angular.copy(w);

            // create config object
            var config = definition.config;
            if (config){
              if (angular.isString(config)){
                config = angular.fromJson(config);
              }
            } else {
              config = {};
            }

            // pass config to scope
            $scope.config = config;

            // convert collapsible to string
            $scope.collapsible = stringToBoolean($scope.collapsible);

            // collapse
            $scope.isCollapsed = false;
          } else {
            $log.warn('could not find widget ' + definition.type);
          }
        } else {
          $log.debug('definition not specified, widget was probably removed');
        }
      }

      function postLink($scope, $element) {
        var definition = $scope.definition;
        if (definition) {
          // bind close function
          $scope.close = function() {
            var column = $scope.col;
            if (column) {
              var index = column.widgets.indexOf(definition);
              if (index >= 0) {
                column.widgets.splice(index, 1);
              }
            }
            $element.remove();
          };

          // bind reload function
          $scope.reload = function(){
            $scope.$broadcast('widgetReload');
          };

          $scope.setFilterMode = function(isMasterFilter){
            $scope.config.masterFilter = isMasterFilter;
            $scope.$broadcast('widgetConfigChanged');
          };



          // bind edit function
          $scope.edit = function() {
            var editScope = $scope.$new();

            var opts = {
              scope: editScope,
              templateUrl: adfTemplatePath + 'widget-edit.html'
            };

            var instance = $modal.open(opts);
            editScope.closeDialog = function() {
              instance.close();
              editScope.$destroy();

              var widget = $scope.widget;
              if (widget.edit && widget.edit.reload){
                // reload content after edit dialog is closed
                $scope.$broadcast('widgetConfigChanged');
              }
            };
          };
        } else {
          $log.debug('widget not found');
        }
      }

      return {
        replace: true,
        restrict: 'EA',
        transclude: false,
        templateUrl: adfTemplatePath + 'widget.html',
        scope: {
          definition: '=',
          col: '=column',
          editMode: '@',
          collapsible: '='
        },
        compile: function compile() {
          return {
            pre: preLink,
            post: postLink
          };
        }
      };

    });



/**
 * @ngdoc directive
 * @name bbva.or.db.directive:ngBbvaWidgetContent
 * @element div
 * @restrict EA
 * @scope
 * @description
 * `ngBbvaWidgetContent` is a directive which renders a report widget content.
 */
angular.module('bbva.or.db')
    .directive('ngBbvaWidgetContent', function($log, $q, $sce, $http, $templateCache, $compile, $controller, $injector, dashboard) {

      function getTemplate(widget){
        var deferred = $q.defer();

        if ( widget.template ){
          deferred.resolve(widget.template);
        } else if (widget.templateUrl) {
          var url = $sce.getTrustedResourceUrl(widget.templateUrl);
          $http.get(url, {cache: $templateCache})
              .success(function(response){
                deferred.resolve(response);
              })
              .error(function(){
                deferred.reject('could not load template');
              });
        }

        return deferred.promise;
      }

      function compileWidget($scope, $element) {
        var model = $scope.model;
        var content = $scope.content;

        // display loading template
        $element.html(dashboard.loadingTemplate);

        // create new scope
        var templateScope = $scope.$new();

        // pass config object to scope
        if (!model.config) {
          model.config = {};
        }

        templateScope.config = model.config;

        // local injections
        var base = {
          $scope: templateScope,
          widget: model,
          config: model.config
        };

        // get resolve promises from content object
        var resolvers = {};
        resolvers.$tpl = getTemplate(content);
        if (content.resolve) {
          angular.forEach(content.resolve, function(promise, key) {
            if (angular.isString(promise)) {
              resolvers[key] = $injector.get(promise);
            } else {
              resolvers[key] = $injector.invoke(promise, promise, base);
            }
          });
        }

        // resolve all resolvers
        $q.all(resolvers).then(function(locals) {
          angular.extend(locals, base);

          // compile & render template
          var template = locals.$tpl;
          $element.html(template);
          if (content.controller) {
            var templateCtrl = $controller(content.controller, locals);
            if (content.controllerAs){
              templateScope[content.controllerAs] = templateCtrl;
            }
            $element.children().data('$ngControllerController', templateCtrl);
          }
          $compile($element.contents())(templateScope);
        }, function(reason) {
          // handle promise rejection
          var msg = 'Could not resolve all promises';
          if (reason) {
            msg += ': ' + reason;
          }
          $log.warn(msg);
          $element.html(dashboard.messageTemplate.replace(/{}/g, msg));
        });
      }

      return {
        replace: true,
        restrict: 'EA',
        transclude: false,
        scope: {
          model: '=',
          content: '='
        },
        link: function($scope, $element) {
          compileWidget($scope, $element);
          $scope.$on('widgetConfigChanged', function(){
            compileWidget($scope, $element);
          });
          $scope.$on('widgetReload', function(){
            compileWidget($scope, $element);
          });
        }
      };

  });




/**
 * @ngdoc directive
 * @name bbva.or.db.directive:bbvaDashboard
 * @element div
 * @restrict EA
 * @scope
 * @description
 *
 * `bbvaDashboard` is a directive which renders the dashboard with all its
 * components. The directive requires a name attribute. The name of the
 * dashboard can be used to store the model.
 */
angular.module('bbva.or.db')
  .directive('bbvaDashboard', function ($rootScope, $route, $log, $modal, dashboard, adfTemplatePath, dashboardConfigService) {

    /**
     * @ngdoc method
     * @name bbva.or.db.directive:bbvaDashboard#copyWidgets
     * @methodOf bbva.or.db.directive:bbvaDashboard
     * @description
     *
     * Copy widget from source to target.
     *
     * @param {object} source of the widget
     * @param {object} target to be registered.
     */
    function copyWidgets(source, target){

      if (source.widgets && source.widgets.length > 0){
        var w = source.widgets.shift();
        while (w){
          target.widgets.push(w);
          w = source.widgets.shift();
        }
      }
    }


    /**
     * @ngdoc method
     * @name bbva.or.db.directive:bbvaDashboard#fillStructure
     * @methodOf bbva.or.db.directive:bbvaDashboard
     * @description
     *
     * Fill structure with the widgets of dashboard page.
     *
     * @param {object} root the root element of the dashboard page model.
     * @param {object|Array} columns the columns of dashboard page.
     * @param {object} counter the counter for control iteration.
     */
    function fillStructure(root, columns, counter) {

        counter = counter || 0;

        if (angular.isDefined(root.rows)) {
            angular.forEach(root.rows, function (row) {
                angular.forEach(row.columns, function (column) {
                    // if the widgets prop doesn't exist, create a new array for it.
                    // this allows ui.sortable to do it's thing without error
                    if (!column.widgets) {
                        column.widgets = [];
                    }

                    // if a column exist at the counter index, copy over the column
                    if (angular.isDefined(columns[counter])) {
                        copyWidgets(columns[counter], column);
                        counter++;
                    }

                    // run fillStructure again for any sub rows/columns
                    counter = fillStructure(column, columns, counter);
                });
            });
        }
        return counter;
    }

    /**
       * @ngdoc method
       * @name bbva.or.db.directive:bbvaDashboard#readColumns
       * @methodOf bbva.or.db.directive:bbvaDashboard
       * @description
       *
       * Method to read columns of the structure.
       *
       * @param {object} root element of the dashboard page model.
       * @param {object|Array} columns the columns of dashboard page.
     */
    function readColumns(root, columns) {

        columns = columns || [];

        if (angular.isDefined(root.rows)) {
            angular.forEach(root.rows, function (row) {
                angular.forEach(row.columns, function (col) {
                    columns.push(col);
                    readColumns(col, columns);
                });
            });
        }
        return columns;
    }

    /**
     * @ngdoc method
     * @name bbva.or.db.directive:bbvaDashboard#changeStructure
     * @methodOf bbva.or.db.directive:bbvaDashboard
     * @description
     *
     * Method to change structure of the dashboard page.
     *
     * @param {object} model the dashboard page model.
     * @param {object} structure the new structure config.
     * @param {object} structureId the new structure ID.
     */
    function changeStructure(model, structure, structureId){
      var columns = readColumns(model);
      var counter = 0;

      model.rows = angular.copy(structure.rows);
      if(structureId)
        model.structure =  structureId;

      while ( counter < columns.length ){
        counter = fillStructure(model, columns, counter);
      }
    }

    /**
     * @ngdoc method
     * @name bbva.or.db.directive:bbvaDashboard#createConfiguration
     * @methodOf bbva.or.db.directive:bbvaDashboard
     * @description
     *
     * Method to init widget configuration by type
     *
     * @param {object} type of the widget
     * @return {object} init configuration for the widget
     */
    function createConfiguration(type){
      var cfg = {};
      var config = dashboard.widgets[type].config;
      if (config){
        cfg = angular.copy(config);
      }
      return cfg;
    }

    return {
      replace: true,
      restrict: 'EA',
      transclude : false,
      scope: {
        structure: '@',
        name: '@',
        collapsible: '@',
        adfModel: '=',
        adfWidgetFilter: '='
      },
      controller: function($scope){
        // sortable options for drag and drop
        $scope.sortableOptions = {
          connectWith: '.column',
          handle: '.fa-arrows',
          cursor: 'move',
          tolerance: 'pointer',
          placeholder: 'placeholder',
          forcePlaceholderSize: true,
          opacity: 0.4
        };


        var model = {};
        var structure = {};
        var widgetFilter = {};
        var structureName = {};
        var name = $scope.name;

        // Watching for changes on adfModel
        $scope.$watch('adfModel', function(oldVal, newVal) {
          
          if (newVal !== null) {
            model = $scope.adfModel;
            widgetFilter = $scope.adfWidgetFilter;
            if ( ! model || ! model.rows ){
              structureName = $scope.structure;
              structure = dashboard.structures[structureName];
              if (structure){
                if (model){
                  model.rows = angular.copy(structure).rows;
                } else {
                  model = angular.copy(structure);
                }
                model.structure = structureName;
              } else {
                $log.error( 'could not find structure ' + structureName);
              }
            }

            if (model) {
              if (!model.title){
                model.title = 'Dashboard';
              }
              $scope.model = model;
            } else {
              $log.error('could not find or create model');
            }
          }
        }, true);

        $rootScope.$on('adfDashboardPageSave', function (event){
          
          $rootScope.$broadcast('adfDashboardChanged', name, model);
        });

        // edit mode
        $scope.editMode = false;
        $scope.editClass = '';

        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#toggleEditMode
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         *
         * Method to toggle dashboard page edit mode
         *
         */
        $scope.toggleEditMode = function(){

          $scope.editMode = ! $scope.editMode;
          if ($scope.editMode){
             $scope.modelCopy = angular.copy($scope.adfModel, {});
          }
          if(!$scope.editMode){
            $rootScope.$broadcast('adfDashboardChanged', name, model);
          }
        };

        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#cancelEditMode
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         *
         * Method to cancel dashboard page edit mode without saving changes
         *
         */
        $scope.cancelEditMode = function(){
          $scope.editMode = false;
          $scope.modelCopy = angular.copy($scope.modelCopy, $scope.adfModel);
        };

        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#editDashboardDialog
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         *
         * Method to edit dashboard name and structure.
         *
         */
        $scope.editDashboardDialog = function(){
          var editDashboardScope = $scope.$new();
          editDashboardScope.structures = dashboard.structures;
          var instance = $modal.open({
            //size: 'lg',
            windowClass: 'center-modal',
            scope: editDashboardScope,
            templateUrl: adfTemplatePath + 'dashboard-edit.html'
          });
          $scope.changeStructure = function(name, structure){
            
            $log.info('change structure to ' + name);
            changeStructure(model, structure, name);
          };
          editDashboardScope.closeDialog = function(){
            instance.close();
            editDashboardScope.$destroy();
          };
        };


        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#addWidgetDialog
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         * Method to show the list of the widget for adding widget to dashboard page
         */
        $scope.addWidgetDialog = function(){

          var addScope = $scope.$new();
          var widgets;

          /**
           * TODO... DE MOMENTO NO FILTRAMOS LOS INFORMES EN FUNCION DE PERMISOS... PERO LLEGARA UN MOMENTO QUE TENDREMOS QUE HACERLO
           * if (angular.isFunction(widgetFilter)){
            widgets = {};
            // jshint ignore:start
            angular.forEach(dashboard.widgets, function(widget, type){
              if (widgetFiHome.jslter(widget, type)){
                widgets[type] = widget;
              }
            });
            // jshint ignore:end
          } else {
            widgets = dashboard.widgets;
          }*/
          widgets = dashboard.widgets;

          addScope.widgets = widgets;
          var opts = {
            size: 'lg',
            windowClass: 'center-modal',
            scope: addScope,
            templateUrl: adfTemplatePath + 'widget-add.html'
          };

          /* Widgets Grid
          addScope.widgetsGrid = {
               data: 'widgets',
              //showGroupPanel: true,
                enablePaging: true,
              //showFooter: true,
                showFilter: true,
                totalServerItems: 'totalServerItems',
                pagingOptions: $scope.pagingOptions,
                filterOptions: $scope.filterOptions,
              //showSelectionCheckbox: true,
                enableColumnResize: true,
              //enablePinning: true,
                enableColumnReordering: true,
                enableRowSelection: true,
                multiSelect: false,
                selectedItems: $scope.mySelections,
                afterSelectionChange: function(row, evt){
              //	        	$scope.equipment = row.entity;
              //	        	$window.location.href = '#/equipments/'+$scope.equipment.id;
              //	        	$window.location.reload();
            },
            //rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>',
            columnDefs: [
              {field:'uid', displayName:'Equipment', cellTemplate: '<a ng-click="loadById(row)"> <img src="{{loadImage(row)}}" class="elemIco"/> <div class="ngCellText">{{row.getProperty(\'uid\')}}</div> </a>'},
              {field:'alias', displayName:'Alias'},
              {field:'enabled', displayName:'Enabled'},
              {field:'realLat', displayName:'Latitude'},
              {field:'realLon', displayName:'Longitude'}
            ]
          };*/


          var instance = $modal.open(opts);
          addScope.addWidget = function(widget){

            var w = {
              type: widget,
              config: createConfiguration(widget)
            };
            addScope.model.rows[0].columns[0].widgets.unshift(w);
            instance.close();

            addScope.$destroy();
          };

          addScope.closeDialog = function(){
            instance.close();
            addScope.$destroy();
          };
        };


        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#removeDashboard
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         *
         * Method to remove a user dashboard page.
         */
        $scope.removeDashboard = function(){
            dashboardConfigService.removeUserDashboard($scope.model);
        };

        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#importDashboardDialog
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         *
         * Method to show the import dashboard page configuration dialog.
         */
        $scope.importDashboardDialog = function(){

          var importDashboardScope = $scope.$new();
          importDashboardScope.file = null;
          importDashboardScope.dashBoardConfig;


          importDashboardScope.$on("fileSelected", function (event, args){
            importDashboardScope.$apply(function (){
              importDashboardScope.file = (args.file);
            });
          });

          var instance = $modal.open({
            // size: 'lg',
            windowClass: 'center-modal',
            scope: importDashboardScope,
            templateUrl: adfTemplatePath + 'dashboardPage-import.html'
          });

          /**
           * @ngdoc method
           * @name bbva.or.db.directive:bbvaDashboard#importDashboard
           * @methodOf bbva.or.db.directive:bbvaDashboard
           * @description
           *
           * Method to import dashboard page configuration.
           */
          importDashboardScope.importDashboard = function(){
            debugger;
            if(importDashboardScope.file){

              var fileReader = new FileReader();
              fileReader.onload = function(e){
                debugger;
                importDashboardScope.dashBoardConfig = angular.fromJson(e.target.result);
                console.log(importDashboardScope.dashBoardConfig);

                if(importDashboardScope.dashBoardConfig){

                  importDashboardScope.dashBoardConfig.title = model.title;

                  if(model.id){
                    importDashboardScope.dashBoardConfig.id = model.id;
                  }else{
                    importDashboardScope.dashBoardConfig.id = undefined;
                  }

                  model = importDashboardScope.dashBoardConfig;

                  $rootScope.$broadcast('adfDashboardChanged', model.title, model);

                  $route.reload();
                }
              };

              fileReader.readAsText(importDashboardScope.file);
              instance.close();

            }else{

              //TODO MENSAJITO DE QUE NO SE HA SELECCIONADO UN FICHERO
            }
          };

          importDashboardScope.close = function(){
            instance.close();
            importDashboardScope.$destroy();
          };
        };

        /**
         * @ngdoc method
         * @name bbva.or.db.directive:bbvaDashboard#exportDashboard
         * @methodOf bbva.or.db.directive:bbvaDashboard
         * @description
         *
         * Method to export the dashboard page configuration.
         */
        $scope.exportDashboard = function(){

          var dashboardConfig = angular.toJson(model, true);

          var downloadLink = document.createElement('a');
          downloadLink.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(dashboardConfig));
          downloadLink.setAttribute('download', 'BBVA-OR-'+ model.title + '.cfg');

          downloadLink.style.display = 'none';
          document.body.appendChild(downloadLink);

          downloadLink.click();
          document.body.removeChild(downloadLink);
        };

      },
      link: function ($scope, $element, $attr) {
        // pass attributes to scope
        $scope.name = $attr.name;
        $scope.structure = $attr.structure;
      },
      templateUrl: adfTemplatePath + 'dashboard.html'
    };
  });
