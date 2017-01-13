(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .controller('ItemMySuffixDetailController', ItemMySuffixDetailController);

    ItemMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Item', 'Requisition'];

    function ItemMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Item, Requisition) {
        var vm = this;

        vm.item = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epurchaseApp:itemUpdate', function(event, result) {
            vm.item = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
