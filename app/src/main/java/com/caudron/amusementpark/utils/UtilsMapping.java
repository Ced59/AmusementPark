package com.caudron.amusementpark.utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UtilsMapping {
    public static <T,U> U mapObject(T sourceObject, Class<U> destinationClass){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sourceObject, destinationClass);
    }

    public static <T, U> List<U> mapList(List<T> sourceList, Class<U> destinationClass){
        List<U> destinationList = new ArrayList<>();

        for(T sourceObject : sourceList){
            U destinationObject = mapObject(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }
}
