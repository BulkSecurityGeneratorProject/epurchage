package com.softage.epurchase.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Requisition entity.
 */
public class RequisitionDTO implements Serializable {

    private Long id;

    private Long reqNumber;

    private String poNumber;

    private Long reqDate;

    private Long poDate;

    private String shipAddress;


    private Long requisitionId;
    
    private Long departmentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getReqNumber() {
        return reqNumber;
    }

    public void setReqNumber(Long reqNumber) {
        this.reqNumber = reqNumber;
    }
    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
    public Long getReqDate() {
        return reqDate;
    }

    public void setReqDate(Long reqDate) {
        this.reqDate = reqDate;
    }
    public Long getPoDate() {
        return poDate;
    }

    public void setPoDate(Long poDate) {
        this.poDate = poDate;
    }
    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long purchaseOrderId) {
        this.requisitionId = purchaseOrderId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequisitionDTO requisitionDTO = (RequisitionDTO) o;

        if ( ! Objects.equals(id, requisitionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RequisitionDTO{" +
            "id=" + id +
            ", reqNumber='" + reqNumber + "'" +
            ", poNumber='" + poNumber + "'" +
            ", reqDate='" + reqDate + "'" +
            ", poDate='" + poDate + "'" +
            ", shipAddress='" + shipAddress + "'" +
            '}';
    }
}
