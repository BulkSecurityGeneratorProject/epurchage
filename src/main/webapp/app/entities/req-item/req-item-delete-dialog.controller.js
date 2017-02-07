(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('ReqItemDeleteController',ReqItemDeleteController);

    ReqItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReqItem'];

    function ReqItemDeleteController($uibModalInstance, entity, ReqItem) {
        var vm = this;

        vm.reqItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReqItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
