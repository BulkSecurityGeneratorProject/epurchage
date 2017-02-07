(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('PurchaseOrderController', PurchaseOrderController);

    PurchaseOrderController.$inject = ['$scope', '$state', 'PurchaseOrder', 'PurchaseOrderSearch'];

    function PurchaseOrderController ($scope, $state, PurchaseOrder, PurchaseOrderSearch) {
        var vm = this;

        vm.purchaseOrders = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PurchaseOrder.query(function(result) {
                vm.purchaseOrders = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PurchaseOrderSearch.query({query: vm.searchQuery}, function(result) {
                vm.purchaseOrders = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
