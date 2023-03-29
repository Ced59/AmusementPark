package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Country;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CountryDao {
    @Insert
    void insert(Country country);

    @Update
    void update(Country country);

    @Delete
    void delete(Country country);

    @Query("SELECT * FROM countries WHERE id = :countryId")
    Flowable<Country> getCountryById(int countryId);

    @Query("SELECT * FROM countries WHERE isRateable = 1")
    Flowable<List<Country>> getRateableCountries();

    @Query("SELECT COUNT(*) FROM countries")
    int getCount();
}
