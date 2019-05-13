'use strict';

angular.module('crudApp').factory('StockService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllStocks: loadAllStocks,
                getAllStocks: getAllStocks,
                getStock: getStock,
                createStock: createStock,
                updateStock: updateStock
            };

            return factory;

            function loadAllStocks() {
                console.log('Fetching all stocks');
                var deferred = $q.defer();
                $http.get(urls.STOCK_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all stocks');
                            $localStorage.stocks = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading stocks');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllStocks(){
                return $localStorage.stocks;
            }

            function getStock(id) {
                console.log('Fetching Stock with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.STOCK_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Stock with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Stock with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createStock(stock) {
                console.log('Creating Stock');
                var deferred = $q.defer();
                $http.post(urls.STOCK_SERVICE_API, stock)
                    .then(
                        function (response) {
                            loadAllStocks();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Stock : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateStock(stock, id) {
                console.log('Updating Stock with id '+id);
                var deferred = $q.defer();
                $http.put(urls.STOCK_SERVICE_API + id, stock)
                    .then(
                        function (response) {
                            loadAllStocks();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Stock with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeStock(id) {
                console.log('Removing Stock with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.STOCK_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllStocks();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Stock with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);