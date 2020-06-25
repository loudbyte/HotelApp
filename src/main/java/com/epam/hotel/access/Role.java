package com.epam.hotel.access;

import java.util.Map;

public enum Role {

    GUEST(AccessMap.accessGuestMap),
    CLIENT(AccessMap.accessClientMap),
    ADMIN(AccessMap.accessAdminMap);

    private final Map<String, Boolean> accessMap;

    Role(Map<String, Boolean> accessMap) {
        this.accessMap = accessMap;
    }

    public Map<String, Boolean> getAccessMap() {
        return accessMap;
    }
}