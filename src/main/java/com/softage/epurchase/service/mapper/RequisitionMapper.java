package com.softage.epurchase.service.mapper;

import com.softage.epurchase.domain.*;
import com.softage.epurchase.service.dto.RequisitionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Requisition and its DTO RequisitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RequisitionMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    RequisitionDTO requisitionToRequisitionDTO(Requisition requisition);

    List<RequisitionDTO> requisitionsToRequisitionDTOs(List<Requisition> requisitions);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(target = "items", ignore = true)
    Requisition requisitionDTOToRequisition(RequisitionDTO requisitionDTO);

    List<Requisition> requisitionDTOsToRequisitions(List<RequisitionDTO> requisitionDTOs);

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
