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

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "department.id", target = "departmentId")
    RequisitionDTO requisitionToRequisitionDTO(Requisition requisition);

    List<RequisitionDTO> requisitionsToRequisitionDTOs(List<Requisition> requisitions);

    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(target = "requisitions", ignore = true)
    @Mapping(source = "departmentId", target = "department")
    Requisition requisitionDTOToRequisition(RequisitionDTO requisitionDTO);

    List<Requisition> requisitionDTOsToRequisitions(List<RequisitionDTO> requisitionDTOs);

    default PurchaseOrder purchaseOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
    }

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
