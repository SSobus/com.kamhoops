angular.module('Kamhoops.directives', [])
    .directive('headerView', function () {
        return {
            templateUrl: '/partials/site/header.html',
            restrict: 'E',
            replace: true
        }
    })

    .directive('navigationView', function () {
        return {
            templateUrl: '/partials/site/navigation.html',
            restrict: 'E',
            replace: true
        }
    })

    .directive('quickGameView', function () {
        return {
            templateUrl: '/partials/gameViews/quickGameView.html',
            restrict: 'E',
            replace: true
        };
    })

    .directive('previousGameView', function () {
        return {
            templateUrl: '/partials/gameViews/previousGame.html',
            restrict: 'E',
            replace: true
        };
    })

    .directive('nextGameView', function () {
        return {
            templateUrl: '/partials/gameViews/nextGame.html',
            restrict: 'E',
            replace: true
        };
    })

    .directive('currentNewsView', function () {
        return {
            templateUrl: '/partials/currentNews.html',
            restrict: 'E',
            replace: true
        };
    })

    .directive('teamList', function () {
        return {
            templateUrl: '/partials/team/teamList.html',
            restrict: 'E',
            replace: true
        };
    })

    .directive('team', function () {
        return {
            templateUrl: '/partials/team/team.html',
            restrict: 'E',
            replace: true
        };
    })

;