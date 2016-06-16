package ru.arnis.newcurrency.Retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arnis on 03/05/16.
 */
public class AskForData {

    final String baseURL = "https://query.yahooapis.com/v1/public/";
    final String line1 = "yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22";
    public String CURRENCY="EURUSD";//no semicolon!!!!!
    final String line2 = "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    public String requestURL;

    Call<Results> call;

    public void addCurrency(String str){
        if (!this.CURRENCY.contains(str)) {
            this.CURRENCY = this.CURRENCY + "," + str;
        }
    }


    public Call<Results> getCall() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Results.class , new MyDeserializer())
                .create();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);

        requestURL = line1 + CURRENCY + line2;

        call = api.getList(requestURL);

        return call;
    }

    public void remove(String id) {
        if (CURRENCY.contains(id)){
            CURRENCY.replaceAll(id,"");
        }
    }
}

