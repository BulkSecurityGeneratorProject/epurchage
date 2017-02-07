(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vendor', {
            parent: 'entity',
            url: '/vendor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.vendor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vendor/vendors.html',
                    controller: 'VendorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vendor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('vendor-detail', {
            parent: 'entity',
            url: '/vendor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epurchaseApp.vendor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vendor/vendor-detail.html',
                    controller: 'VendorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vendor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Vendor', function($stateParams, Vendor) {
                    return Vendor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vendor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vendor-detail.edit', {
            parent: 'vendor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendor.new', {
            parent: 'vendor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                regNo: null,
                                companyName: null,
                                contactPerson: null,
                                address: null,
                                productType: null,
                                email: null,
                                panNumber: null,
                                tinNumber: null,
                                stNumber: null,
                                exerciseNumber: null,
                                pfNumber: null,
                                esicNumber: null,
                                accountName: null,
                                accountNumber: null,
                                ifscCode: null,
                                swiftNumber: null,
                                bankBranch: null,
                                vendorType: null,
                                vendorstatus: null,
                                vendorTimeSpan: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: 'vendor' });
                }, function() {
                    $state.go('vendor');
                });
            }]
        })
        .state('vendor.edit', {
            parent: 'vendor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: 'vendor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendor.delete', {
            parent: 'vendor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-delete-dialog.html',
                    controller: 'VendorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: 'vendor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
