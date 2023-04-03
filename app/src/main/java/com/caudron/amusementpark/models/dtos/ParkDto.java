package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

public class ParkDto {
    private int id;

    @SerializedName("@id")
    private String idUri;
    private String name;
    private CountryDto country;
    private double latitude;
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdUri() {
        return idUri;
    }

    public void setIdUri(String idUri) {
        this.idUri = idUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryDto getCountry() {
        return country;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
