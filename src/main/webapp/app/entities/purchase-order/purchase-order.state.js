(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('purchase-order', {
            parent: 'entity',
            url: '/purchase-order',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.purchaseOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase-order/purchase-orders.html',
                    controller: 'PurchaseOrderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('purchaseOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('purchase-order-detail', {
            parent: 'entity',
            url: '/purchase-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.purchaseOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase-order/purchase-order-detail.html',
                    controller: 'PurchaseOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('purchaseOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PurchaseOrder', function($stateParams, PurchaseOrder) {
                    return PurchaseOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'purchase-order',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('purchase-order-detail.edit', {
            parent: 'purchase-order-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-order-dialog.html',
                    controller: 'PurchaseOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchaseOrder', function(PurchaseOrder) {
                            return PurchaseOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase-order.new', {
            parent: 'purchase-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-order-dialog.html',
                    controller: 'PurchaseOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                poNumber: null,
                                poDate: null,
                                devAddress: null,
                                billAddress: null,
                                vendorAddress: null,
                                particulars: null,
                                quantity: null,
                                unitPrice: null,
                                totalPrice: null,
                                esicDeduction: null,
                                grandTotal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('purchase-order', null, { reload: 'purchase-order' });
                }, function() {
                    $state.go('purchase-order');
                });
            }]
        })
        .state('purchase-order.edit', {
            parent: 'purchase-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-order-dialog.html',
                    controller: 'PurchaseOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchaseOrder', function(PurchaseOrder) {
                            return PurchaseOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase-order', null, { reload: 'purchase-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase-order.delete', {
            parent: 'purchase-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase-order/purchase-order-delete-dialog.html',
                    controller: 'PurchaseOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PurchaseOrder', function(PurchaseOrder) {
                            return PurchaseOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase-order', null, { reload: 'purchase-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
