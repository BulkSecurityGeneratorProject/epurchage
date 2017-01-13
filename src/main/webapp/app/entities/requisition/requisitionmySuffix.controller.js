(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionMySuffixController', RequisitionMySuffixController);

    RequisitionMySuffixController.$inject = ['$scope', '$state', 'Requisition', 'RequisitionSearch'];

    function RequisitionMySuffixController ($scope, $state, Requisition, RequisitionSearch) {
        var vm = this;

        vm.requisitions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Requisition.query(function(result) {
                vm.requisitions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            RequisitionSearch.query({query: vm.searchQuery}, function(result) {
                vm.requisitions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
