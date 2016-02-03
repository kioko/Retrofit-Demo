package com.thomaskioko.retrofitdemo.api;

import com.thomaskioko.retrofitdemo.services.MovieServices;
import com.thomaskioko.retrofitdemo.utils.ApplicationConstants;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * @author kioko
 * @version Version 1.0
 */


public class ApiClient {

    private RestAdapter restAdapter;
    private boolean isDebug;

    /**
     * Set the {@link RestAdapter} log level.
     *
     * @param isDebug If true, the log level is set to
     *                {@link RestAdapter.LogLevel#FULL}. Otherwise
     *                {@link RestAdapter.LogLevel#NONE}.
     */
    public ApiClient setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
        if (restAdapter != null) {
            restAdapter.setLogLevel(isDebug ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
        }
        return this;
    }

    /**
     * Create a new {@link RestAdapter.Builder}. Override this to e.g. set your own client or executor.
     *
     * @return A {@link RestAdapter.Builder} with no modifications.
     */
    protected RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }

    /**
     * Return the current {@link RestAdapter} instance. If none exists (first call, API key changed),
     * builds a new one.
     * <p/>
     * When building, sets the endpoint, a custom converter ({@link ApiHelper#getGsonBuilder()})
     * and a {@link RequestInterceptor} which adds the API key as query param.
     */
    protected RestAdapter getRestAdapter() {

        RestAdapter.Builder builder = null;
        if (restAdapter == null) {
            //Create a new instance of the Rest Adapter class
            builder = newRestAdapterBuilder();

            builder.setEndpoint(ApplicationConstants.END_POINT);
            builder.setClient(new OkClient(ApiHelper.getOkHttpClient()));
            builder.setConverter(new GsonConverter(ApiHelper.getGsonBuilder().create()));
        }

        if (isDebug) {
            if (builder != null) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }
        }
        if (builder != null) {
            restAdapter = builder.build();
        }
        return restAdapter;
    }

    /**
     * Create Movie service instance.
     *
     * @return Movie Service.
     */
    public MovieServices movieServices() {
        return getRestAdapter().create(MovieServices.class);
    }

}
