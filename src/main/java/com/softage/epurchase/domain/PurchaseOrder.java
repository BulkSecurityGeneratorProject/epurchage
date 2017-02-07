package com.softage.epurchase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "purchaseorder")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "po_date")
    private Long poDate;

    @Column(name = "dev_address")
    private String devAddress;

    @Column(name = "bill_address")
    private String billAddress;

    @Column(name = "vendor_address")
    private String vendorAddress;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "unit_price")
    private Long unitPrice;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "esic_deduction")
    private Long esicDeduction;

    @Column(name = "grand_total")
    private Long grandTotal;

    @ManyToOne
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public PurchaseOrder poNumber(String poNumber) {
        this.poNumber = poNumber;
        return this;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Long getPoDate() {
        return poDate;
    }

    public PurchaseOrder poDate(Long poDate) {
        this.poDate = poDate;
        return this;
    }

    public void setPoDate(Long poDate) {
        this.poDate = poDate;
    }

    public String getDevAddress() {
        return devAddress;
    }

    public PurchaseOrder devAddress(String devAddress) {
        this.devAddress = devAddress;
        return this;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public PurchaseOrder billAddress(String billAddress) {
        this.billAddress = billAddress;
        return this;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public PurchaseOrder vendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
        return this;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getParticulars() {
        return particulars;
    }

    public PurchaseOrder particulars(String particulars) {
        this.particulars = particulars;
        return this;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Long getQuantity() {
        return quantity;
    }

    public PurchaseOrder quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public PurchaseOrder unitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public PurchaseOrder totalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getEsicDeduction() {
        return esicDeduction;
    }

    public PurchaseOrder esicDeduction(Long esicDeduction) {
        this.esicDeduction = esicDeduction;
        return this;
    }

    public void setEsicDeduction(Long esicDeduction) {
        this.esicDeduction = esicDeduction;
    }

    public Long getGrandTotal() {
        return grandTotal;
    }

    public PurchaseOrder grandTotal(Long grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public void setGrandTotal(Long grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Department getDepartment() {
        return department;
    }

    public PurchaseOrder department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        if (purchaseOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, purchaseOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
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
