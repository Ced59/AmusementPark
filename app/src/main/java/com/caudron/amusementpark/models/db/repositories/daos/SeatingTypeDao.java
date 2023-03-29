package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.SeatingType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface SeatingTypeDao {
    @Insert
    void insert(SeatingType seatingType);

    @Update
    void update(SeatingType seatingType);

    @Delete
    void delete(SeatingType seatingType);

    @Query("SELECT * FROM seatingtypes WHERE name = :name")
    Single<SeatingType> getByName(String name);

    @Query("SELECT * FROM seatingtypes")
    Flowable<List<SeatingType>> getAll();

    @Query("SELECT COUNT(*) FROM seatingtypes")
    int getCount();
}

