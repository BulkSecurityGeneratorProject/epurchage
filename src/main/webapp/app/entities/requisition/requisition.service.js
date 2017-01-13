(function() {
    'use strict';
    angular
        .module('epurchaseApp')
        .factory('Requisition', Requisition);

    Requisition.$inject = ['$resource'];

    function Requisition ($resource) {
        var resourceUrl =  'api/requisitions/:id';

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
