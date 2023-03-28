package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

public class ViewDto {

    @SerializedName("@id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("hydra:first")
    private String firstPage;

    @SerializedName("hydra:last")
    private String lastPage;

    @SerializedName("hydra:previous")
    private String previousPage;

    @SerializedName("hydra:next")
    private String nextPage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}






