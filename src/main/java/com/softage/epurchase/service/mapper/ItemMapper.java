package com.softage.epurchase.service.mapper;

import com.softage.epurchase.domain.*;
import com.softage.epurchase.service.dto.ItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Item and its DTO ItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemMapper {

    @Mapping(source = "requisition.id", target = "requisitionId")
    ItemDTO itemToItemDTO(Item item);

    List<ItemDTO> itemsToItemDTOs(List<Item> items);

    @Mapping(source = "requisitionId", target = "requisition")
    Item itemDTOToItem(ItemDTO itemDTO);

    List<Item> itemDTOsToItems(List<ItemDTO> itemDTOs);

    default Requisition requisitionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Requisition requisition = new Requisition();
        requisition.setId(id);
        return requisition;
    }
}
