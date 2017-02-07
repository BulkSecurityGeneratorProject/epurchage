(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('DepartmentDialogController', DepartmentDialogController);

    DepartmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Department', 'Requisition', 'PurchaseOrder'];

    function DepartmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Department, Requisition, PurchaseOrder) {
        var vm = this;

        vm.department = entity;
        vm.clear = clear;
        vm.save = save;
        vm.requisitions = Requisition.query();
        vm.purchaseorders = PurchaseOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.department.id !== null) {
                Department.update(vm.department, onSaveSuccess, onSaveError);
            } else {
                Department.save(vm.department, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epurchaseApp:departmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
