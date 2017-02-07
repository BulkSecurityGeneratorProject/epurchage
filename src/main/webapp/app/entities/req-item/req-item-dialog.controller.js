(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('ReqItemDialogController', ReqItemDialogController);

    ReqItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReqItem', 'Requisition'];

    function ReqItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReqItem, Requisition) {
        var vm = this;

        vm.reqItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.requisitions = Requisition.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reqItem.id !== null) {
                ReqItem.update(vm.reqItem, onSaveSuccess, onSaveError);
            } else {
                ReqItem.save(vm.reqItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epurchaseApp:reqItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
