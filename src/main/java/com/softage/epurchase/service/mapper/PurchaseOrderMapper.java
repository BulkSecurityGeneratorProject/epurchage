package com.softage.epurchase.service.mapper;

import com.softage.epurchase.domain.*;
import com.softage.epurchase.service.dto.PurchaseOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PurchaseOrder and its DTO PurchaseOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseOrderMapper {

    @Mapping(source = "department.id", target = "departmentId")
    PurchaseOrderDTO purchaseOrderToPurchaseOrderDTO(PurchaseOrder purchaseOrder);

    List<PurchaseOrderDTO> purchaseOrdersToPurchaseOrderDTOs(List<PurchaseOrder> purchaseOrders);

    @Mapping(source = "departmentId", target = "department")
    PurchaseOrder purchaseOrderDTOToPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);

    List<PurchaseOrder> purchaseOrderDTOsToPurchaseOrders(List<PurchaseOrderDTO> purchaseOrderDTOs);

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
