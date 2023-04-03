package com.caudron.amusementpark.models.db.repositories.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caudron.amusementpark.models.entities.Status;

import java.util.List;

@Dao
public interface StatusDao {
    @Insert
    void insert(Status status);

    @Update
    void update(Status status);

    @Delete
    void delete(Status status);

    @Query("SELECT * FROM statuses")
    List<Status> getAll();

    @Query("SELECT * FROM statuses WHERE name = :name")
    Status getByName(String name);

    @Query("SELECT COUNT(*) FROM statuses")
    LiveData<Integer> getCount();
}
