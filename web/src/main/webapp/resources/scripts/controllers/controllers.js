angular.module('Kamhoops.controllers', ['Kamhoops.config', 'Kamhoops.services'])

    .controller('KamhoopsController', function ($scope, $location) {
        $scope.setRoute = function (route) {
            $location.path(route);
        };

    })

    .controller('HomepageController', function ($scope, KamhoopsService) {
        $scope.service = KamhoopsService;

        $scope.service.fetchNews();
    })

    .controller('ScheduleController', function ($scope, KamhoopsService) {

    })

;