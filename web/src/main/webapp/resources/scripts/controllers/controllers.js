angular.module('Kamhoops.controllers', ['Kamhoops.config', 'Kamhoops.services'])

    .controller('KamhoopsController', function ($scope, $location, authentication) {
        $scope.setRoute = function (route) {
            $location.path(route);
        };
    })

    .controller('HomepageController', function ($scope) {

    })

;