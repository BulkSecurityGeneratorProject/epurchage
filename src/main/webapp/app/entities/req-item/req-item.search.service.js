(function() {
    'use strict';

    angular
        .module('epurchaseApp')
        .factory('ReqItemSearch', ReqItemSearch);

    ReqItemSearch.$inject = ['$resource'];

    function ReqItemSearch($resource) {
        var resourceUrl =  'api/_search/req-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
