package com.softage.epurchase.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Vendor entity.
 */
public class VendorDTO implements Serializable {

    private Long id;

    private String regNo;

    private String companyName;

    private String contactPerson;

    private String address;

    private String productType;

    private String email;

    private String panNumber;

    private String tinNumber;

    private String stNumber;

    private String exerciseNumber;

    private String pfNumber;

    private String esicNumber;

    private String accountName;

    private Long accountNumber;

    private String ifscCode;

    private String swiftNumber;

    private String bankBranch;

    private String vendorType;

    private String vendorstatus;

    private String vendorTimeSpan;


    private Set<ItemDTO> items = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }
    public String getStNumber() {
        return stNumber;
    }

    public void setStNumber(String stNumber) {
        this.stNumber = stNumber;
    }
    public String getExerciseNumber() {
        return exerciseNumber;
    }

    public void setExerciseNumber(String exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }
    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }
    public String getEsicNumber() {
        return esicNumber;
    }

    public void setEsicNumber(String esicNumber) {
        this.esicNumber = esicNumber;
    }
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getSwiftNumber() {
        return swiftNumber;
    }

    public void setSwiftNumber(String swiftNumber) {
        this.swiftNumber = swiftNumber;
    }
    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }
    public String getVendorstatus() {
        return vendorstatus;
    }

    public void setVendorstatus(String vendorstatus) {
        this.vendorstatus = vendorstatus;
    }
    public String getVendorTimeSpan() {
        return vendorTimeSpan;
    }

    public void setVendorTimeSpan(String vendorTimeSpan) {
        this.vendorTimeSpan = vendorTimeSpan;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<ItemDTO> items) {
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

        VendorDTO vendorDTO = (VendorDTO) o;

        if ( ! Objects.equals(id, vendorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VendorDTO{" +
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
