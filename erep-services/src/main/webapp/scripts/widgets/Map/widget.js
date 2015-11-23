'use strict';

angular.module('bbva.or.widgets.Map', ['bbva.or.db.provider'])
    .config(["dashboardProvider",function(dashboardProvider){
        dashboardProvider
            .widget('MapWidget', {
                title: 'Map',
                description: 'Muestra un mapa con varias capas de información',
                previewImg : 'images/widgets/wm.png',
                controller: 'MapController',
                controllerAs: 'list',
                templateUrl: 'partials/widgets/Map/widget.html',
                config: {
                    titleIcoClass: 'fa-globe',
                    hasFilter: false
                }
            });
    }]).
    controller('MapController', function($q, $http, $rootScope, $timeout, $controller, $scope, $modal, config){

        $scope.config = config;

        $rootScope.$on('map:selectedFeatures', function(event, features) {
            debugger;
            angular.forEach(features, function (feature, key){
                console.log('Map Selected Feature: '+feature.id +' | '+ feature.type);
            }, this);
        });


        $scope.mapOptions = {
            center: {lon: -3.21, lat: 39},
            zoom: 6,
            tileLayers: [{
                    id: 'map4',
                    name: 'Stamen Dark',
                    visible: false,
                    source: ol.source.Stamen,
                    sourceProperties : {layer: 'toner'}
                },{
                    id: 'map5',
                    name: 'Stamen Light',
                    visible: true,
                    source: ol.source.Stamen,
                    sourceProperties : {layer: 'toner-lite'}
                },{
                    id: 'map1',
                    name: 'MapQuest Satelite',
                    visible: false,
                    source: ol.source.MapQuest,
                    sourceProperties: {layer: 'sat'}
                },{
                    id: 'map2',
                    name: 'MapQuest Político',
                    visible: false,
                    source: ol.source.MapQuest,
                    sourceProperties : {layer: 'osm'}
                },{
                    id: 'map3',
                    name: 'MapBox Light',
                    visible: false,
                    source: ol.source.TileJSON,
                    sourceProperties : {url: 'http://api.tiles.mapbox.com/v3/mapbox.world-light.jsonp'}
            }],
            overlaysLayers: [
                {
                    id: 'ol1',
                    name: 'ANS',
                    visible: true,
                    minResolution: 1200,
                    maxResolution: 5000,
                    metaData: {
                        overlayType: 'ANS',
                        overlayTemplate: 'mapAnsOverlay.html',
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
                        name: 'DTNE',
                        description: 'D.T.NOROESTE',
                        lat: 42.3261,
                        long: -5.2295,
                        ansOK: 50,
                        ansKO: 20
                    },{
                        name: 'DTNORTE',
                        description: 'D.T.NORTE',
                        lat: 41.87,
                        long: -0.8459,
                        ansOK: 41.66,
                        ansKO: 7
                    },{
                        name: 'DTCAT',
                        description: 'D.T.CATALUÑA',
                        lat: 41.8524,
                        long: 1.5765,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTCENTRO',
                        description: 'D.T.CENTRO',
                        lat: 39.9,
                        long: -2.9774,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTSUR',
                        description: 'D.T.SUR',
                        lat: 37.6,
                        long: -5,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTESTE',
                        description: 'D.T.ESTE',
                        lat: 39,
                        long: -0.19,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTCANARIAS',
                        description: 'D.T.CANARIAS',
                        lat: 28.6,
                        long: -15.4,
                        ansOK: 90,
                        ansKO: 10
                    }]
                },{
                    id: 'ol2',
                    name: 'ANS',
                    visible: false,
                    minResolution: 1200,
                    maxResolution: 5000,
                    metaData: {
                        overlayType: 'ANSM',
                        overlayTemplate: 'mapAnsOverlay.html',
                        infoType: 'C', // C = CHART
                        chartType: 'M', // M = Multibar Chart,
                        chartSeries: [{
                            name: 'En tiempo',
                            keyProperty : 'ansOK'
                        },{
                            name: 'Con Retraso',
                            keyProperty : 'ansKO'
                        }]
                    },
                    data: [{
                        name: 'DTNE',
                        description: 'D.T.NOROESTE',
                        lat: 42.3261,
                        long: -5.2295,
                        ansOK: 50,
                        ansKO: 20
                    },{
                        name: 'DTNORTE',
                        description: 'D.T.NORTE',
                        lat: 41.87,
                        long: -0.8459,
                        ansOK: 41.66,
                        ansKO: 7
                    },{
                        name: 'DTCAT',
                        description: 'D.T.CATALUÑA',
                        lat: 41.8524,
                        long: 1.5765,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTCENTRO',
                        description: 'D.T.CENTRO',
                        lat: 39.9,
                        long: -2.9774,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTSUR',
                        description: 'D.T.SUR',
                        lat: 37.6,
                        long: -5,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTESTE',
                        description: 'D.T.ESTE',
                        lat: 39,
                        long: -0.19,
                        ansOK: 22,
                        ansKO: 10
                    },{
                        name: 'DTCANARIAS',
                        description: 'D.T.CANARIAS',
                        lat: 28.6,
                        long: -15.4,
                        ansOK: 90,
                        ansKO: 10
                    }]
                }, {
                    id: 'ANS_CBCS',
                    name: 'ANS CBC',
                    visible: true,
                    maxResolution: 1200,
                    metaData: {
                        overlayType: 'ANSCBC',
                        overlayTemplate: 'mapAnsOverlay.html',
                        infoType: 'C', // C = CHART
                        chartType: 'P', // M = Multibar Chart,
                        chartSeries: [{
                            name: 'En tiempo',
                            keyProperty: 'ansOK'
                        }, {
                            name: 'Con Retraso',
                            keyProperty: 'ansKO'
                        }]
                    },
                    data: [
                        {
                            name: 'CBC_Vitoria-Gasteiz',
                            description: 'CBC Vitoria-Gasteiz',
                            lat: 42.849601,
                            long: -2.6661550000000034,
                            ansOK: 4,
                            ansKO: 22
                        }, {
                            name: 'CBC_Albacete',
                            description: 'CBC Albacete',
                            lat: 38.994349,
                            long: -1.858542400000033,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Alicante',
                            description: 'CBC Alicante',
                            lat: 38.3459963,
                            long: -0.4906855000000405,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Almería',
                            description: 'CBC Almería',
                            lat: 36.834047,
                            long: -2.4637136000000055,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Oviedo',
                            description: 'CBC Oviedo',
                            lat: 43.3619145,
                            long: -5.849388699999963,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Ávila',
                            description: 'CBC Ávila',
                            lat: 40.656685,
                            long: -4.681208599999991,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Badajoz',
                            description: 'CBC Badajoz',
                            lat: 38.8801136,
                            long: -6.970209299999965,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_PalmaMallorca',
                            description: 'CBC Palma de Mallorca',
                            lat: 39.68499660118128,
                            long: 3.00201416015625,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Barcelona',
                            description: 'CBC Barcelona',
                            lat: 41.3850639,
                            long: 2.1734034999999494,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Burgos',
                            description: 'CBC Burgos',
                            lat: 42.3439925,
                            long: -3.6969060000000127,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Cáceres',
                            description: 'CBC Cáceres',
                            lat: 39.4752934,
                            long: -6.372499999999945,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Cádiz',
                            description: 'CBC Cádiz',
                            lat: 36.5270612,
                            long: -6.288596200000029,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Santander',
                            description: 'CBC Santander',
                            lat: 43.46230569999999,
                            long: -3.8099803000000065,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Castellón',
                            description: 'CBC Castellón',
                            lat: 39.9863563,
                            long: -0.051324600000043574,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Ceuta', // dtdte5e56
                            description: 'CBC Ceuta',
                            lat: 35.8893874,
                            long: -5.321345500000007,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_CiudadReal',
                            description: 'CBC Ciudad Real',
                            lat: 38.9848295,
                            long: -3.927377799999931,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Cordoba',
                            description: 'CBC Córdoba',
                            lat: 37.8881751,
                            long: -4.7793834999999945,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Cuenca',
                            description: 'CBC Cuenca',
                            lat: 40.0703925,
                            long: -2.1374161999999615,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Gerona',
                            description: 'CBC Gerona',
                            lat: 42.0407088,
                            long: 2.937573899999961,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Granada',
                            description: 'CBC Granada',
                            lat: 37.1773363,
                            long: -3.5985570999999936,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Guadalajara',
                            description: 'CBC Guadalajara',
                            lat: 40.632489,
                            long: -3.1601699999999937,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_SanSebastian',
                            description: 'CBC San Sebastián',
                            lat: 43.318334,
                            long: -1.9812312999999904,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Huelva',
                            description: 'CBC Huelva',
                            lat: 37.261421,
                            long: -6.944722400000046,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Huesca',
                            description: 'CBC Huesca',
                            lat: 42.1361842,
                            long: -0.4082190999999966,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Jaén',
                            description: 'CBC Jaén',
                            lat: 37.7795594,
                            long: -3.7849716000000626,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Coruña',
                            description: 'CBC Coruña',
                            lat: 43.1,
                            long: -8.411540100000025,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Logroño',
                            description: 'CBC Logroño',
                            lat: 42.4627195,
                            long: -2.444985200000019,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_GranCanaria',
                            description: 'CBC Gran Canaria',
                            lat: 27.9202202,
                            long: -15.547437299999956,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Leon',
                            description: 'CBC León',
                            lat: 42.5987263,
                            long: -5.567095900000027,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Lleida',
                            description: 'CBC Lleida',
                            lat: 41.6175899,
                            long: 0.6200145999999904,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Lugo',
                            description: 'CBC Lugo',
                            lat: 43.0764348,
                            long: -7.611421699999937,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Madrid',
                            description: 'CBC Madrid',
                            lat: 40.4167754,
                            long: -3.7037901999999576,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Malaga',
                            description: 'CBC Málaga',
                            lat: 36.721261,
                            long: -4.421265500000004,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Pamplona',
                            description: 'CBC Pamplona',
                            lat: 42.812526,
                            long: -1.645774500000016,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Murcia',
                            description: 'CBC Murcia',
                            lat: 37.9922399,
                            long: -1.1306544000000258,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Orense',
                            description: 'CBC Orense',
                            lat: 42.2,
                            long: -7.6,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Palencia',
                            description: 'CBC Palencia',
                            lat: 42.0096857,
                            long: -4.528801599999952,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Pontevedra',
                            description: 'CBC Pontevedra',
                            lat: 42.3808401,
                            long: -8.572101599999996,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Salamanca',
                            description: 'CBC Salamanca',
                            lat: 40.9701039,
                            long: -5.663539700000001,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_SCTenerife',
                            description: 'CBC S.C.Tenerife',
                            lat: 28.2785654,
                            long: -16.57153399999993,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Segovia',
                            description: 'CBC Segovia',
                            lat: 40.9429032,
                            long: -4.1088068999999905,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Sevilla',
                            description: 'CBC Sevilla',
                            lat: 37.3890924,
                            long: -5.984458899999936,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Soria',
                            description: 'CBC Soria',
                            lat: 41.7665464,
                            long: -2.4790305999999873,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Tarragona',
                            description: 'CBC Tarragona',
                            lat: 41.1188827,
                            long: 1.2444908999999598,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Teruel',
                            description: 'CBC Teruel',
                            lat: 40.345735,
                            long: -1.1064241999999922,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Toledo',
                            description: 'CBC Toledo',
                            lat: 39.8628316,
                            long: -4.02732309999999,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Valencia',
                            description: 'CBC Valencia',
                            lat: 39.4699075,
                            long: -0.3762881000000107,
                            ansOK: 22,
                            ansKO: 10
                        }, {
                            name: 'CBC_Valladolid',
                            description: 'CBC Valladolid',
                            lat: 41.652251,
                            long: -4.724532100000033,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Bilbao',
                            description: 'CBC Bilbao',
                            lat: 43.2630126,
                            long: -2.9349852000000283,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Zamora',
                            description: 'CBC Zamora',
                            lat: 41.5034712,
                            long: -5.746787899999958,
                            ansOK: 22,
                            ansKO: 10
                        },{
                            name: 'CBC_Zaragoza',
                            description: 'CBC Zaragoza',
                            lat: 41.5034712,
                            long: -5.746787899999958,
                            ansOK: 22,
                            ansKO: 10
                        }
                    ]
                }
            ],
            layerGroups: [
            // UNIDADES ORGANIZATIVAS
            {
                id: 'LG_UO',
                name: 'Unidades Organizativas',
                visible: true,
                layers:[
                    {
                        id: 'LG_UO_UT',
                        name: 'UT',
                        visible: true,
                        featureType: 'UO_UT',
                        source: {
                            type: ol.source.Vector,
                            url: 'data/UO-UT-SPAIN.kml',
                            format: {
                                type: 'KML',
                                argProperties: {
                                    extractStyles:false
                                }
                            }
                        },
                        minResolution: 1200,
                        style: {
                            fill: {color: 'transparent'},
                            stroke: {width: 2, color: '#cb226d'} //
                        },
                        metaData: {
                            type: '{{typeFeature}}',
                            url:'http://bbva.or/EREP_WEB/map/features/{{typeFeature}}',  // TODO IGUAL PARA TODOS
                            format: 'GSON, KML, OFFICINAS'
                        }
                    },{
                        id: 'LG_UO_CBC',
                        name: 'CBC',
                        visible: true,
                        featureType: 'UO_CBC',
                        source: {
                            type: ol.source.Vector,
                            url: 'data/UO-CBC-SPAIN.json',
                            format: {
                                type: 'TopoJSON'
                            }
                        },
                        //minResolution: 1000,
                        maxResolution: 1200,
                        style: {
                            fill: {color: 'transparent'},
                            stroke: {width: 2, color: '#FF8800'} //
                        },
                        metaData: {
                            type: '{{typeFeature}}',
                            url:'http://bbva.or/EREP_WEB/map/features/{{typeFeature}}',  // TODO IGUAL PARA TODOS
                            format: 'GSON, KML, OFFICINAS'
                        }
                    },
                    {
                        id: 'fl2',
                        name: 'Oficinas',
                        visible: true,
                        featureType: 'BBVA_OFFICE',
                        featureIcon: 'images/markers/ma.png',
                        overlayTemplate: 'mapOverlayOffice.html',
                        maxResolution: 300,
                        source:{
                            type: ol.source.Vector,
                            clustering: true,
                            data: [{
                                name: 'ALCOBENDAS',
                                lat: 4.05412911444444444444444444444444444444E01,
                                long: -3.6432701222222222222222222222222222222E00
                            },{
                                name: 'ALCORCON',
                                lat: 4.03454247583333333333333333333333333333E01,
                                long: -3.8243117416666666666666666666666666667E00
                            },{
                                name: 'BOADILLA DEL MONTE',
                                lat: 40.40652555,
                                long: -3.8857134
                            },{
                                name: 'CIEMPOZUELOS',
                                lat: 40.1598102,
                                long: -3.6188632
                            },{
                                name: 'ESQUIVIAS',
                                lat: 40.1037743,
                                long: -3.7672829
                            },{
                                name: 'FUENLABRADA',
                                lat: 4.02873395416666666666666666666666666667E01,
                                long: -3.8004843333333333333333333333333333333E00
                            },{
                                name: 'GETAFE',
                                lat: 40.31276271,
                                long: -3.72220777
                            },{
                                name: 'GRIÑON',
                                lat: 40.2130327,
                                long: -3.8521699
                            },{
                                name: 'HUMANES DE MADRID',
                                lat: 40.255765,
                                long: -3.8251273
                            },{
                                name: 'ILLESCAS',
                                lat: 40.125845,
                                long: -3.8483365
                            },{
                                name: 'LEGANES',
                                lat: 4.03342846666666666666666666666666666667E01,
                                long: -3.762765625
                            },{
                                name: 'MADRID',
                                lat: 4.04302568880222641509433962264150943396E01,
                                long: -3.6867590057713207547169811320754716981E00
                            },{
                                name: 'MAJADAHONDA',
                                lat: 40.468302425,
                                long: -3.870316975
                            },{
                                name: 'MOSTOLES',
                                lat: 40.32229025625,
                                long: -3.86679583125
                            },{
                                name: 'PARLA',
                                lat: 40.23733758,
                                long: -3.76638428
                            },{
                                name: 'PINTO',
                                lat: 40.244426825,
                                long: -3.697751075
                            },{
                                name: 'POZUELO DE ALARCON',
                                lat: 4.04283825666666666666666666666666666667E01,
                                long: -3.8013528166666666666666666666666666667E00
                            },{
                                name: 'ROZAS DE MADRID (LAS)',
                                lat: 40.501875875,
                                long: -3.88470215
                            },{
                                name: 'SAN SEBASTIAN DE LOS REYES',
                                lat: 40.550836725,
                                long: -3.626470525
                            },{
                                name: 'SESEÑA',
                                lat: 40.1045433,
                                long: -3.6977322
                            },{
                                name: 'TORREJON DE LA CALZADA',
                                lat: 40.2005257,
                                long: -3.7991593
                            },{
                                name: 'TRES CANTOS',
                                lat: 4.06014710333333333333333333333333333333E01,
                                long: -3.7104270666666666666666666666666666667E00
                            },{
                                name: 'TRES CANTOS 2',
                                lat: 4.06014710333333333333333333333333333333E01,
                                long: -3.7106270666666666666666666666666666667E00
                            },{
                                name: 'VALDEMORO',
                                lat: 40.189463825,
                                long: -3.68446805
                            },{
                                name: 'VILLAVICIOSA DE ODON',
                                lat: 40.35844,
                                long: -3.9034394
                            },{
                                name: 'YUNCOS',
                                lat: 40.088903,
                                long: -3.872654
                            },{
                                name: 'COLMENAR VIEJO',
                                lat: 4.06651427666666666666666666666666666667E01,
                                long: -3.7704049333333333333333333333333333333E00
                            },{
                                name: 'HOYO DE MANZANARES',
                                lat: 40.629523,
                                long: -3.875498
                            },{
                                name: 'MIRAFLORES DE LA SIERRA',
                                lat: 40.8110118,
                                long: -3.7686377
                            }]
                        },
                        metaData: {
                            url:'http://bbva.or/EREP_WEB/map/features/{{typeFeature}}',  // TODO IGUAL PARA TODOS
                            format: 'GSON, KML, OFFICINAS'
                        }
                }]
            }],
            featuresLayers: [{
                id: 'fl3',
                name: 'Alertas',
                visible: false,
                featureType: 'BBVA_ALERTS',
                featureIcon: 'images/markers/mr.png',
                overlayTemplate: 'mapOverlayAlert.html',
                source:{
                    type: ol.source.Vector,
                    clustering: true,
                    data: [{
                        name: 'Alerta 1',
                        alertDate: new Date(),
                        description: 'Cajero Roto',
                        lat: 42.06016710333333333333333333333333333333E00,
                        long: -3.7104470666666666666666666666666666667E00
                    },{
                        name: 'Alerta 2',
                        alertDate: new Date(),
                        description: 'Atraco',
                        lat: 42.560836725,
                        long: -3.636470525
                    }]
                }
            }]
        };

        /* Close Map Type Modal */
        $scope.close = function(){
            console.log('close modal');
            $modalInstance.close();
        };
    });