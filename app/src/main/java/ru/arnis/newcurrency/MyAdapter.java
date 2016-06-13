package ru.arnis.newcurrency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arnis on 12.06.2016.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    Rates rates;

    public MyAdapter(Context context, Rates rates) {
        this.context=context;
        this.rates=rates;
    }

    @Override
    public int getCount() {
        return rates.getMap().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView= LayoutInflater.from(context).inflate(R.layout.tab_layout,parent,false);

        TextView rate = (TextView) convertView.findViewById(R.id.currency_rate);
        ImageView icon = (ImageView) convertView.findViewById(R.id.currency_icon);

       // if (rates.getByKey("USDRUB")!=null)
            rate.setText(Double.toString(rates.getByKey("USDRUB")));
            //rate.setText(Double.toString(rates.getByPos(position)));
        //else rate.setText(Double.toString(rates.getByKey("default")));

        icon.setImageResource(R.mipmap.dollar);

        return convertView;
    }
}
