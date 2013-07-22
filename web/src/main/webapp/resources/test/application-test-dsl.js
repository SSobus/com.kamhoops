/**
 *
 * Application Test DSL (Domain Specific Language)
 *
 * Define any globally applicable functions that can be used during testing. These are loaded first
 * and can be used in any describe()/it()/whatever code block
 *
 */

/**
 * Include and setup all Angular related modules and functionality for testing.
 *
 * This should be called first and before each test
 *
 * example:
 *   beforeEach(function() {
 *       configureApplicationWithDefaults();
 *   });
 */
function configureApplicationWithDefaults() {
    module("Kamhoops");

    setRequestContext("");
    setAuthenticatedUserFromUser(null);
}

/**
 * Configure the request context
 * @param {String} [requestContext] optional
 */
function setRequestContext(requestContext) {
    angular.module('Kamhoops.config')
        .constant("requestContext", requestContext || "");
}

function setAuthenticatedUserFromUser(user) {
    angular.module('Kamhoops.config')
        .config(function (authenticationProvider) {
            authenticationProvider
                .setAuthenticatedUser(user)
            ;
        });
}

function setAuthenticatedUserAsAdministrator() {
    angular.module('Kamhoops.config')
        .config(function (authenticationProvider) {
            authenticationProvider
                .setAuthenticatedUser(users.administrators[0])
            ;
        });
}

/**
 * @returns {Object} the authenticated user as configured by the authenticationProvider
 */
function getAuthenticatedUser() {
    var user;

    inject(function (authentication) {
        user = authentication;
    });

    return user;
}

/**
 * Configure the page model based on the supplied object
 * @param {Object} pageModel
 */
function setPageModelFromObject(pageModel) {
    angular.module('Kamhoops.config')
        .config(function (pageModelProvider) {

            _.each(pageModel, function (element, index) {
                pageModelProvider.add(index, element);
            });

        });
}

/**
 * Return the page model as configured by the pageModelProvider (usually from setPageModelFromObject)
 */
function getPageModel() {
    var pageModelObject;

    inject(function (pageModel) {
        pageModelObject = pageModel;
    });

    return pageModelObject;
}

/**
 * Return a blank scope object from the root scope. This can only be called after all initialization has been completed
 * @returns {Object} newly configured scope
 */
function getScopeFromRootScope() {
    var scope;

    inject(function ($rootScope) {
        scope = $rootScope.$new();
    });

    return scope;
}

/**
 * Configure the scope from the supplied controller
 * @param {String} controllerName
 * @param {Object} scope created from getScopeFromRootScope() or otherwise
 */
function setupControllerFromScope(controllerName, scope) {
    inject(function ($controller) {
        $controller(controllerName, {$scope: scope});
    });

    return scope;
}

/**
 * Configure and return the scope from the supplied controller
 * @param controllerName
 * @returns {Object} scope as configured by the controller
 */
function setupControllerAndReturnScope(controllerName) {
    var scope = getScopeFromRootScope();

    setupControllerFromScope(controllerName, scope);

    return scope;
}

function httpGETSuccessForUrlAndReturnData(url, dataToReturn) {
    inject(function ($httpBackend) {
        $httpBackend.whenGET(url).respond(200,
            dataToReturn
        );
    });
}

function httpGETErrorForUrlAndReturnData(url, dataToReturn) {
    inject(function ($httpBackend) {
        $httpBackend.whenGET(url).respond(500,
            dataToReturn
        );
    });
}

function httpPOSTSuccessForUrlAndReturnData(url, dataToReturn) {
    inject(function ($httpBackend) {
        $httpBackend.whenPOST(url).respond(200,
            dataToReturn
        );
    });
}

function httpPOSTErrorForUrlAndReturnData(url, dataToReturn) {
    inject(function ($httpBackend) {
        $httpBackend.whenPOST(url).respond(500,
            dataToReturn
        );
    });
}

/**
 * Flush any pending http requests (post/get/delete)
 */
function httpFlush() {
    inject(function ($httpBackend) {
        $httpBackend.flush();
    });
}

/**
 * Flush any pending timeouts
 */
function timeoutFlush() {
    inject(function ($timeout) {
        $timeout.flush();
    });
}

/**
 * Wrap data in a successful json callback (server simulation)
 * @param data
 * @returns {{status: string, result: *}}
 */
function jsonSuccessWithData(data) {
    return {
        "status": "SUCCESS",
        "result": data
    };
}

/**
 * Wrap data in an error json callback for the supplied errors (server simulation)
 * @param data
 * @returns {{status: string, result: null, errors: *}}
 */
function jsonErrorUsingErrorArrayData(data) {
    return {
        "status": "FAIL",
        "result": null,
        "errors": data
    };
}

/**
 * Wrap data in an error json callback for a duplicate entity exception (server simulation)
 * @returns {{status: string, result: null, errors: Array}}
 */
function jsonErrorFromDuplicateEntry(id, objectName, fieldName) {
    return {
        "status": "FAIL",
        "result": null,
        "errors": [
            {"id": id || null, "objectName": objectName, "fieldName": fieldName, "errorMessage": null, "errorType": "DUPLICATION"}
        ]
    };
}