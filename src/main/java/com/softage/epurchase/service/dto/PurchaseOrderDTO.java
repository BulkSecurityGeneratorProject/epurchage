package com.softage.epurchase.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the PurchaseOrder entity.
 */
public class PurchaseOrderDTO implements Serializable {

    private Long id;

    private String poNumber;

    private Long poDate;

    private String devAddress;

    private String billAddress;

    private String vendorAddress;

    private String particulars;

    private Long quantity;

    private Long unitPrice;

    private Long totalPrice;

    private Long esicDeduction;

    private Long grandTotal;


    private Long departmentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
    public Long getPoDate() {
        return poDate;
    }

    public void setPoDate(Long poDate) {
        this.poDate = poDate;
    }
    public String getDevAddress() {
        return devAddress;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }
    public String getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }
    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }
    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Long getEsicDeduction() {
        return esicDeduction;
    }

    public void setEsicDeduction(Long esicDeduction) {
        this.esicDeduction = esicDeduction;
    }
    public Long getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Long grandTotal) {
        this.grandTotal = grandTotal;
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

        PurchaseOrderDTO purchaseOrderDTO = (PurchaseOrderDTO) o;

        if ( ! Objects.equals(id, purchaseOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
            "id=" + id +
            ", poNumber='" + poNumber + "'" +
            ", poDate='" + poDate + "'" +
            ", devAddress='" + devAddress + "'" +
            ", billAddress='" + billAddress + "'" +
            ", vendorAddress='" + vendorAddress + "'" +
            ", particulars='" + particulars + "'" +
            ", quantity='" + quantity + "'" +
            ", unitPrice='" + unitPrice + "'" +
            ", totalPrice='" + totalPrice + "'" +
            ", esicDeduction='" + esicDeduction + "'" +
            ", grandTotal='" + grandTotal + "'" +
            '}';
    }
}
