package com.caudron.amusementpark.models.entities.general_preferences;

public class GeneralConfig {

    private String mainPageCountryCode;
    private String mainPageCountryName;
    private boolean makeDatasOffline;

    public String getMainPageCountryCode() {
        return mainPageCountryCode;
    }

    public void setMainPageCountryCode(String mainPageCountryCode) {
        this.mainPageCountryCode = mainPageCountryCode;
    }

    public boolean isMakeDatasOffline() {
        return makeDatasOffline;
    }

    public void setMakeDatasOffline(boolean makeDatasOffline) {
        this.makeDatasOffline = makeDatasOffline;
    }

    public String getMainPageCountryName() {
        return mainPageCountryName;
    }

    public void setMainPageCountryName(String mainPageCountryName) {
        this.mainPageCountryName = mainPageCountryName;
    }
}
