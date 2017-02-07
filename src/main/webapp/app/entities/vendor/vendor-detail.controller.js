(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('VendorDetailController', VendorDetailController);

    VendorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vendor', 'Item'];

    function VendorDetailController($scope, $rootScope, $stateParams, previousState, entity, Vendor, Item) {
        var vm = this;

        vm.vendor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:vendorUpdate', function(event, result) {
            vm.vendor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
