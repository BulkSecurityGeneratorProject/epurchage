(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionMySuffixDeleteController',RequisitionMySuffixDeleteController);

    RequisitionMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Requisition'];

    function RequisitionMySuffixDeleteController($uibModalInstance, entity, Requisition) {
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
