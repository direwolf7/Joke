package com.example.joke.coding;

import com.example.joke.model.Joke;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface JokeApi {

    String BASE_URL = "https://sv443.net/jokeapi/v2/joke/";

    @GET("any")
    Call<Joke> getJoke();

    @GET("Programming")
    Call<Joke> getProgrammingJoke(@QueryMap Map<String, String> parameters);

    @GET("Miscellaneous")
    Call<Joke> getMiscellaneousJoke(@QueryMap Map<String, String> parameters);

    @GET("Dark")
    Call<Joke> getDarkJoke(@QueryMap Map<String, String> parameters);

    @GET("Programming,Miscellaneous")
    Call<Joke> getProgrammingAndMiscellaneousJoke(@QueryMap Map<String, String> parameters);

    @GET("Programming,Dark")
    Call<Joke> getProgrammingAndDarkJoke(@QueryMap Map<String, String> parameters);

    @GET("Miscellaneous,Dark")
    Call<Joke> getMiscellaneousAndDarkJoke(@QueryMap Map<String, String> parameters);
}
