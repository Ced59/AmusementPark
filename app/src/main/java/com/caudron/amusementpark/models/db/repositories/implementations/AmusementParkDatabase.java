package com.caudron.amusementpark.models.db.repositories.implementations;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.caudron.amusementpark.models.db.repositories.daos.CoasterDao;
import com.caudron.amusementpark.models.db.repositories.daos.CountryDao;
import com.caudron.amusementpark.models.db.repositories.daos.CreditDao;
import com.caudron.amusementpark.models.db.repositories.daos.ImageDao;
import com.caudron.amusementpark.models.db.repositories.daos.ManufacturerDao;
import com.caudron.amusementpark.models.db.repositories.daos.MaterialTypeDao;
import com.caudron.amusementpark.models.db.repositories.daos.ParkDao;
import com.caudron.amusementpark.models.db.repositories.daos.SeatingTypeDao;
import com.caudron.amusementpark.models.db.repositories.daos.StatusDao;
import com.caudron.amusementpark.models.entities.Coaster;
import com.caudron.amusementpark.models.entities.Country;
import com.caudron.amusementpark.models.entities.Credit;
import com.caudron.amusementpark.models.entities.Image;
import com.caudron.amusementpark.models.entities.Manufacturer;
import com.caudron.amusementpark.models.entities.MaterialType;
import com.caudron.amusementpark.models.entities.Park;
import com.caudron.amusementpark.models.entities.SeatingType;
import com.caudron.amusementpark.models.entities.Status;

@Database(entities = {Coaster.class, Country.class, Credit.class, Image.class, Manufacturer.class, MaterialType.class, Park.class, SeatingType.class, Status.class}, version = 2, exportSchema = false)
public abstract class AmusementParkDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "amusement_park_db";
    private static AmusementParkDatabase sInstance;

    public static synchronized AmusementParkDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AmusementParkDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }

    public abstract CoasterDao coasterDao();
    public abstract CountryDao countryDao();
    public abstract CreditDao creditDao();
    public abstract ImageDao imageDao();
    public abstract ManufacturerDao manufacturerDao();
    public abstract MaterialTypeDao materialTypeDao();
    public abstract ParkDao parkDao();
    public abstract SeatingTypeDao seatingTypeDao();
    public abstract StatusDao statusDao();
}

