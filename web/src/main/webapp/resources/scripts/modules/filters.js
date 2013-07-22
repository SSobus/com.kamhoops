/**
 * filters.js
 *
 * Create a module for custom Angular filters. These are used throughout the application mainly for UI modification
 */
angular.module('Kamhoops.filters', [])

/**
 * Converts the string format 2505551234 or 92505551234 to 250-555-1234
 */
    .filter('phoneNumberFormat', function () {
        return function (phoneNumber) {

            if (!phoneNumber) {
                return "";
            }

            var number = "";

            // If number is of format 1234561234 or 91234561234 convert to 123-456-1234
            if (phoneNumber.length == 10 || phoneNumber.length == 11) {
                number = phoneNumber.slice(-10).replace(/^(\d\d\d)(\d\d\d)(\d\d\d\d)$/g, "\$1-\$2-\$3");
            }

            return number;
        }
    })

/**
 * Converts objects to arrays of values
 */
    .filter('objectToArray', function () {
        return function (input) {
            if (!angular.isObject(input)) {
                return [];
            }

            return _.values(input);
        }
    })

/**
 * Convert a URI, URL, UNC or path to just a filename
 */
    .filter('uriToFilename', function () {
        return function (input) {
            if (!angular.isString(input)) {
                return "";
            }

            return input.replace(/^.*[\\\/]/, '');
        };
    })

/**
 * Limiting filter used for ng-repeat. This allows starting at an arbitrary point in a
 * data array.
 *
 * Return a portion of the array or an empty array if input is not an array
 */
    .filter('startFrom', function () {
        return function (input, start) {
            start = +start;

            return (angular.isArray(input) ? input.slice(start) : []);
        };
    })

/**
 * Convert null input to display an arbitrary value
 */
    .filter('nullToString', function () {
        return function (input, value) {
            return ( input == null ? value : input);
        };
    })

/**
 * Convert a boolean input to string one of two strings depending on if it is true or false
 */
    .filter('booleanToString', function () {
        return function (input, trueValue, falseValue) {
            return ( input ? trueValue : falseValue);
        };
    })

/**
 * Convert a string representation of a date in millis using the defaultDateTimeFormat unless the filter
 * is supplied with a custom format
 */
    .filter('millisToString', function (defaultDateTimeFormat) {
        return function (input, format) {
            return ( !angular.isNumber(input) ? "" : (format == null ? moment(Number(input)).format(defaultDateTimeFormat) : moment(Number(input)).format(format)));
        };
    })

/**
 * Convert to a string representation of a date in millis using the defaultDateTimeSecondsFormat
 */
    .filter('millisToStringWithSeconds', function (defaultDateTimeSecondsFormat) {
        return function (input) {
            return ( !angular.isNumber(input) ? "" : moment(Number(input)).format(defaultDateTimeSecondsFormat) );
        };
    })

    .filter('formatUri', function () {
        return function (input) {
            if (!angular.isString(input)) {
                return "";
            }

            return (input.indexOf("\\\\") == 0 || input.indexOf("//") == 0 ? "file://" + input : input);
        }
    })

/**
 * Deep Filter
 *
 * Uses a recursive strategy to analyse an object to apply its keys against another object for filtering
 *
 * example:
 *   - <tr ng-repeat="account in (crmData.accounts.data | deepFilter:accountFilters)">
 *   - $scope.accountFilter = angular.copy($scope.crmData.accounts.filter)
 */
    .filter('deepFilter', function (logger) {

        function deepFilter(filter, objectToFilter) {
            var includeInResult = true;

            angular.forEach(filter, function (filterValue, key) {

                if (!includeInResult || filterValue === "" || filterValue === "null" || filterValue === null || filterValue === [] || filterValue === {}) {
                    return;
                }

                switch (typeof filterValue) {
                    case "string":
                        includeInResult = includeInResult && String(objectToFilter[key]).indexOf(filterValue) != -1;
                        break;
                    case "number":
                        includeInResult = includeInResult && objectToFilter[key] == filterValue;
                        break;
                    case "object":
                        includeInResult = includeInResult && deepFilter(filterValue, objectToFilter[key]);
                        break;
                    default:
                        logger.info("Missing implementation in deepFilter for: '" + typeof filterValue + "'");
                }
            });

            return includeInResult;
        }


        return function (input, filterObject) {

            if (!angular.isArray(input) || !angular.isObject(filterObject)) {
                return input;
            }

            return _.filter(input, function (objectToFilter) {
                return deepFilter(filterObject, objectToFilter);
            });
        }
    })

/**
 * Describe Filter
 *
 * Used to create a textual representation of an applied filter
 */
    .filter('describeFilter', function () {

        function describeFilter(filterBy, parent) {
            var filterDescription = '';

            angular.forEach(filterBy, function (value, key) {
                if (angular.isDefined(value) && value !== "null" && value !== null && value !== '' && value !== [] && value !== {}) {

                    if (angular.isString(parent) && parent != '') {
                        filterDescription += parent + '->';
                    }

                    if (angular.isObject(value)) {
                        filterDescription += describeFilter(value, key);
                    } else {
                        filterDescription += key + ': ' + value + '; ';
                    }
                }
            });

            return filterDescription;
        }

        return function (input) {
            if (!angular.isObject(input)) {
                return "";
            }

            return "Filtering on: " + describeFilter(input, '');
        };
    })
;
