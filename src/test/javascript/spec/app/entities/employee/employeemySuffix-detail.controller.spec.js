'use strict';

describe('Controller Tests', function() {

    describe('Employee Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmployee, MockDepartment, MockRequisition;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockRequisition = jasmine.createSpy('MockRequisition');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Employee': MockEmployee,
                'Department': MockDepartment,
                'Requisition': MockRequisition
            };
            createController = function() {
                $injector.get('$controller')("EmployeeMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'epurchaseApp:employeeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
