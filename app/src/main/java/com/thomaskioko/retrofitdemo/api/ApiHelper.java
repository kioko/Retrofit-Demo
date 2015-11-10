package com.thomaskioko.retrofitdemo.api;

import android.annotation.SuppressLint;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.OkHttpClient;
import com.thomaskioko.retrofitdemo.utils.ApplicationConstants;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Kioko
 * @version Version
 */


public class ApiHelper {

    /**
     * Format for decoding JSON dates in string format.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat JSON_STRING_DATE = new SimpleDateFormat("yyy-MM-dd");

    /**
     * Time zone for Trakt dates.
     */
    private static final TimeZone TRAKT_TIME_ZONE = TimeZone.getTimeZone("GMT-8:00");

    private static final long SECOND_IN_MILLISECONDS = 1000;

    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * This method adds parameter to the RestAdapter. e.g Timeout Duration
     *
     * @return okHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        okHttpClient.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(ApplicationConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    /**
     * Create a {@link GsonBuilder} and register all of the custom types needed in
     * order to properly deserialize complex TMDb-specific types.
     *
     * @return Assembled GSON builder instance.
     */
    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // class types
        builder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
            @Override
            public Integer deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {
                try {
                    return json.getAsInt();
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {

                try {
                    return JSON_STRING_DATE.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });

        builder.registerTypeAdapter(Calendar.class, new JsonDeserializer<Calendar>() {
            @Override
            public Calendar deserialize(JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {
                Calendar value = Calendar.getInstance(TRAKT_TIME_ZONE);
                value.setTimeInMillis(json.getAsLong() * SECOND_IN_MILLISECONDS);
                return value;
            }
        });
        return builder;
    }
}
