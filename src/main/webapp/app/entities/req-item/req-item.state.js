(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('req-item', {
            parent: 'entity',
            url: '/req-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.reqItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/req-item/req-items.html',
                    controller: 'ReqItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reqItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('req-item-detail', {
            parent: 'entity',
            url: '/req-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.reqItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/req-item/req-item-detail.html',
                    controller: 'ReqItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reqItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ReqItem', function($stateParams, ReqItem) {
                    return ReqItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'req-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('req-item-detail.edit', {
            parent: 'req-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/req-item/req-item-dialog.html',
                    controller: 'ReqItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReqItem', function(ReqItem) {
                            return ReqItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('req-item.new', {
            parent: 'req-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/req-item/req-item-dialog.html',
                    controller: 'ReqItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                itemId: null,
                                itemName: null,
                                purpose: null,
                                specificationItem: null,
                                location: null,
                                officeCode: null,
                                qty: null,
                                unit: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('req-item', null, { reload: 'req-item' });
                }, function() {
                    $state.go('req-item');
                });
            }]
        })
        .state('req-item.edit', {
            parent: 'req-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/req-item/req-item-dialog.html',
                    controller: 'ReqItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReqItem', function(ReqItem) {
                            return ReqItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('req-item', null, { reload: 'req-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('req-item.delete', {
            parent: 'req-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/req-item/req-item-delete-dialog.html',
                    controller: 'ReqItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReqItem', function(ReqItem) {
                            return ReqItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('req-item', null, { reload: 'req-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
