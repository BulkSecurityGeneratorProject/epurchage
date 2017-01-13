(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionMySuffixDialogController', RequisitionMySuffixDialogController);

    RequisitionMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Requisition', 'Employee', 'Item'];

    function RequisitionMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Requisition, Employee, Item) {
        var vm = this;

        vm.requisition = entity;
        vm.clear = clear;
        vm.save = save;
        vm.employees = Employee.query();
        vm.items = Item.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.requisition.id !== null) {
                Requisition.update(vm.requisition, onSaveSuccess, onSaveError);
            } else {
                Requisition.save(vm.requisition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epurchaseApp:requisitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
