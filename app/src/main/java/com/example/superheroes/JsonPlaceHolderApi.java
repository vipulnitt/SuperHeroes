package com.example.superheroes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("all.json")
    Call<List<Data>> getData();
}
