package com.epam.hotel.entity;

public class OrderFacilityDetail implements BaseEntityMarker {
    private long id;
    private String facilityPackageName;

    public OrderFacilityDetail() {
    }

    public OrderFacilityDetail(long id, String facilityPackage) {
        this.id = id;
        this.facilityPackageName = facilityPackage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacilityPackageName() {
        return facilityPackageName;
    }

    public void setFacilityPackageName(String facilityPackageName) {
        this.facilityPackageName = facilityPackageName;
    }

    @Override
    public String toString() {
        return "OrderFacilityDetail{" +
                "id=" + id +
                ", facilityPackage='" + facilityPackageName + '\'' +
                '}';
    }
}
