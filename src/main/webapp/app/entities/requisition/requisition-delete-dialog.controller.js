(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionDeleteController',RequisitionDeleteController);

    RequisitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Requisition'];

    function RequisitionDeleteController($uibModalInstance, entity, Requisition) {
        var vm = this;

        vm.requisition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Requisition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
