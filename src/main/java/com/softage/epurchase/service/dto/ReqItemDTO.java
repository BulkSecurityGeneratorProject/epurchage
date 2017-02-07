package com.softage.epurchase.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ReqItem entity.
 */
public class ReqItemDTO implements Serializable {

    private Long id;

    private Long itemId;

    private String itemName;

    private String purpose;

    private String specificationItem;

    private String location;

    private String officeCode;

    private Integer qty;

    private String unit;


    private Long requisitionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    public String getSpecificationItem() {
        return specificationItem;
    }

    public void setSpecificationItem(String specificationItem) {
        this.specificationItem = specificationItem;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReqItemDTO reqItemDTO = (ReqItemDTO) o;

        if ( ! Objects.equals(id, reqItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReqItemDTO{" +
            "id=" + id +
            ", itemId='" + itemId + "'" +
            ", itemName='" + itemName + "'" +
            ", purpose='" + purpose + "'" +
            ", specificationItem='" + specificationItem + "'" +
            ", location='" + location + "'" +
            ", officeCode='" + officeCode + "'" +
            ", qty='" + qty + "'" +
            ", unit='" + unit + "'" +
            '}';
    }
}
