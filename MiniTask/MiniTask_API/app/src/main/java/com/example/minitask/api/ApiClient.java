package com.example.minitask.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    private static Retrofit retrofit = null;
    // Updated BASE_URL to the API root, not the Swagger UI page

    private static final String BASE_URL = "";      // kết nối link API//////////////


    public static Retrofit getClient() {
        if (retrofit == null) {
            return getClient(BASE_URL);
        }
        return retrofit;
    }

///////////////////////////////////////////////////////

// Tạo Retrofit Client

///////////////////////////////////////////////////////
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
