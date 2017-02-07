(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('requisition', {
            parent: 'entity',
            url: '/requisition',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.requisition.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/requisition/requisitions.html',
                    controller: 'RequisitionController',
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
        .state('requisition-detail', {
            parent: 'entity',
            url: '/requisition/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.requisition.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/requisition/requisition-detail.html',
                    controller: 'RequisitionDetailController',
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
                        name: $state.current.name || 'requisition',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('requisition-detail.edit', {
            parent: 'requisition-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisition-dialog.html',
                    controller: 'RequisitionDialogController',
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
        .state('requisition.new', {
            parent: 'requisition',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisition-dialog.html',
                    controller: 'RequisitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                reqNumber: null,
                                poNumber: null,
                                reqDate: null,
                                poDate: null,
                                shipAddress: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('requisition', null, { reload: 'requisition' });
                }, function() {
                    $state.go('requisition');
                });
            }]
        })
        .state('requisition.edit', {
            parent: 'requisition',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisition-dialog.html',
                    controller: 'RequisitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Requisition', function(Requisition) {
                            return Requisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('requisition', null, { reload: 'requisition' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('requisition.delete', {
            parent: 'requisition',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requisition/requisition-delete-dialog.html',
                    controller: 'RequisitionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Requisition', function(Requisition) {
                            return Requisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('requisition', null, { reload: 'requisition' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
