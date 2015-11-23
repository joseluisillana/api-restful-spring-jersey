'use strict';

/**
 * @ngdoc object
 * @name bbva.or.db.dashboardProvider
 * @description
 *
 * The dashboardProvider can be used to register structures and widgets.
 */
angular.module('bbva.or.db.provider', [])
  .provider('dashboard', function(){

    var widgets = {};
    var structures = {};
    var messageTemplate = '<div class="alert alert-danger">{}</div>';

    /*jshint multistr: true */
    var loadingTemplate = '\
      <div class="progress progress-striped active">\n\
        <div class="progress-bar" role="progressbar" style="width: 100%">\n\
          <span class="sr-only">loading ...</span>\n\
        </div>\n\
      </div>';

   /**
    * @ngdoc method
    * @name bbva.or.db.dashboardProvider#widget
    * @methodOf bbva.or.db.dashboardProvider
    * @description
    *
    * Registers a new widget.
    *
    * @param {string} name of the widget
    * @param {object} widget to be registered.
    *
    *   Object properties:
    *
    *   - `title` - `{string=}` - The title of the widget.
    *   - `description` - `{string=}` - Description of the widget.
    *   - `config` - `{object}` - Predefined widget configuration.
    *   - `controller` - `{string=|function()=}` - Controller fn that should be
    *      associated with newly created scope of the widget or the name of a
    *      {@link http://docs.angularjs.org/api/angular.Module#controller registered controller}
    *      if passed as a string.
    *   - `controllerAs` - `{string=}` - A controller alias name. If present the controller will be
    *      published to scope under the `controllerAs` name.
    *   - `template` - `{string=|function()=}` - html template as a string.
    *   - `templateUrl` - `{string=}` - path to an html template.
    *   - `reload` - `{boolean=}` - true if the widget could be reloaded. The default is false.
    *   - `resolve` - `{Object.<string, function>=}` - An optional map of dependencies which should
    *      be injected into the controller. If any of these dependencies are promises, the widget
    *      will wait for them all to be resolved or one to be rejected before the controller is
    *      instantiated.
    *      If all the promises are resolved successfully, the values of the resolved promises are
    *      injected.
    *
    *      The map object is:
    *      - `key` – `{string}`: a name of a dependency to be injected into the controller.
    *      - `factory` - `{string|function}`: If `string` then it is an alias for a service.
    *        Otherwise if function, then it is {@link http://docs.angularjs.org/api/AUTO.$injector#invoke injected}
    *        and the return value is treated as the dependency. If the result is a promise, it is
    *        resolved before its value is injected into the controller.
    *   - `edit` - `{object}` - Edit modus of the widget.
    *      - `controller` - `{string=|function()=}` - Same as above, but for the edit mode of the widget.
    *      - `template` - `{string=|function()=}` - Same as above, but for the edit mode of the widget.
    *      - `templateUrl` - `{string=}` - Same as above, but for the edit mode of the widget.
    *      - `resolve` - `{Object.<string, function>=}` - Same as above, but for the edit mode of the widget.
    *      - `reload` - {boolean} - true if the widget should be reloaded, after the edit mode is closed.
    *        Default is true.
    *
    * @returns {Object} self
    */
    this.widget = function(name, widget){
      var w = angular.extend({reload: false}, widget);
      if ( w.edit ){
        var edit = {reload: true};
        angular.extend(edit, w.edit);
        w.edit = edit;
      }
        w.type = name;
      widgets[name] = w;
      return this;
    };

   /**
    * @ngdoc method
    * @name bbva.or.db.dashboardProvider#structure
    * @methodOf bbva.or.db.dashboardProvider
    * @description
    *
    * Registers a new structure.
    *
    * @param {string} name of the structure
    * @param {object} structure to be registered.
    *
    *   Object properties:
    *
    *   - `rows` - `{Array.<Object>}` - Rows of the dashboard structure.
    *     - `styleClass` - `{string}` - CSS Class of the row.
    *     - `columns` - `{Array.<Object>}` - Columns of the row.
    *       - `styleClass` - `{string}` - CSS Class of the column.
    *
    * @returns {Object} self
    */
    this.structure = function(name, structure){
      structures[name] = structure;
      return this;
    };

   /**
    * @ngdoc method
    * @name bbva.or.db.dashboardProvider#messageTemplate
    * @methodOf bbva.or.db.dashboardProvider
    * @description
    *
    * Changes the template for messages.
    *
    * @param {string} template for messages.
    *
    * @returns {Object} self
    */
    this.messageTemplate = function(template){
      messageTemplate = template;
      return this;
    };

   /**
    * @ngdoc method
    * @name bbva.or.db.dashboardProvider#loadingTemplate
    * @methodOf bbva.or.db.dashboardProvider
    * @description
    *
    * Changes the template which is displayed as
    * long as the widget resources are not resolved.
    *
    * @param {string} loading template
    *
    * @returns {Object} self
    */
    this.loadingTemplate = function(template){
      loadingTemplate = template;
      return this;
    };


   /**
    * @ngdoc object
    * @name bbva.or.db
    * @description
    *
    * The dashboard holds all structures and widgets.
    * @returns {Object} self
    */
    this.$get = function(){
      return {
        widgets: widgets,
        structures: structures,
        messageTemplate: messageTemplate,
        loadingTemplate: loadingTemplate
      };
    };
  });

