package com.softage.epurchase.service.mapper;

import com.softage.epurchase.domain.*;
import com.softage.epurchase.service.dto.ReqItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ReqItem and its DTO ReqItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReqItemMapper {

    @Mapping(source = "requisition.id", target = "requisitionId")
    ReqItemDTO reqItemToReqItemDTO(ReqItem reqItem);

    List<ReqItemDTO> reqItemsToReqItemDTOs(List<ReqItem> reqItems);

    @Mapping(source = "requisitionId", target = "requisition")
    ReqItem reqItemDTOToReqItem(ReqItemDTO reqItemDTO);

    List<ReqItem> reqItemDTOsToReqItems(List<ReqItemDTO> reqItemDTOs);

    default Requisition requisitionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Requisition requisition = new Requisition();
        requisition.setId(id);
        return requisition;
    }
}
