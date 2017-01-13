(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('itemmySuffix', {
            parent: 'entity',
            url: '/itemmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.item.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/item/itemsmySuffix.html',
                    controller: 'ItemMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('item');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('itemmySuffix-detail', {
            parent: 'entity',
            url: '/itemmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.item.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/item/itemmySuffix-detail.html',
                    controller: 'ItemMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('item');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Item', function($stateParams, Item) {
                    return Item.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'itemmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('itemmySuffix-detail.edit', {
            parent: 'itemmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item/itemmySuffix-dialog.html',
                    controller: 'ItemMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Item', function(Item) {
                            return Item.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('itemmySuffix.new', {
            parent: 'itemmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item/itemmySuffix-dialog.html',
                    controller: 'ItemMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                itemName: null,
                                itemType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('itemmySuffix', null, { reload: 'itemmySuffix' });
                }, function() {
                    $state.go('itemmySuffix');
                });
            }]
        })
        .state('itemmySuffix.edit', {
            parent: 'itemmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item/itemmySuffix-dialog.html',
                    controller: 'ItemMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Item', function(Item) {
                            return Item.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('itemmySuffix', null, { reload: 'itemmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('itemmySuffix.delete', {
            parent: 'itemmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item/itemmySuffix-delete-dialog.html',
                    controller: 'ItemMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Item', function(Item) {
                            return Item.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('itemmySuffix', null, { reload: 'itemmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
