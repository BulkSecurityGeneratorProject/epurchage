package com.softage.epurchase.service.mapper;

import com.softage.epurchase.domain.*;
import com.softage.epurchase.service.dto.VendorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Vendor and its DTO VendorDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, })
public interface VendorMapper {

    VendorDTO vendorToVendorDTO(Vendor vendor);

    List<VendorDTO> vendorsToVendorDTOs(List<Vendor> vendors);

    Vendor vendorDTOToVendor(VendorDTO vendorDTO);

    List<Vendor> vendorDTOsToVendors(List<VendorDTO> vendorDTOs);

    default Item itemFromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
