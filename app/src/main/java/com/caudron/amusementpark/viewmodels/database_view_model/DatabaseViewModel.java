package com.caudron.amusementpark.viewmodels.database_view_model;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;

import com.caudron.amusementpark.models.db.repositories.daos.CoasterDao;
import com.caudron.amusementpark.models.db.repositories.daos.CountryDao;
import com.caudron.amusementpark.models.db.repositories.daos.ImageDao;
import com.caudron.amusementpark.models.db.repositories.daos.ParkDao;
import com.caudron.amusementpark.models.db.repositories.daos.StatusDao;
import com.caudron.amusementpark.models.db.repositories.implementations.AmusementParkDatabase;
import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;
import com.caudron.amusementpark.models.entities.Coaster;
import com.caudron.amusementpark.models.entities.Country;
import com.caudron.amusementpark.models.entities.Credit;
import com.caudron.amusementpark.models.entities.Image;
import com.caudron.amusementpark.models.entities.Manufacturer;
import com.caudron.amusementpark.models.entities.MaterialType;
import com.caudron.amusementpark.models.entities.Park;
import com.caudron.amusementpark.models.entities.SeatingType;
import com.caudron.amusementpark.models.entities.Status;
import com.caudron.amusementpark.utils.UtilsMapping;

import java.util.List;


public class DatabaseViewModel extends AndroidViewModel {

    public DatabaseViewModel(Application application) {
        super(application);
    }


    public void insertCoasters(CoastersResponseDto coastersResponseDto, Context context) {
        List<Coaster> coasters = UtilsMapping.mapList(coastersResponseDto.getCoasterDtos(), Coaster.class);
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        CoasterDao coasterDao = db.coasterDao();
        for(Coaster coaster : coasters){

            MaterialType materialType = coaster.getMaterialType();
            if (materialType != null){
                coaster.setMaterialTypeId(materialType.getName());
            }
            SeatingType seatingType = coaster.getSeatingType();
            if (seatingType != null){
                coaster.setSeatingTypeId(seatingType.getName());
            }
            Manufacturer manufacturer = coaster.getManufacturer();
            if (manufacturer != null){
                coaster.setManufacturerId(manufacturer.getName());
            }
            Park park = coaster.getPark();
            if (park != null){
                coaster.setParkId(park.getId());
            }
            Status status = coaster.getStatus();
            if (status != null){
                coaster.setStatusId(status.getName());
            }
            Image image = coaster.getMainImage();
            if (image != null){
                coaster.setImageId(image.getPath());
            }

            Coaster existingCoaster = coasterDao.getById(coaster.getId());
            if (existingCoaster == null) {
                coasterDao.insert(coaster);
            } else {
                coasterDao.update(coaster);
            }

            if (materialType != null){
                MaterialType existingMaterialType = db.materialTypeDao().getByName(materialType.getName());

            }
        }
    }

    public void insertImages(ImagesResponseDto imagesResponseDto, Context context) {
        List<Image> images = UtilsMapping.mapList(imagesResponseDto.getImageDtos(), Image.class);
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        ImageDao imageDao = db.imageDao();
        for(Image image : images){
            Image existingImage = imageDao.getByPath(image.getPath());
            if (existingImage == null) {
                imageDao.insert(image);
            } else {
                imageDao.update(image);
            }
        }
    }

    public void insertParks(ParksResponseDto parksResponseDto, Context context) {
        List<Park> parks = UtilsMapping.mapList(parksResponseDto.getParks(), Park.class);
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        ParkDao parkDao = db.parkDao();

        for(Park park : parks){
            Country country = park.getCountry();
            if (country != null){
                park.setCountryId(country.getId());
            }

            Park existingPark = parkDao.getById(park.getId());
            if (existingPark == null){
                parkDao.insert(park);
            } else {
                parkDao.update(park);
            }

            if (country != null){
                Country existingCountry = db.countryDao().getCountryById(country.getId());
                if (existingCountry == null) {
                    db.countryDao().insert(country);
                } else {
                    db.countryDao().update(country);
                }
            }
        }
    }

    public void insertStatuses(StatusesResponseDto statusesResponseDto, Context context) {
        List<Status> statuses = UtilsMapping.mapList(statusesResponseDto.getStatuses(), Status.class);
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        StatusDao statusDao = db.statusDao();
        for(Status status : statuses){
            Status existingStatus = statusDao.getByName(status.getName());
            if (existingStatus == null){
                statusDao.insert(status);
            }else {
                statusDao.update(status);
            }
        }
    }

    public int getCountCoasters(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        CoasterDao coasterDao = db.coasterDao();
        return coasterDao.getCount();
    }

    public int getCountParks(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        ParkDao parkDao = db.parkDao();
        return parkDao.getCount();
    }

    public int getCountImages(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        ImageDao imageDao = db.imageDao();
        return imageDao.getCount();
    }

    public int getCountCountries(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        CountryDao countryDao = db.countryDao();
        return  countryDao.getCount();
    }
}