package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Credit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CreditDao {
    @Insert
    void insert(Credit credit);

    @Update
    void update(Credit credit);

    @Delete
    void delete(Credit credit);

    @Query("SELECT * FROM credits WHERE coaster = :coasterName")
    Single<List<Credit>> getByCoasterName(String coasterName);

    @Query("SELECT * FROM credits")
    Flowable<List<Credit>> getAll();

    @Query("SELECT COUNT(*) FROM credits")
    int getCount();
}

