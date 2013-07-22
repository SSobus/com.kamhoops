angular.module('Kamhoops.config', [])
    .value('defaultDateTimeFormat', 'YYYY/MM/DD - HH:mm')
    .value('defaultDateTimeSecondsFormat', 'YYYY/MM/DD - HH:mm:ss')

/**
 * Page Model Provider
 *
 * This provider allows for a Velocity page to set model attributes from Spring to be injected
 * in to Angular Controllers/services as the 'page' service.
 *
 * Example:
 *
 * In a Velocity Page:
 *
 * <script type="text/javascript>
 * angular.module('Kamhoops.config')
 *  .config(function(pageProvider) {
 *      pageProvider
 *          .add("producerId", "$!{produceId}")
 *          .add("anotherVar", "$!{myVarFromSpring"})
 *       ;
 *  });
 * </script>
 *
 * In a Controller:
 *
 * .controller("MyController", function($scope, page) {
 *      $scope.myVarFromSpring = page.myVarFromSpring;
 * });
 */
    .provider('pageModel', function () {

        this.pageModel = {};

        this.add = function (name, value) {
            this.pageModel[name] = value;
            return this;
        };

        this.$get = function () {
            return this.pageModel;
        };
    })

/**
 * Authentication Provider
 *
 * This provider holds the currently authenticated user's details and provides additional functions
 * Example:
 *
 * In a Velocity Page:
 *
 * <script type="text/javascript>
 * angular.module('Kamhoops.config')
 *  .config(function(authenticationProvider) {
 *      authenticationProvider
 *          .setAuthenticatedUser({
 *              firstname: "${authenticatedUser.firstName}",
 *              lastname: "${authenticatedUser.lastName}"
 *          })
 *       ;
 *  });
 * </script>
 *
 * In a Controller:
 *
 * .controller("MyController", function($scope, authentication) {
 *      $scope.isUserAuthenticated = authentication.isAuthenticated();
 * });
 */
    .provider('authentication', function () {

        var userTemplate = {
            id: null,
            firstname: null,
            lastname: null,
            email: null,
            lastLogin: null,

            authenticated: false
        };

        this.authenticatedUser = angular.copy(userTemplate);

        // Additional functions to provide
        this.authenticatedUserFunctions = {
            isAuthenticated: function () {
                return this.authenticated;
            },
            getFullName: function () {
                return this.firstname + " " + this.lastname
            }
        };

        this.setAuthenticatedUser = function (authenticatedUser) {

            if (angular.isObject(authenticatedUser)) {
                this.authenticatedUser = authenticatedUser;
                this.authenticatedUser.authenticated = true;
            } else {
                this.authenticatedUser = angular.copy(userTemplate);
                this.authenticatedUser.authenticated = false;
            }

            return this;
        };

        this.$get = function () {
            return angular.extend(this.authenticatedUser, this.authenticatedUserFunctions);
        };
    })
;