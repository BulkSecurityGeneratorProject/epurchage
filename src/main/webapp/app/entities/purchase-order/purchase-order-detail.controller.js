(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('PurchaseOrderDetailController', PurchaseOrderDetailController);

    PurchaseOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PurchaseOrder', 'Department'];

    function PurchaseOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, PurchaseOrder, Department) {
        var vm = this;

        vm.purchaseOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:purchaseOrderUpdate', function(event, result) {
            vm.purchaseOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
