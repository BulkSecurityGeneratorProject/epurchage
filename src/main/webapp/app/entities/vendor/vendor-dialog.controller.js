(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('VendorDialogController', VendorDialogController);

    VendorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vendor', 'Item'];

    function VendorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Vendor, Item) {
        var vm = this;

        vm.vendor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.items = Item.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vendor.id !== null) {
                Vendor.update(vm.vendor, onSaveSuccess, onSaveError);
            } else {
                Vendor.save(vm.vendor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epurchaseApp:vendorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
