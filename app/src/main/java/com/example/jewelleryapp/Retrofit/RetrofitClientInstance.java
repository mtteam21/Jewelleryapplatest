package com.example.jewelleryapp.Retrofit;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

        private static Retrofit retrofit;

        public static Retrofit getRetrofitInstance(String baseUrl) {
            if (retrofit == null) {
                retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

}
