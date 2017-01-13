(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('ItemMySuffixController', ItemMySuffixController);

    ItemMySuffixController.$inject = ['$scope', '$state', 'Item', 'ItemSearch'];

    function ItemMySuffixController ($scope, $state, Item, ItemSearch) {
        var vm = this;

        vm.items = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Item.query(function(result) {
                vm.items = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ItemSearch.query({query: vm.searchQuery}, function(result) {
                vm.items = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
