package com.caudron.amusementpark.models.apiclients;

import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {
    @GET("coasters")
    Call<CoastersResponseDto> getCoasters(@Header("X-AUTH-TOKEN") String authToken, @Query("page") int page);

    // Exemple de méthode pour récupérer la liste des images
    @GET("images")
    Call<ImagesResponseDto> getImages(@Header("X-AUTH-TOKEN") String authToken, @Query("page") int page);

    // Exemple de méthode pour récupérer la liste des parcs d'attractions
    @GET("parks")
    Call<ParksResponseDto> getParks(@Header("X-AUTH-TOKEN") String authToken, @Query("page") int page);

    // Exemple de méthode pour récupérer la liste des statuts
    @GET("statuses")
    Call<StatusesResponseDto> getStatuses(@Header("X-AUTH-TOKEN") String authToken, @Query("page") int page);
}
