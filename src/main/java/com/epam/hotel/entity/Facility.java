package com.epam.hotel.entity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Facility extends BaseEntity {

    private BigDecimal price;
    private Map<Integer, String> facilityNameMap;
    private Map<Integer, String> facilityDescriptionMap;

    public Facility() {
    }

    public Facility(BigDecimal price, Map<Integer, String> facilityNameMap, Map<Integer, String> facilityDescriptionMap) {
        this.price = price;
        this.facilityNameMap = facilityNameMap;
        this.facilityDescriptionMap = facilityDescriptionMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<Integer, String> getFacilityNameMap() {
        return facilityNameMap;
    }

    public void setFacilityNameMap(Map<Integer, String> facilityNameMap) {
        this.facilityNameMap = facilityNameMap;
    }

    public Map<Integer, String> getFacilityDescriptionMap() {
        return facilityDescriptionMap;
    }

    public void setFacilityDescriptionMap(Map<Integer, String> facilityDescriptionMap) {
        this.facilityDescriptionMap = facilityDescriptionMap;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", price=" + price +
                ", facilityNameMap=" + facilityNameMap +
                ", facilityDescriptionMap=" + facilityDescriptionMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return Objects.equals(price, facility.price) &&
                Objects.equals(facilityNameMap, facility.facilityNameMap) &&
                Objects.equals(facilityDescriptionMap, facility.facilityDescriptionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, facilityNameMap, facilityDescriptionMap);
    }
}