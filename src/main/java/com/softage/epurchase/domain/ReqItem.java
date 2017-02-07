package com.softage.epurchase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReqItem.
 */
@Entity
@Table(name = "req_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reqitem")
public class ReqItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "specification_item")
    private String specificationItem;

    @Column(name = "location")
    private String location;

    @Column(name = "office_code")
    private String officeCode;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "unit")
    private String unit;

    @ManyToOne
    private Requisition requisition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public ReqItem itemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public ReqItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPurpose() {
        return purpose;
    }

    public ReqItem purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSpecificationItem() {
        return specificationItem;
    }

    public ReqItem specificationItem(String specificationItem) {
        this.specificationItem = specificationItem;
        return this;
    }

    public void setSpecificationItem(String specificationItem) {
        this.specificationItem = specificationItem;
    }

    public String getLocation() {
        return location;
    }

    public ReqItem location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public ReqItem officeCode(String officeCode) {
        this.officeCode = officeCode;
        return this;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public Integer getQty() {
        return qty;
    }

    public ReqItem qty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public ReqItem unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public ReqItem requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReqItem reqItem = (ReqItem) o;
        if (reqItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reqItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReqItem{" +
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
