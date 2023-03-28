package com.caudron.amusementpark.models.dtos;

public class CreditDto {
    private String coaster;
    private String credit;

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

    public ImageDto getImage() {
        return image;
    }

    public void setImage(ImageDto image) {
        this.image = image;
    }

    private ImageDto image;
}
