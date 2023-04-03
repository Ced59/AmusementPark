package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Country;

@Dao
public interface CountryDao {
    @Insert
    void insert(Country country);

    @Update
    void update(Country country);

    @Delete
    void delete(Country country);

    @Query("SELECT * FROM countries WHERE name = :countryName")
    Country getCountryByName(String countryName);


    @Query("SELECT COUNT(*) FROM countries")
    LiveData<Integer> getCount();
}
