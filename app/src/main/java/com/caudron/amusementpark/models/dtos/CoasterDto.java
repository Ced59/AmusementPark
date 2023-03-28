package com.caudron.amusementpark.models.dtos;

import java.util.List;

public class CoasterDto {
    private int id;
    private String name;
    private MaterialTypeDto materialType;
    private SeatingTypeDto seatingType;
    private int speed;
    private int height;
    private int length;
    private int inversionsNumber;
    private ManufacturerDto manufacturer;
    private ParkDto park;
    private StatusDto status;
    private int totalRatings;
    private int validDuels;
    private String score;
    private int rank;
    private ImageDto mainImage;
    private List<CreditDto> credits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialTypeDto getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialTypeDto materialType) {
        this.materialType = materialType;
    }

    public SeatingTypeDto getSeatingType() {
        return seatingType;
    }

    public void setSeatingType(SeatingTypeDto seatingType) {
        this.seatingType = seatingType;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getInversionsNumber() {
        return inversionsNumber;
    }

    public void setInversionsNumber(int inversionsNumber) {
        this.inversionsNumber = inversionsNumber;
    }

    public ManufacturerDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ParkDto getPark() {
        return park;
    }

    public void setPark(ParkDto park) {
        this.park = park;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public int getValidDuels() {
        return validDuels;
    }

    public void setValidDuels(int validDuels) {
        this.validDuels = validDuels;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ImageDto getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageDto mainImage) {
        this.mainImage = mainImage;
    }

    public List<CreditDto> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditDto> credits) {
        this.credits = credits;
    }
}
