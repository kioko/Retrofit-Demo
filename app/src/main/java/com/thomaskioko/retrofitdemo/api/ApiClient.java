package com.thomaskioko.retrofitdemo.api;

import com.squareup.okhttp.OkHttpClient;
import com.thomaskioko.retrofitdemo.services.MovieServices;
import com.thomaskioko.retrofitdemo.utils.ApplicationConstants;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * @author kioko
 * @version Version 1.0
 */


public class ApiClient {

    private static MovieServices movieServices;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private RestAdapter restAdapter;
    private boolean isDebug;

    /**
     * This method adds parameter to the RestAdapter. e.g Timeout Duration
     *
     * @return okHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        okHttpClient.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(ApplicationConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(ApplicationConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    public static MovieServices getApiClient() {
        if (movieServices == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ApplicationConstants.END_POINT)
                    .setClient(new OkClient(getOkHttpClient()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            movieServices = restAdapter.create(MovieServices.class);
        }

        return movieServices;
    }

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


    public MovieServices movieServices() {
        return getRestAdapter().create(MovieServices.class);
    }

}
