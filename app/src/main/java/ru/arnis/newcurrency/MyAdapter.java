package ru.arnis.newcurrency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.zip.Inflater;

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
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
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

        if (rates.get("USDRUB")!=null)
            rate.setText(Double.toString(rates.get("USDRUB")));
        else rate.setText(Double.toString(rates.get("default")));

        icon.setImageResource(R.mipmap.dollar);

        return convertView;
    }
}
