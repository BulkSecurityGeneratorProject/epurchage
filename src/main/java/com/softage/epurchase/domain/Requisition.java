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

    @Column(name = "req_no")
    private Long reqNo;

    @Column(name = "req_date")
    private Long reqDate;

    @Column(name = "po_date")
    private Long poDate;

    @Column(name = "ponumber")
    private String ponumber;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "requisition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReqNo() {
        return reqNo;
    }

    public Requisition reqNo(Long reqNo) {
        this.reqNo = reqNo;
        return this;
    }

    public void setReqNo(Long reqNo) {
        this.reqNo = reqNo;
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

    public String getPonumber() {
        return ponumber;
    }

    public Requisition ponumber(String ponumber) {
        this.ponumber = ponumber;
        return this;
    }

    public void setPonumber(String ponumber) {
        this.ponumber = ponumber;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Requisition employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Requisition items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Requisition addItem(Item item) {
        items.add(item);
        item.setRequisition(this);
        return this;
    }

    public Requisition removeItem(Item item) {
        items.remove(item);
        item.setRequisition(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
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
            ", reqNo='" + reqNo + "'" +
            ", reqDate='" + reqDate + "'" +
            ", poDate='" + poDate + "'" +
            ", ponumber='" + ponumber + "'" +
            '}';
    }
}
