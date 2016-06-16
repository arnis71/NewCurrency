package ru.arnis.newcurrency;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arnis on 15.06.2016.
 */
public class MenuAdapter extends BaseAdapter {
    String[] codes;
    String[] countries;
    String[] names;
    Bitmap[] flags;
    Context context;

    public MenuAdapter(Context context, String[] codes, String[] contries, String[] names, Bitmap[] flags) {
        this.codes=codes;
        this.countries=contries;
        this.names=names;
        this.flags=flags;
        this.context=context;
    }

    @Override
    public int getCount() {
        return codes.length;
    }

    @Override
    public Object getItem(int position) {
        return codes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        public final TextView code;
        public final TextView name;
        public final TextView country;
        public final ImageView icon;

        public ViewHolder(TextView code, TextView country, TextView name, ImageView icon) {
            this.code = code;
            this.country = country;
            this.name = name;
            this.icon = icon;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView code;
        TextView country;
        TextView name;
        ImageView icon;

        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.menu_tab,parent,false);
            code = (TextView) convertView.findViewById(R.id.code);
            country = (TextView)convertView.findViewById(R.id.country);
            name = (TextView)convertView.findViewById(R.id.name);
            icon = (ImageView)convertView.findViewById(R.id.flag);
            convertView.setTag(new ViewHolder(code,country,name,icon));
        }else {
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            code=viewHolder.code;
            country=viewHolder.country;
            name=viewHolder.name;
            icon=viewHolder.icon;
        }

        code.setText(codes[position]);
        country.setText(countries[position]);
        name.setText(names[position]);

        return convertView;
    }
}
