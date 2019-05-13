var app = angular.module('crudApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/ecommerce',
    STOCK_SERVICE_API : 'http://localhost:8080/ecommerce/api/stocks/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'StockController',
                controllerAs:'ctrl',
                resolve: {
                    stocks: function ($q, StockService) {
                        console.log('Load all stocks');
                        var deferred = $q.defer();
                        StockService.loadAllStocks().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);