angular.module('Kamhoops.services', [])

/**
 * Simple JQuery UI Wrapper
 *
 * This wrapper was inspired from the following jsfiddle: http://jsfiddle.net/vGjXH/ and has been modified
 * to include additional functionality. This wrapper is provides the ability to mock out jquery functionality
 */
    .factory('jqueryUI', function ($window, $templateCache, $document, $compile, $rootScope) {
        return {
            wrapper: function (cssSelector, pluginName, options, templateName, dialogScope) {
                if (templateName) {
                    var templateDom = $($templateCache.get(templateName));
                    $document.append(templateDom);
                    $compile(templateDom)(dialogScope);
                }
                return $(cssSelector)[pluginName](options);
            },

            performAction: function (cssSelector, pluginName, action, options) {
                if (options) {
                    return $(cssSelector)[pluginName](action, options);
                } else {
                    return $(cssSelector)[pluginName](action);
                }
            },

            makeClassGallery: function (className) {
                $("." + className).fancybox({
                    openEffect: 'none',
                    closeEffect: 'none'
                });
            },

            dialog: {
                open: function (cssSelector, dialogOptions) {
                    var options = angular.extend({
                        close: function () {
                            //$rootScope.$broadcast(events.type.DIALOG, "closed", cssSelector);
                        }
                    }, dialogOptions);

                    // $rootScope.$broadcast(events.type.DIALOG, "open", cssSelector);

                    return $(cssSelector).dialog(options).dialog("open");
                },
                close: function (cssSelector) {
                    //$rootScope.$broadcast(events.type.DIALOG, "close", cssSelector);
                    return $(cssSelector).dialog("close");
                },
                destroy: function (cssSelector) {
                    return $(cssSelector).dialog("destroy");
                },
                recenter: function (cssSelector) {
                    return $(cssSelector).dialog("option", "position", "center");
                }
            },

            show: function (cssSelector, speed) {
                return $(cssSelector).show(speed);
            },

            hide: function (cssSelector, speed) {
                return $(cssSelector).hide(speed);
            },

            slideToggle: function (cssSelector, speed) {
                return $(cssSelector).slideToggle(speed);
            },

            disableButton: function (cssSelector) {
                return $(cssSelector).button("disable");
            },

            enableButton: function (cssSelector) {
                return $(cssSelector).button("enable");
            }
        };
    })

/**
 * Logger Service
 *
 * Provide a common/centralized logging system
 */
    .factory("logger", function ($rootScope, $log) {
        var events = [], logger = {};
        var eventLevels = {info: "INFO", error: "ERROR", debug: "DEBUG", warn: "WARN"};
        var isLoggerEnabled = false, BROADCAST_LABEL = "event:loggerNewEvent";

        /**
         * Create a cross browser compatible logging facility and ensure it is always safe to use console.log and any other log functions
         *
         * example: log("blahblah", this, args);
         * see: http://paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
         */
        window.log = function f() {
            log.history = log.history || [];
            log.history.push(arguments);
            if (this.console) {
                arguments.callee = arguments.callee.caller;
                var newArguments = [].slice.call(arguments);

                if (typeof console.log === 'object') {
                    log.apply.call(console.log, console, newArguments);
                } else {
                    console.log.apply(console, newArguments);
                }
            }
        };

        (function (a) {
            function b() {
            }

            for (var c = "assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","), d; !!(d = c.pop());) {
                a[d] = a[d] || b;
            }
        })
        (function () {
            try {
                console.log();
                return window.console;
            } catch (a) {
                return (window.console = {});
            }
        }());

        function addEvent(type, message) {
            events.push({position: events.length, type: type, message: message, created: new Date().getTime()});
            $rootScope.$broadcast(BROADCAST_LABEL);
        }

        function exceptionLogger(exception, cause) {
            addEvent(eventLevels.error, exception);
        }

        function setLoggerEnabled() {
            isLoggerEnabled = true;
            addEvent("LOGGER", "Logging Enabled");

            for (var level in eventLevels) {
                if (eventLevels.hasOwnProperty(level)) {
                    logger[level] = angular.isFunction($log[level]) ? $log[level] : $log.info || angular.noop;
                }
            }
        }

        function setLoggerDisabled() {
            isLoggerEnabled = false;
            addEvent("LOGGER", "Logging Disabled");

            for (var level in eventLevels) {
                if (eventLevels.hasOwnProperty(level)) {
                    logger[level] = angular.noop;
                }
            }
        }

        function formatEventLabel(eventLabel) {
            return angular.isString(eventLabel) ? eventLabel.replace(/\ /g, "_").replace(/\W/g, '').toUpperCase() : "";
        }

        var service = {
            info: function (message) {
                addEvent(eventLevels.info, message);
                logger.info(message);
            },

            error: function (message) {
                addEvent(eventLevels.error, message);
                logger.error(message);
            },

            warn: function (message) {
                addEvent(eventLevels.warn, message);
                logger.warn(message);
            },

            debug: function (message) {
                addEvent(eventLevels.debug, message);
                logger.debug(message);
            },

            getEvents: function () {
                return angular.copy(events);
            },

            getEventsByLabel: function (eventLabel) {
                var filtered = [], eventLabel = formatEventLabel(eventLabel);

                if (eventLabel.length > 0) {
                    for (var i = 0; i < events.length; i++) {
                        if (events[i].type == eventLabel) {
                            filtered.push(events[i]);
                        }
                    }
                }

                return angular.copy(filtered);
            },

            getLastEvent: function () {
                return events.length > 0 ? angular.copy(events.slice(-1)[0]) : {};
            },

            getLastEventsByCount: function (count) {
                return events.slice(-count);
            },

            getEventCount: function () {
                return events.length;
            },

            getEventLevels: function () {
                return angular.copy(eventLevels);
            },

            isEnabled: function () {
                return isLoggerEnabled;
            },

            setEnabled: function (isEnabled) {
                isEnabled ? setLoggerEnabled() : setLoggerDisabled();
            },

            addEvent: function (eventLabel, message) {
                var label = formatEventLabel(eventLabel);

                if (angular.isString(label) && label.length > 0 && angular.isString(message) && message.length > 0) {
                    addEvent(label, message);
                }
            },

            getBroadcastLabel: function () {
                return BROADCAST_LABEL;
            }
        };

        if (console instanceof Object) {
            setLoggerEnabled();
        } else {
            setLoggerDisabled();
        }

        return service;
    })

/**
 * Kamhoops Data Service
 *
 * Provides access to the server API for data modification
 */
    .factory("KamhoopsService", function ($rootScope, $http, $timeout, logger, requestContext) {

        var dataTemplate = {
            data: [],
            filter: {},
            lastRefreshed: null,
            isLoading: false
        };

        var delayUpdate = function (fn) {
            $timeout(function () {
                fn();
            }, 500);
        };

        /**
         * Generic url fetch into a data object following the dataTemplate pattern
         * @param url
         * @param into
         */
        var fetch = function (url, into) {
            into.isLoading = true;

            $http.get(requestContext + url)
                .success(function (data) {
                    delayUpdate(function () {
                        into.data = data;
                        into.isLoading = false;
                        into.lastRefreshed = new Date().getTime();
                    });
                })
                .error(function (data) {
                    delayUpdate(function () {
                        into.isLoading = false;
                    });
                    logger.error("Could not retrieve data list from the server: " + angular.toJson(data));
                })
            ;
        };

        /**
         * Generic delete of an entity
         * @param {String} objectUrlWithId url pointing to the object and id that is to be deleted
         * @param {Object} callbacks optional success and error callbacks
         */
        var deleteEntity = function (objectUrlWithId, callbacks) {
            $http.delete(requestContext + objectUrlWithId)
                .success(function (data) {
                    delayUpdate(function () {
                        if (angular.isFunction(callbacks.success)) {
                            callbacks.success(data);
                        }
                    });
                })
                .error(function (data) {
                    logger.error("Could not delete entity at '" + objectUrlWithId + "'. See errors for details: " + angular.toJson(data));

                    delayUpdate(function () {
                        if (angular.isFunction(callbacks.error)) {
                            callbacks.error(formatErrorsIntoErrorObject(data));
                        }
                    });
                })
            ;
        };

        /**
         * Generic function to remove an object from a list
         * @param entityId
         * @param objectList
         */
        var removeEntityFromObjectList = function (entity, objectList) {
            if (objectList.data.length == 0) {
                return;
            }

            var index = objectList.data.indexOf(entity);
            if (index >= 0) {
                objectList.data.splice(index, 1);
            }

            objectList.isLoading = false;
        };

        /**
         * Generic POST to toggle the active status of an entity
         * @param objectUrlWithId
         * @param callbacks
         */
        function toggleActive(objectUrlWithId, callbacks) {
            $http.post(requestContext + objectUrlWithId + '/toggleActive.json')
                .success(function (data) {
                    delayUpdate(function () {
                        if (angular.isFunction(callbacks.success)) {
                            callbacks.success(data);
                        }
                    });

                })
                .error(function (data) {
                    logger.error("Could not toggle active status. See errors for details: " + angular.toJson(data));

                    delayUpdate(function () {
                        if (angular.isFunction(callbacks.error)) {
                            callbacks.error(formatErrorsIntoErrorObject(data));
                        }
                    });
                })
            ;
        };

        /**
         * Create an error object to represent errors returned from the server
         * @param {Object} errors containing the @code(errors) key
         * @returns {Object} where properties are the fields that triggered the error. If errors
         */
        function formatErrorsIntoErrorObject(errors) {
            var formattedErrors = {};

            if (angular.isObject(errors) && angular.isArray(errors.errors)) {
                _.each(errors.errors, function (error) {
                    formattedErrors[error.fieldName] = {
                        message: error.errorMessage,
                        type: error.errorType
                    };
                });
            } else {
                formattedErrors = errors;
            }

            return formattedErrors;
        }

        var service = {};

        var news = {
            news: angular.extend(angular.copy(dataTemplate),
                {
                    template: {
                        id: null,
                        name: null
                    }
                }),

            cleanNews: function (news) {
                return _.omit(news, '$$hashKey');
            },

            fetchNews: function () {
                fetch('/news/list.json', service.news);
            }
        };

        var teams = {
            teams: angular.extend(angular.copy(dataTemplate),
                {
                    template: {
                        id: null,
                        name: null
                    }
                }),
            cleanTeams: function (teams) {
                return _.omit(teams, '$$hashKey');
            },

            fetchTeams: function () {
                fetch('/teams/list.json', service.teams);
            }
        }

        angular.extend(service, news, teams);

        // Attach to rootScope for watchers
        $rootScope.kamhoopsData = service;

        return service;
    })

;