package com.example.lab10.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
public class ApiClient {
    private static Retrofit retrofit = null;
    // Updated BASE_URL to the API root, not the Swagger UI page
    private static final String BASE_URL = "http://76.13.212.30:6868/";
    public static Retrofit getClient() {
        if (retrofit == null) {
            return getClient(BASE_URL);
        }
        return retrofit;
    }
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            // Create logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // Create OkHttp client
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            httpClient.writeTimeout(30, TimeUnit.SECONDS);
            // Create Gson instance
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            // Build Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    public static MovieApiService getApiService() {
        return getClient().create(MovieApiService.class);
    }
    public static void resetClient() {
        retrofit = null;
    }
    public static void setBaseUrl(String newBaseUrl) {
        resetClient();
        retrofit = getClient(newBaseUrl);
    }
}
