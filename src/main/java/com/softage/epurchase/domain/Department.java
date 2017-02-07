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
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "depart_id")
    private Long departId;

    @Column(name = "depart_name")
    private String departName;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requisition> departmentreqs = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrder> departmentords = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartId() {
        return departId;
    }

    public Department departId(Long departId) {
        this.departId = departId;
        return this;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public Department departName(String departName) {
        this.departName = departName;
        return this;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Set<Requisition> getDepartmentreqs() {
        return departmentreqs;
    }

    public Department departmentreqs(Set<Requisition> requisitions) {
        this.departmentreqs = requisitions;
        return this;
    }

    public Department addDepartmentreq(Requisition requisition) {
        departmentreqs.add(requisition);
        requisition.setDepartment(this);
        return this;
    }

    public Department removeDepartmentreq(Requisition requisition) {
        departmentreqs.remove(requisition);
        requisition.setDepartment(null);
        return this;
    }

    public void setDepartmentreqs(Set<Requisition> requisitions) {
        this.departmentreqs = requisitions;
    }

    public Set<PurchaseOrder> getDepartmentords() {
        return departmentords;
    }

    public Department departmentords(Set<PurchaseOrder> purchaseOrders) {
        this.departmentords = purchaseOrders;
        return this;
    }

    public Department addDepartmentord(PurchaseOrder purchaseOrder) {
        departmentords.add(purchaseOrder);
        purchaseOrder.setDepartment(this);
        return this;
    }

    public Department removeDepartmentord(PurchaseOrder purchaseOrder) {
        departmentords.remove(purchaseOrder);
        purchaseOrder.setDepartment(null);
        return this;
    }

    public void setDepartmentords(Set<PurchaseOrder> purchaseOrders) {
        this.departmentords = purchaseOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department department = (Department) o;
        if (department.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, department.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + id +
            ", departId='" + departId + "'" +
            ", departName='" + departName + "'" +
            '}';
    }
}
