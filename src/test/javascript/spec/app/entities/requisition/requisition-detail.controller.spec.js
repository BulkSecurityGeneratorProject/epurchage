'use strict';

describe('Controller Tests', function() {

    describe('Requisition Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRequisition, MockPurchaseOrder, MockReqItem, MockDepartment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRequisition = jasmine.createSpy('MockRequisition');
            MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
            MockReqItem = jasmine.createSpy('MockReqItem');
            MockDepartment = jasmine.createSpy('MockDepartment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Requisition': MockRequisition,
                'PurchaseOrder': MockPurchaseOrder,
                'ReqItem': MockReqItem,
                'Department': MockDepartment
            };
            createController = function() {
                $injector.get('$controller')("RequisitionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'epurchaseApp:requisitionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
