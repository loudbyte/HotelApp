package com.epam.hotel.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FacilityPackage extends BaseEntity {

    private Map<Integer, String> facilityPackageNameMap;
    private BigDecimal discount;
    private List<Facility> facilityList;

    public List<Facility> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    public FacilityPackage(Map<Integer, String> facilityPackageNameMap, List<Facility> facilityList, BigDecimal discount) {
        this.facilityPackageNameMap = facilityPackageNameMap;
        this.discount = discount;
        this.facilityList = facilityList;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public FacilityPackage() {
    }

    public Map<Integer, String> getFacilityPackageNameMap() {
        return facilityPackageNameMap;
    }

    public void setFacilityPackageNameMap(Map<Integer, String> facilityPackageNameMap) {
        this.facilityPackageNameMap = facilityPackageNameMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FacilityPackage{" +
                "id=" + id +
                ", facilityPackageNameMap=" + facilityPackageNameMap +
                ", discount=" + discount +
                ", facilityList=" + facilityList +
                '}';
    }
}