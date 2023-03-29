package com.caudron.amusementpark.viewmodels.database_view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.entities.Coaster;
import com.caudron.amusementpark.utils.UtilsMapping;

import java.util.List;


public class DatabaseViewModel extends AndroidViewModel {

    public DatabaseViewModel(Application application) {
        super(application);
    }


    public void insertCoasters(CoastersResponseDto coastersResponseDto) {
        List<Coaster> coasters = UtilsMapping.mapList(coastersResponseDto.getCoasterDtos(), Coaster.class);

    }
}