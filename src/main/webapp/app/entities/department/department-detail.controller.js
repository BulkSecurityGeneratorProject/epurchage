(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('DepartmentDetailController', DepartmentDetailController);

    DepartmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Department', 'Requisition', 'PurchaseOrder'];

    function DepartmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Department, Requisition, PurchaseOrder) {
        var vm = this;

        vm.department = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:departmentUpdate', function(event, result) {
            vm.department = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
