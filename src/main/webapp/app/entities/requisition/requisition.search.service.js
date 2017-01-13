(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .factory('RequisitionSearch', RequisitionSearch);

    RequisitionSearch.$inject = ['$resource'];

    function RequisitionSearch($resource) {
        var resourceUrl =  'api/_search/requisitions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
