package ru.arnis.newcurrency;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arnis on 12.06.2016.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    Rates rates;
    GestureDetector gd;

    public MyAdapter(Context context, Rates rates) {
        this.context=context;
        this.rates=rates;
    }

    private void assignBitmap(ImageView iv,int pos){
        switch (pos){
            case 0:iv.setImageResource(R.mipmap.dollar);break;
            case 1:iv.setImageResource(R.mipmap.ruble);
        }
    }

    @Override
    public int getCount() {
        return rates.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        rates.getData().remove(position);
    }

    private static class ViewHolder {
        public final TextView rate;
        public final TextView time;
        public final TextView name;
        public final ImageView icon;

        public ViewHolder(TextView rate, TextView time, TextView name, ImageView icon) {
            this.rate = rate;
            this.time = time;
            this.name = name;
            this.icon = icon;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView rate;
        TextView time;
        TextView name;
        ImageView icon;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tab_layout, parent, false);
            rate = (TextView) convertView.findViewById(R.id.currency_rate);
            time = (TextView) convertView.findViewById(R.id.time);
            name = (TextView) convertView.findViewById(R.id.name);
            icon = (ImageView) convertView.findViewById(R.id.currency_icon);
            convertView.setTag(new ViewHolder(rate, time, name, icon));
        }
        else{
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            rate=viewHolder.rate;
            time=viewHolder.time;
            name=viewHolder.name;
            icon=viewHolder.icon;

        }


        name.setText(rates.getData().get(position).id);
        rate.setText(Double.toString(rates.getData().get(position).Rate));
        String str=rates.getData().get(position).Time+" "+rates.getData().get(position).Date;
        time.setText(str);

    assignBitmap(icon,position);

        return convertView;
    }
}
