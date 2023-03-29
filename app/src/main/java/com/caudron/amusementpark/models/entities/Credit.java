package com.caudron.amusementpark.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "credits")
public class Credit {
    @PrimaryKey
    @NonNull
    private String coaster;
    private String credit;
    @Ignore
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
