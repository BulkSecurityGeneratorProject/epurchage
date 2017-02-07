(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionDialogController', RequisitionDialogController);

    RequisitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Requisition', 'PurchaseOrder', 'ReqItem', 'Department'];

    function RequisitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Requisition, PurchaseOrder, ReqItem, Department) {
        var vm = this;

        vm.requisition = entity;
        vm.clear = clear;
        vm.save = save;
        vm.requisitions = PurchaseOrder.query({filter: 'requisition-is-null'});
        $q.all([vm.requisition.$promise, vm.requisitions.$promise]).then(function() {
            if (!vm.requisition.requisitionId) {
                return $q.reject();
            }
            return PurchaseOrder.get({id : vm.requisition.requisitionId}).$promise;
        }).then(function(requisition) {
            vm.requisitions.push(requisition);
        });
        vm.reqitems = ReqItem.query();
        vm.departments = Department.query();

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
