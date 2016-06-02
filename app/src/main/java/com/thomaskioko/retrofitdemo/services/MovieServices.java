package com.thomaskioko.retrofitdemo.services;


import com.thomaskioko.retrofitdemo.data.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * This class contains API functions
 *
 * @author Kioko
 * @version Version
 */


public interface MovieServices {

    /**
     * Get movie callback.
     */
    @GET("movies.json")
    Call<List<Movie>> getMovies();

}
