package ru.arnis.newcurrency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arnis on 04/05/16.
 */
public class CustomAdapter extends BaseAdapter {

    private final ArrayList mData;

    public CustomAdapter(Map<String, Double> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Map.Entry<String, Double> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customView = inflater.inflate(R.layout.tab_layout,parent,false);

        Map.Entry<String,Double> item = getItem(position);

        TextView bigText = (TextView) customView.findViewById(R.id.textView);

        bigText.setText(item.getKey());
        return customView;
    }
}
