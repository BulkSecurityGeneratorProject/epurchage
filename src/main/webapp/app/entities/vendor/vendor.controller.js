(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('VendorController', VendorController);

    VendorController.$inject = ['$scope', '$state', 'Vendor', 'VendorSearch'];

    function VendorController ($scope, $state, Vendor, VendorSearch) {
        var vm = this;

        vm.vendors = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Vendor.query(function(result) {
                vm.vendors = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            VendorSearch.query({query: vm.searchQuery}, function(result) {
                vm.vendors = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
