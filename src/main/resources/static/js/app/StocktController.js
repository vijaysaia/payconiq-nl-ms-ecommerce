'use strict';

angular.module('crudApp').controller('StockController',
    ['StockService', '$scope',  function( StockService, $scope) {

        var self = this;
        self.stock = {};
        self.stocks=[];

        self.submit = submit;
        self.getAllStocks = getAllStocks;
        self.createStock = createStock;
        self.updateStock = updateStock;
        self.editStock = editStock;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.onlyText = /^[a-zA-Z]+$/;
        self.digitsOrDecimals=/^[+-]?((\d+(\.\d*)?)|(\.\d+))$/;
        

        function submit() {
            console.log('Submitting');
            if (self.stock.id === undefined || self.stock.id === null) {
                console.log('Saving New Stock', self.stock);
                createStock(self.stock);
            } else {
                updateStock(self.stock, self.stock.id);
                console.log('Stock updated with id ', self.stock.id);
            }
        }

        function createStock(stock) {
            console.log('About to create stock');
            StockService.createStock(stock)
                .then(
                    function (response) {
                        console.log('Stock created successfully');
                        self.successMessage = 'Stock created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.stock={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Stock');
                        self.errorMessage = 'Error while creating Stock: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateStock(stock, id){
            console.log('About to update stock');
            StockService.updateStock(stock, id)
                .then(
                    function (response){
                        console.log('Stock updated successfully');
                        self.successMessage='Stock updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Stock');
                        self.errorMessage='Error while updating Stock '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }





        function getAllStocks(){
            return StockService.getAllStocks();
        }

        function editStock(id) {
            self.successMessage='';
            self.errorMessage='';
            StockService.getStock(id).then(
                function (stock) {
                    self.stock = stock;
                },
                function (errResponse) {
                    console.error('Error while removing stock ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.stock={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);