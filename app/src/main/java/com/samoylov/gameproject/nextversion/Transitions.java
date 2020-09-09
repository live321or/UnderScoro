package com.samoylov.gameproject.nextversion;

public class Transitions implements RowType {

    private String locationName;
    public Transitions(String locationName){
        this.locationName=locationName;
    }

    public String getLocationName() {
        return locationName;
    }
}
