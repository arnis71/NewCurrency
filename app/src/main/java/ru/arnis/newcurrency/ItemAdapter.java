/**
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.arnis.newcurrency;

import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import ru.arnis.newcurrency.Retrofit.Rate;

public class ItemAdapter extends DragItemAdapter<Rate, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;

    public ItemAdapter(ArrayList<Rate> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String text = Double.toString(mItemList.get(position).Rate);
        holder.rate.setText(text);
        holder.name.setText(mItemList.get(position).id);
        String str=mItemList.get(position).Time+" "+mItemList.get(position).Date;
        holder.time.setText(str);
        holder.icon.setImageResource(R.mipmap.dollar);
        holder.itemView.setTag(text);
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).id.hashCode();
    }

    public class ViewHolder extends DragItemAdapter<Rate, ViewHolder>.ViewHolder {
        public TextView rate;
        public TextView time;
        public TextView name;
        public ImageView icon;

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId);
            rate = (TextView) itemView.findViewById(R.id.currency_rate);
            time = (TextView) itemView.findViewById(R.id.time);
            name = (TextView) itemView.findViewById(R.id.name);
            icon = (ImageView) itemView.findViewById(R.id.currency_icon);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
