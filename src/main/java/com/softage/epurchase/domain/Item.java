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
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand_type")
    private String brandType;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vendor> vendors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandType() {
        return brandType;
    }

    public Item brandType(String brandType) {
        this.brandType = brandType;
        return this;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public Item vendors(Set<Vendor> vendors) {
        this.vendors = vendors;
        return this;
    }

    public Item addVendor(Vendor vendor) {
        vendors.add(vendor);
        vendor.getItems().add(this);
        return this;
    }

    public Item removeVendor(Vendor vendor) {
        vendors.remove(vendor);
        vendor.getItems().remove(this);
        return this;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors = vendors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        if (item.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", brandType='" + brandType + "'" +
            '}';
    }
}
