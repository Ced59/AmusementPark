package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Image;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert
    void insert(Image image);

    @Update
    void update(Image image);

    @Delete
    void delete(Image image);

    @Query("SELECT * FROM images")
    List<Image> getAll();

    @Query("SELECT * FROM images WHERE path = :path")
    Image getByPath(String path);

    @Query("SELECT COUNT(*) FROM images")
    int getCount();
}

