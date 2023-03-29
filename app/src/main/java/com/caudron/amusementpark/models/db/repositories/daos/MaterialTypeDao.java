package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.MaterialType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MaterialTypeDao {
    @Insert
    void insert(MaterialType materialType);

    @Update
    void update(MaterialType materialType);

    @Delete
    void delete(MaterialType materialType);

    @Query("SELECT * FROM materialtypes WHERE name = :name")
    Single<MaterialType> getByName(String name);

    @Query("SELECT * FROM materialtypes")
    Flowable<List<MaterialType>> getAll();

    @Query("SELECT COUNT(*) FROM materialtypes")
    int getCount();
}

