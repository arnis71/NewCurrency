package ru.arnis.newcurrency.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arnis on 03/05/16.
 */
public class Main {

    final static String baseURL = "https://query.yahooapis.com/v1/public/";
    final static String line1 = "yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22EURUSD,";
    public static String CURRENCY = "USDRUB"; // temporary
    final static String line2 = "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    public static String requestURL = line1 + CURRENCY + line2;


    public static API pullAPI() {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Results.class , new MyDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);

        return api;



    }
}

