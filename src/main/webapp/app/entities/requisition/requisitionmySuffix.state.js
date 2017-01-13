(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('requisitionmySuffix', {
            parent: 'entity',
            url: '/requisitionmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.requisition.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/requisition/requisitionsmySuffix.html',
                    controller: 'RequisitionMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('requisition');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('requisitionmySuffix-detail', {
            parent: 'entity',
            url: '/requisitionmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.requisition.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/requisition/requisitionmySuffix-detail.html',
                    controller: 'RequisitionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('requisition');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Requisition', function($stateParams, Requisition) {
                    return Requisition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'requisitionmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('requisitionmySuffix-detail.edit', {
            parent: 'requisitionmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisitionmySuffix-dialog.html',
                    controller: 'RequisitionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Requisition', function(Requisition) {
                            return Requisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('requisitionmySuffix.new', {
            parent: 'requisitionmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisitionmySuffix-dialog.html',
                    controller: 'RequisitionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                reqNo: null,
                                reqDate: null,
                                poDate: null,
                                ponumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('requisitionmySuffix', null, { reload: 'requisitionmySuffix' });
                }, function() {
                    $state.go('requisitionmySuffix');
                });
            }]
        })
        .state('requisitionmySuffix.edit', {
            parent: 'requisitionmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisitionmySuffix-dialog.html',
                    controller: 'RequisitionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Requisition', function(Requisition) {
                            return Requisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('requisitionmySuffix', null, { reload: 'requisitionmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('requisitionmySuffix.delete', {
            parent: 'requisitionmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisitionmySuffix-delete-dialog.html',
                    controller: 'RequisitionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Requisition', function(Requisition) {
                            return Requisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('requisitionmySuffix', null, { reload: 'requisitionmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
