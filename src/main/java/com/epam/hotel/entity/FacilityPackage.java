package com.epam.hotel.entity;

import java.util.List;

public class FacilityPackage implements BaseEntityMarker{
        long orderFacilityDetailId;
        String facilityPackageName;
        List<Facility> facilityList;

        public FacilityPackage() {
        }

        public FacilityPackage(long orderFacilityDetailId, String facilityPackageName, List<Facility> facilityList) {
                this.orderFacilityDetailId = orderFacilityDetailId;
                this.facilityPackageName = facilityPackageName;
                this.facilityList = facilityList;
        }

        public long getOrderFacilityDetailId() {
                return orderFacilityDetailId;
        }

        public void setOrderFacilityDetailId(long orderFacilityDetailId) {
                this.orderFacilityDetailId = orderFacilityDetailId;
        }

        public String getFacilityPackageName() {
                return facilityPackageName;
        }

        public void setFacilityPackageName(String facilityPackageName) {
                this.facilityPackageName = facilityPackageName;
        }

        public List<Facility> getFacilityList() {
                return facilityList;
        }

        public void setFacilityList(List<Facility> facilityList) {
                this.facilityList = facilityList;
        }

        @Override
        public String toString() {
                return "FacilityPackage{" +
                        "orderFacilityDetailId=" + orderFacilityDetailId +
                        ", facilityPackageName='" + facilityPackageName + '\'' +
                        ", facilityList=" + facilityList +
                        '}';
        }
}