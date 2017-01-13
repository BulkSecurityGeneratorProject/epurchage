(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('EmployeeMySuffixDialogController', EmployeeMySuffixDialogController);

    EmployeeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Employee', 'Department', 'Requisition'];

    function EmployeeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Employee, Department, Requisition) {
        var vm = this;

        vm.employee = entity;
        vm.clear = clear;
        vm.save = save;
        vm.departments = Department.query({filter: 'employee-is-null'});
        $q.all([vm.employee.$promise, vm.departments.$promise]).then(function() {
            if (!vm.employee.departmentId) {
                return $q.reject();
            }
            return Department.get({id : vm.employee.departmentId}).$promise;
        }).then(function(department) {
            vm.departments.push(department);
        });
        vm.requisitions = Requisition.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employee.id !== null) {
                Employee.update(vm.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save(vm.employee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epurchaseApp:employeeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
