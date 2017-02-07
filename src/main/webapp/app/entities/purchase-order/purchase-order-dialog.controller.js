(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('PurchaseOrderDialogController', PurchaseOrderDialogController);

    PurchaseOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PurchaseOrder', 'Department'];

    function PurchaseOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PurchaseOrder, Department) {
        var vm = this;

        vm.purchaseOrder = entity;
        vm.clear = clear;
        vm.save = save;
        vm.departments = Department.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.purchaseOrder.id !== null) {
                PurchaseOrder.update(vm.purchaseOrder, onSaveSuccess, onSaveError);
            } else {
                PurchaseOrder.save(vm.purchaseOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epurchaseApp:purchaseOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
