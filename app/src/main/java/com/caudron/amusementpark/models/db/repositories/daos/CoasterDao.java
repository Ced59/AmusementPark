package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Coaster;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CoasterDao {
    @Insert
    void insert(Coaster coaster);

    @Update
    void update(Coaster coaster);

    @Delete
    void delete(Coaster coaster);

    @Query("SELECT * FROM coasters")
    List<Coaster> getAll();

    @Query("SELECT * FROM coasters WHERE id = :id")
    Coaster getById(int id);

    @Query("SELECT * FROM coasters WHERE name = :name")
    Coaster getByName(String name);

    @Query("SELECT * FROM coasters WHERE parkId = :parkId")
    List<Coaster> getByParkId(int parkId);

    @Query("SELECT * FROM coasters WHERE materialTypeId = :materialTypeId")
    List<Coaster> getByMaterialTypeId(int materialTypeId);

    @Query("SELECT * FROM coasters WHERE seatingTypeId = :seatingTypeId")
    List<Coaster> getBySeatingTypeId(int seatingTypeId);

    @Query("SELECT * FROM coasters WHERE manufacturerId = :manufacturerName")
    List<Coaster> getByManufacturerName(String manufacturerName);

    @Query("SELECT * FROM coasters WHERE statusId = :statusName")
    List<Coaster> getByStatusName(String statusName);

    @Query("SELECT * FROM coasters WHERE inversionsNumber = :inversionsNumber")
    List<Coaster> getByInversionsNumber(int inversionsNumber);

    @Query("SELECT * FROM coasters WHERE height > :height")
    List<Coaster> getByHeightGreaterThan(int height);

    @Query("SELECT * FROM coasters WHERE length > :length")
    List<Coaster> getByLengthGreaterThan(int length);

    @Query("SELECT * FROM coasters WHERE speed > :speed")
    List<Coaster> getBySpeedGreaterThan(int speed);

    @Query("SELECT COUNT(*) FROM coasters")
    LiveData<Integer> getCount();

}
