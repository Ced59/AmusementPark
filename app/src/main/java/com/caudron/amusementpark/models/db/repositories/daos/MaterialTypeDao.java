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

    //TODO Remettre Single
    @Query("SELECT * FROM materialtypes WHERE name = :name")
    MaterialType getByName(String name);

    //TODO Remettre Flowable
    @Query("SELECT * FROM materialtypes")
    List<MaterialType> getAll();

    @Query("SELECT COUNT(*) FROM materialtypes")
    int getCount();



    //TODO Valable pour toutes les classes DAO! (pfff ca fait du boulot pour subscribe ect...)
}

