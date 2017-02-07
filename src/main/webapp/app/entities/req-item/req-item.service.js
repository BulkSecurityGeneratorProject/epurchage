(function() {
    'use strict';
    angular
        .module('epurchaseApp')
        .factory('ReqItem', ReqItem);

    ReqItem.$inject = ['$resource'];

    function ReqItem ($resource) {
        var resourceUrl =  'api/req-items/:id';

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
