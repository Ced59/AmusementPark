package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Park;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ParkDao {
    @Insert
    void insert(Park park);

    @Update
    void update(Park park);

    @Delete
    void delete(Park park);

    @Query("SELECT * FROM parks WHERE id = :id")
    Park getById(int id);

    @Query("SELECT * FROM parks WHERE countryCode = :countryId")
    Flowable<List<Park>> getByCountryCode(int countryId);

    @Query("SELECT * FROM parks")
    LiveData<List<Park>> getAll();

    @Query("SELECT COUNT(*) FROM parks")
    LiveData<Integer> getCount();
}
