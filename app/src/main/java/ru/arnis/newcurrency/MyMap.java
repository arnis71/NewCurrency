package ru.arnis.newcurrency;

import java.util.ArrayList;

/**
 * Created by arnis on 13.06.2016.
 */
public class MyMap {
    ArrayList<String> currency;
    ArrayList<Double> rate;

    public MyMap() {
        this.currency=new ArrayList<>();
        this.rate = new ArrayList<>();
    }

    public Double get(String key){
        return rate.get(currency.indexOf(key));
    }
}
