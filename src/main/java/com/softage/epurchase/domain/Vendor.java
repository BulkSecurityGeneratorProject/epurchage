package com.softage.epurchase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vendor")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "address")
    private String address;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "email")
    private String email;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "tin_number")
    private String tinNumber;

    @Column(name = "st_number")
    private String stNumber;

    @Column(name = "exercise_number")
    private String exerciseNumber;

    @Column(name = "pf_number")
    private String pfNumber;

    @Column(name = "esic_number")
    private String esicNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "swift_number")
    private String swiftNumber;

    @Column(name = "bank_branch")
    private String bankBranch;

    @Column(name = "vendor_type")
    private String vendorType;

    @Column(name = "vendorstatus")
    private String vendorstatus;

    @Column(name = "vendor_time_span")
    private String vendorTimeSpan;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "vendor_item",
               joinColumns = @JoinColumn(name="vendors_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="items_id", referencedColumnName="ID"))
    private Set<Item> items = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public Vendor regNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Vendor companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Vendor contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public Vendor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductType() {
        return productType;
    }

    public Vendor productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getEmail() {
        return email;
    }

    public Vendor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public Vendor panNumber(String panNumber) {
        this.panNumber = panNumber;
        return this;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public Vendor tinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
        return this;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getStNumber() {
        return stNumber;
    }

    public Vendor stNumber(String stNumber) {
        this.stNumber = stNumber;
        return this;
    }

    public void setStNumber(String stNumber) {
        this.stNumber = stNumber;
    }

    public String getExerciseNumber() {
        return exerciseNumber;
    }

    public Vendor exerciseNumber(String exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
        return this;
    }

    public void setExerciseNumber(String exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public Vendor pfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
        return this;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public String getEsicNumber() {
        return esicNumber;
    }

    public Vendor esicNumber(String esicNumber) {
        this.esicNumber = esicNumber;
        return this;
    }

    public void setEsicNumber(String esicNumber) {
        this.esicNumber = esicNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public Vendor accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Vendor accountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public Vendor ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getSwiftNumber() {
        return swiftNumber;
    }

    public Vendor swiftNumber(String swiftNumber) {
        this.swiftNumber = swiftNumber;
        return this;
    }

    public void setSwiftNumber(String swiftNumber) {
        this.swiftNumber = swiftNumber;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public Vendor bankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
        return this;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getVendorType() {
        return vendorType;
    }

    public Vendor vendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getVendorstatus() {
        return vendorstatus;
    }

    public Vendor vendorstatus(String vendorstatus) {
        this.vendorstatus = vendorstatus;
        return this;
    }

    public void setVendorstatus(String vendorstatus) {
        this.vendorstatus = vendorstatus;
    }

    public String getVendorTimeSpan() {
        return vendorTimeSpan;
    }

    public Vendor vendorTimeSpan(String vendorTimeSpan) {
        this.vendorTimeSpan = vendorTimeSpan;
        return this;
    }

    public void setVendorTimeSpan(String vendorTimeSpan) {
        this.vendorTimeSpan = vendorTimeSpan;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Vendor items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Vendor addItem(Item item) {
        items.add(item);
        item.getVendors().add(this);
        return this;
    }

    public Vendor removeItem(Item item) {
        items.remove(item);
        item.getVendors().remove(this);
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
        Vendor vendor = (Vendor) o;
        if (vendor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vendor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + id +
            ", regNo='" + regNo + "'" +
            ", companyName='" + companyName + "'" +
            ", contactPerson='" + contactPerson + "'" +
            ", address='" + address + "'" +
            ", productType='" + productType + "'" +
            ", email='" + email + "'" +
            ", panNumber='" + panNumber + "'" +
            ", tinNumber='" + tinNumber + "'" +
            ", stNumber='" + stNumber + "'" +
            ", exerciseNumber='" + exerciseNumber + "'" +
            ", pfNumber='" + pfNumber + "'" +
            ", esicNumber='" + esicNumber + "'" +
            ", accountName='" + accountName + "'" +
            ", accountNumber='" + accountNumber + "'" +
            ", ifscCode='" + ifscCode + "'" +
            ", swiftNumber='" + swiftNumber + "'" +
            ", bankBranch='" + bankBranch + "'" +
            ", vendorType='" + vendorType + "'" +
            ", vendorstatus='" + vendorstatus + "'" +
            ", vendorTimeSpan='" + vendorTimeSpan + "'" +
            '}';
    }
}
