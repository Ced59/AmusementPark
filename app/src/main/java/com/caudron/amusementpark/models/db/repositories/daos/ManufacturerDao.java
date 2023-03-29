package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Manufacturer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ManufacturerDao {
    @Insert
    void insert(Manufacturer manufacturer);

    @Update
    void update(Manufacturer manufacturer);

    @Delete
    void delete(Manufacturer manufacturer);

    @Query("SELECT * FROM manufacturers WHERE name = :name")
    Single<Manufacturer> getByName(String name);

    @Query("SELECT * FROM manufacturers")
    Flowable<List<Manufacturer>> getAll();

    @Query("SELECT COUNT(*) FROM manufacturers")
    int getCount();
}

