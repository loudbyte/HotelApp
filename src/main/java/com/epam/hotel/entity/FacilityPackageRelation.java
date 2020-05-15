package com.epam.hotel.entity;

public class FacilityPackageRelation implements BaseEntityMarker {
    private long packageId;

    private long facilityId;

    public FacilityPackageRelation() {
    }

    public FacilityPackageRelation(long packageId, long facilityId) {
        this.packageId = packageId;
        this.facilityId = facilityId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(long facilityId) {
        this.facilityId = facilityId;
    }

    @Override
    public String toString() {
        return "FacilityPackage{" +
                "packageId=" + packageId +
                ", facilityId=" + facilityId +
                '}';
    }
}
