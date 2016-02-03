package com.thomaskioko.retrofitdemo.services;


import com.thomaskioko.retrofitdemo.data.Movie;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * This class contains API functions
 *
 * @author Kioko
 * @version Version
 */


public interface MovieServices {

    /**
     * Get movie callback.
     *
     * @param movieDayScheduleCallback MovieDaySchedule object
     */
    @GET("/movies.json")
    void getMovies(Callback<List<Movie>> movieDayScheduleCallback);

}
