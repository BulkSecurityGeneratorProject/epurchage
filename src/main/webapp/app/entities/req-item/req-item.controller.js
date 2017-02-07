(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('ReqItemController', ReqItemController);

    ReqItemController.$inject = ['$scope', '$state', 'ReqItem', 'ReqItemSearch'];

    function ReqItemController ($scope, $state, ReqItem, ReqItemSearch) {
        var vm = this;

        vm.reqItems = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ReqItem.query(function(result) {
                vm.reqItems = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReqItemSearch.query({query: vm.searchQuery}, function(result) {
                vm.reqItems = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
