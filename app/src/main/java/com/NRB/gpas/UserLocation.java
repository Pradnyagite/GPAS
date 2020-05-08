package com.NRB.gpas;

public class UserLocation {

    double lat,lng;
    String userName;

    public UserLocation(double lat, double lng, String userName)
    {
        this.lat = lat;
        this.lng = lng;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
