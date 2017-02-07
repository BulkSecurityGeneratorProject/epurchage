(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .factory('PurchaseOrderSearch', PurchaseOrderSearch);

    PurchaseOrderSearch.$inject = ['$resource'];

    function PurchaseOrderSearch($resource) {
        var resourceUrl =  'api/_search/purchase-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
