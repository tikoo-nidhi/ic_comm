package com.app.iccomm.Model;

public class AddressModel {
    private String name, std_code, mobile, pin_code, building, town, district, state, country, address_type;
    int key;
    Boolean selectAddress=false;

    public AddressModel() {
    }

    public AddressModel(int key, String name, String std_code, String mobile, String pin_code, String building, String town, String district, String state, String country, String address_type) {
        this.key = key;
        this.name = name;
        this.std_code = std_code;
        this.mobile = mobile;
        this.pin_code = pin_code;
        this.building = building;
        this.town = town;
        this.district = district;
        this.state = state;
        this.country = country;
        this.address_type = address_type;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStd_code() {
        return std_code;
    }

    public void setStd_code(String std_code) {
        this.std_code = std_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public Boolean getSelectAddress() {
        return selectAddress;
    }

    public void setSelectAddress(Boolean selectAddress) {
        this.selectAddress = selectAddress;
    }
}
