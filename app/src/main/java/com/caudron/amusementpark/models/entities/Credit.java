package com.caudron.amusementpark.models.entities;

public class Credit {
    private String coaster;
    private String credit;
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getCoaster() {
        return coaster;
    }

    public void setCoaster(String coaster) {
        this.coaster = coaster;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

}
