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

    private Long reqNo;

    private Long reqDate;

    private Long poDate;

    private String ponumber;


    private Long employeeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getReqNo() {
        return reqNo;
    }

    public void setReqNo(Long reqNo) {
        this.reqNo = reqNo;
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
    public String getPonumber() {
        return ponumber;
    }

    public void setPonumber(String ponumber) {
        this.ponumber = ponumber;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
            ", reqNo='" + reqNo + "'" +
            ", reqDate='" + reqDate + "'" +
            ", poDate='" + poDate + "'" +
            ", ponumber='" + ponumber + "'" +
            '}';
    }
}
