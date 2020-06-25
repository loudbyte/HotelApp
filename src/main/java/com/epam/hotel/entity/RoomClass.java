package com.epam.hotel.entity;

import java.util.Map;

public class RoomClass implements BaseEntityMarker {

    private long id;
    private Map<Integer, String> roomClassNameMap;
    private Map<Integer, String> roomClassDescriptionMap;

    public RoomClass() {
    }

    public RoomClass(Map<Integer, String> roomClassNameMap, Map<Integer, String> orderStatusDescriptionMap) {
        this.roomClassNameMap = roomClassNameMap;
        this.roomClassDescriptionMap = orderStatusDescriptionMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Integer, String> getRoomClassNameMap() {
        return roomClassNameMap;
    }

    public void setRoomClassNameMap(Map<Integer, String> roomClassNameMap) {
        this.roomClassNameMap = roomClassNameMap;
    }

    public Map<Integer, String> getRoomClassDescriptionMap() {
        return roomClassDescriptionMap;
    }

    public void setRoomClassDescriptionMap(Map<Integer, String> roomClassDescriptionMap) {
        this.roomClassDescriptionMap = roomClassDescriptionMap;
    }

    @Override
    public String toString() {
        return "RoomClass{" +
                "id=" + id +
                ", roomClassNameMap=" + roomClassNameMap +
                ", roomClassDescriptionMap=" + roomClassDescriptionMap +
                '}';
    }
}
