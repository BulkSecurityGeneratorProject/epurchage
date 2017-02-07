(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionDetailController', RequisitionDetailController);

    RequisitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Requisition', 'PurchaseOrder', 'ReqItem', 'Department'];

    function RequisitionDetailController($scope, $rootScope, $stateParams, previousState, entity, Requisition, PurchaseOrder, ReqItem, Department) {
        var vm = this;

        vm.requisition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:requisitionUpdate', function(event, result) {
            vm.requisition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
