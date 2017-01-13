(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('RequisitionMySuffixDetailController', RequisitionMySuffixDetailController);

    RequisitionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Requisition', 'Employee', 'Item'];

    function RequisitionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Requisition, Employee, Item) {
        var vm = this;

        vm.requisition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:requisitionUpdate', function(event, result) {
            vm.requisition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
