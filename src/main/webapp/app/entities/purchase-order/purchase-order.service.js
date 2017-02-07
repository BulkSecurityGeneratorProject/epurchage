(function() {
    'use strict';
    angular
        .module('epurchaseApp')
        .factory('PurchaseOrder', PurchaseOrder);

    PurchaseOrder.$inject = ['$resource'];

    function PurchaseOrder ($resource) {
        var resourceUrl =  'api/purchase-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
