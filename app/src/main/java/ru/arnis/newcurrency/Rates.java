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
    AskForData askForData;

    public Rates(AskForData askForData) {
        this.data = new ArrayList<>();
        this.askForData=askForData;
    }


    public ArrayList<Rate> getData() {
        return data;
    }

    private ArrayList<Rate> data;

    OnRateUpdatedListener mOnRateUpdatedListener;
    public void setOnRateUpdatedListener(OnRateUpdatedListener listener){
        mOnRateUpdatedListener=listener;
    }

    public void remove(int position){
        String id = data.get(position).id;
        data.remove(position);
        String CURRENCY = askForData.getCURRENCY();
        String case1 = id+",";
        String case2 = ","+id;
        if (CURRENCY.contains(case1)){
            askForData.setCURRENCY(CURRENCY.replaceAll(case1,""));
        }
        if (CURRENCY.contains(case2)){
            askForData.setCURRENCY(CURRENCY.replaceAll(case2,""));
        }

    }
    public void addToCall(String currency){
        if (!askForData.getCURRENCY().contains(currency)) {
            askForData.updateCURRENCY(currency);
        }
    }

    public void addToData(Rate rate){
        data.add(rate);
        addToCall(rate.id);
    }
    public void updateRates(List<Rate> list){
        data= (ArrayList<Rate>) list;
        mOnRateUpdatedListener.onUpdate();

    }

    public void swap(int positionOne, int positionTwo) {
        Rate buf = data.get(positionOne);
        data.set(positionOne,data.get(positionTwo));
        data.set(positionTwo,buf);
        buf=null;
    }
}
