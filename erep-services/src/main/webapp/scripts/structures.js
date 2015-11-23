'use strict';

/**
 * @ngdoc overview
 * @name bbva.or.db.structures
 * @description
 * #bbva.or.db.structures
 * Module with all dashboard structure pages
 */
angular.module('bbva.or.db.structures', ['bbva.or.db'])
.config(['dashboardProvider',function(dashboardProvider){

  dashboardProvider
    .structure('6-6', {
      icon:'images/structures/6-6.png',
      rows: [{
        columns: [{
          styleClass: 'col-md-6'
        }, {
          styleClass: 'col-md-6'
        }]
      }]
    })
    .structure('4-8', {
      icon:'images/structures/4-8.png',
      rows: [{
        columns: [{
          styleClass: 'col-md-4',
          widgets: []
        }, {
          styleClass: 'col-md-8',
          widgets: []
        }]
      }]
    })
    .structure('12/4-4-4', {
      icon:'images/structures/12-444.png',
      rows: [{
        columns: [{
          styleClass: 'col-md-12'
        }]
      }, {
        columns: [{
          styleClass: 'col-md-4'
        }, {
          styleClass: 'col-md-4'
        }, {
          styleClass: 'col-md-4'
        }]
      }]
    })
    .structure('12/6-6', {
      icon:'images/structures/12-66.png',
      rows: [{
        columns: [{
          styleClass: 'col-md-12'
        }]
      }, {
        columns: [{
          styleClass: 'col-md-6'
        }, {
          styleClass: 'col-md-6'
        }]
      }]
    })
    .structure('12/6-6/12', {
      icon:'images/structures/12-66-12.png',
      rows: [{
        columns: [{
          styleClass: 'col-md-12'
        }]
      }, {
        columns: [{
          styleClass: 'col-md-6'
        }, {
          styleClass: 'col-md-6'
        }]
      }, {
        columns: [{
          styleClass: 'col-md-12'
        }]
      }]
    })
    .structure('3-9 (6-6/12)', {
        icon:'images/structures/3-9.png',
      rows: [{
        columns: [{
          styleClass: 'col-md-3'
        },{
          styleClass: 'col-md-9',
          rows: [{
            columns: [{
              styleClass: 'col-md-6'
            },{
              styleClass: 'col-md-6'
            }]
          },{
            columns: [{
              styleClass: 'col-md-12'
            }]
          }]
        }]
      }]
    });

}]);
