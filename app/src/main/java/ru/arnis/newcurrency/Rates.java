package ru.arnis.newcurrency;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.arnis.newcurrency.Retrofit.AskForData;
import ru.arnis.newcurrency.Retrofit.Rate;

/**
 * Created by arnis on 12.06.2016.
 */
public class Rates {

    public Rates() {
        this.data = new ArrayList<>();
    }


    public ArrayList<Rate> getData() {
        return data;
    }

    private ArrayList<Rate> data;

    OnRateUpdatedListener mOnRateUpdatedListener;
    public void setOnRateUpdatedListener(OnRateUpdatedListener listener){
        mOnRateUpdatedListener=listener;
    }

//    public Map<String, Double> getMap() {
//        return ratesMap;
//    }
//    public Set<Map.Entry<String, Double>> getMapEntry(){
//        return ratesMap.entrySet();
//    }
//
//    public Double getByKey(String key){
//       return ratesMap.get(key);
//    }

    public void updateRates(List<Rate> list){
//        for (Rate r: list){
//            ratesMap.put(r.id,r.Rate);
//            Log.d("happy", "updateRates: "+ r.id+ " "+r.Rate);
//        }
        data= (ArrayList<Rate>) list;
        mOnRateUpdatedListener.onUpdate();

    }

}
