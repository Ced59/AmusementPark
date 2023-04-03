package com.caudron.amusementpark.viewmodels.database_view_model;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.caudron.amusementpark.models.db.repositories.daos.CoasterDao;
import com.caudron.amusementpark.models.db.repositories.daos.CountryDao;
import com.caudron.amusementpark.models.db.repositories.daos.ImageDao;
import com.caudron.amusementpark.models.db.repositories.daos.MaterialTypeDao;
import com.caudron.amusementpark.models.db.repositories.daos.ParkDao;
import com.caudron.amusementpark.models.db.repositories.daos.StatusDao;
import com.caudron.amusementpark.models.db.repositories.implementations.AmusementParkDatabase;
import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;
import com.caudron.amusementpark.models.entities.Coaster;
import com.caudron.amusementpark.models.entities.Country;
import com.caudron.amusementpark.models.entities.Image;
import com.caudron.amusementpark.models.entities.Manufacturer;
import com.caudron.amusementpark.models.entities.MaterialType;
import com.caudron.amusementpark.models.entities.Park;
import com.caudron.amusementpark.models.entities.SeatingType;
import com.caudron.amusementpark.models.entities.Status;
import com.caudron.amusementpark.utils.UtilsCountries;
import com.caudron.amusementpark.utils.UtilsMapping;
import com.caudron.amusementpark.utils.UtilsStrings;

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
                if(UtilsStrings.extractIdFromUri(park.getIdUri()) != null){
                    coaster.setParkId(UtilsStrings.extractIdFromUri(park.getIdUri()));
                }
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
                try{
                    coasterDao.insert(coaster);
                } catch (SQLiteConstraintException e){
                    coasterDao.update(coaster);
                }
            } else {
                coasterDao.update(coaster);
            }

            if (materialType != null){
                try {
                    db.materialTypeDao().insert(materialType);
                } catch (SQLiteConstraintException e) {
                    MaterialType existingMaterialType = db.materialTypeDao().getByName(materialType.getName());
                    if (existingMaterialType != null){
                        db.materialTypeDao().update(materialType);
                    }
                }
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
                try{
                    imageDao.insert(image);
                } catch (SQLiteConstraintException e){
                    imageDao.update(image);
                }
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
                park.setCountryId(country.getName());
            }
            Park existingPark = parkDao.getById(park.getId());
            if (existingPark == null){
                parkDao.insert(park);
            } else {
                parkDao.update(park);
            }

            if (country != null){
                try {
                    String nameCountryApi = country.getName();
                    String nameCountryFormatted = UtilsStrings.extractCountryName(nameCountryApi);
                    country.setName(nameCountryFormatted);
                    UtilsCountries.fillCountryCode(country);
                    db.countryDao().insert(country);
                } catch (SQLiteConstraintException e) {
                    Country existingCountry = db.countryDao().getCountryByName(country.getName());
                    if (existingCountry != null){
                        UtilsCountries.fillCountryCode(country);
                        db.countryDao().update(country);
                    }
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
                String nameStatusApi = status.getName();
                String nameStatusFormatted = UtilsStrings.extractStatus(nameStatusApi);
                status.setName(nameStatusFormatted);

                try{
                    statusDao.insert(status);
                } catch (SQLiteConstraintException e){
                    statusDao.update(status);
                }

            }else {
                statusDao.update(status);
            }
        }
    }

    public LiveData<Integer> getCountCoasters(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        CoasterDao coasterDao = db.coasterDao();
        return coasterDao.getCount();
    }

    public LiveData<Integer> getCountParks(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        ParkDao parkDao = db.parkDao();
        return parkDao.getCount();
    }

    public LiveData<Integer> getCountImages(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        ImageDao imageDao = db.imageDao();
        return imageDao.getCount();
    }

    public LiveData<Integer> getCountCountries(Context context){
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        CountryDao countryDao = db.countryDao();
        return  countryDao.getCount();
    }

    public LiveData<Integer> getCountMaterialType(Context context) {
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        MaterialTypeDao materialTypeDao = db.materialTypeDao();
        return materialTypeDao.getCount();
    }

    public LiveData<Integer> getCountStatuses(Context context) {
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        StatusDao statusDao = db.statusDao();
        return statusDao.getCount();
    }

    public LiveData<List<Country>> getCountries(Context context) {
        AmusementParkDatabase db = AmusementParkDatabase.getInstance(context);
        CountryDao countryDao = db.countryDao();
        return countryDao.getAll();
    }
}