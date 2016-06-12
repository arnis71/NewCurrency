package ru.arnis.newcurrency;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ru.arnis.newcurrency.Retrofit.AskForData;
import ru.arnis.newcurrency.Retrofit.Results;

/**
 * Created by arnis on 12.06.2016.
 */
public class Rates {
    Map<String, Double> rates;
    AskForData data;

    public Rates(AskForData data) {
        this.rates = new HashMap<>();
        this.data=data;
        rates.put("default",0.0);
    }

    public Double get(String key){
       return rates.get(key);
    }

    public void updateRates(Results body){
        //call api here and pull info from server
        rates.put(body.rate.get(0).id, body.rate.get(0).Rate);
        rates.put(body.rate.get(1).id, body.rate.get(1).Rate);

        Log.d("happy", "updateRates: " + body.rate.get(0).Rate);

    }
}
