(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('PurchaseOrderDeleteController',PurchaseOrderDeleteController);

    PurchaseOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'PurchaseOrder'];

    function PurchaseOrderDeleteController($uibModalInstance, entity, PurchaseOrder) {
        var vm = this;

        vm.purchaseOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PurchaseOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
