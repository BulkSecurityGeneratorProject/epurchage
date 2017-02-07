(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('ReqItemDetailController', ReqItemDetailController);

    ReqItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReqItem', 'Requisition'];

    function ReqItemDetailController($scope, $rootScope, $stateParams, previousState, entity, ReqItem, Requisition) {
        var vm = this;

        vm.reqItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:reqItemUpdate', function(event, result) {
            vm.reqItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
