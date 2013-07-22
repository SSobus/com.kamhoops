/**
 * Application Configuration for AngularJS
 *
 * Configure various application values, define dependencies, setup root scope and define the route provider
 *
 * Notes:
 * - Do not minify this file or any angular file without reading about the issues with minification and Angular
 */

/**
 * Setup the application module includes
 */
var globalDependencies = [
    'Kamhoops.config',
    'Kamhoops.filters',
    'Kamhoops.directives',
    'Kamhoops.events',
    'Kamhoops.controllers',
    'Kamhoops.services',
    'ui.bootstrap',
    'ui.validate',
    'ui.route'
];

angular.module('Kamhoops', globalDependencies)
    .run(function ($rootScope, logger, authentication, requestContext) {
        $rootScope.logger = logger;
        $rootScope.requestContext = requestContext;
        $rootScope.user = authentication;
    });

angular.module('KamhoopsCrm', globalDependencies)
    .config(function ($routeProvider, requestContext) {
        var crmPartialsPath = requestContext + '/partials/crm';

        $routeProvider
            .when('/', {templateUrl: crmPartialsPath + '/crmHome.html', controller: 'CrmHomeController'})

        /**
         * If the route doesn't exist, we'll land the user back to the main logbook list page
         */
            .otherwise({redirectTo: '/'})
        ;
    })
    .run(function ($rootScope, logger, authentication, requestContext) {
        $rootScope.logger = logger;
        $rootScope.requestContext = requestContext;
        $rootScope.user = authentication;
    });
