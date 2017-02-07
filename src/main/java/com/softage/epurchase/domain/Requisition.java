package com.softage.epurchase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Requisition.
 */
@Entity
@Table(name = "requisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "requisition")
public class Requisition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "req_number")
    private Long reqNumber;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "req_date")
    private Long reqDate;

    @Column(name = "po_date")
    private Long poDate;

    @Column(name = "ship_address")
    private String shipAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private PurchaseOrder requisition;

    @OneToMany(mappedBy = "requisition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReqItem> requisitions = new HashSet<>();

    @ManyToOne
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReqNumber() {
        return reqNumber;
    }

    public Requisition reqNumber(Long reqNumber) {
        this.reqNumber = reqNumber;
        return this;
    }

    public void setReqNumber(Long reqNumber) {
        this.reqNumber = reqNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public Requisition poNumber(String poNumber) {
        this.poNumber = poNumber;
        return this;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Long getReqDate() {
        return reqDate;
    }

    public Requisition reqDate(Long reqDate) {
        this.reqDate = reqDate;
        return this;
    }

    public void setReqDate(Long reqDate) {
        this.reqDate = reqDate;
    }

    public Long getPoDate() {
        return poDate;
    }

    public Requisition poDate(Long poDate) {
        this.poDate = poDate;
        return this;
    }

    public void setPoDate(Long poDate) {
        this.poDate = poDate;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public Requisition shipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
        return this;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public PurchaseOrder getRequisition() {
        return requisition;
    }

    public Requisition requisition(PurchaseOrder purchaseOrder) {
        this.requisition = purchaseOrder;
        return this;
    }

    public void setRequisition(PurchaseOrder purchaseOrder) {
        this.requisition = purchaseOrder;
    }

    public Set<ReqItem> getRequisitions() {
        return requisitions;
    }

    public Requisition requisitions(Set<ReqItem> reqItems) {
        this.requisitions = reqItems;
        return this;
    }

    public Requisition addRequisition(ReqItem reqItem) {
        requisitions.add(reqItem);
        reqItem.setRequisition(this);
        return this;
    }

    public Requisition removeRequisition(ReqItem reqItem) {
        requisitions.remove(reqItem);
        reqItem.setRequisition(null);
        return this;
    }

    public void setRequisitions(Set<ReqItem> reqItems) {
        this.requisitions = reqItems;
    }

    public Department getDepartment() {
        return department;
    }

    public Requisition department(Department department) {
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
        Requisition requisition = (Requisition) o;
        if (requisition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, requisition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Requisition{" +
            "id=" + id +
            ", reqNumber='" + reqNumber + "'" +
            ", poNumber='" + poNumber + "'" +
            ", reqDate='" + reqDate + "'" +
            ", poDate='" + poDate + "'" +
            ", shipAddress='" + shipAddress + "'" +
            '}';
    }
}
