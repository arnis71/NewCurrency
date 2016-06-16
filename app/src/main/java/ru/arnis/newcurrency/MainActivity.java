package ru.arnis.newcurrency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.arnis.newcurrency.Retrofit.Rate;
import ru.arnis.newcurrency.Retrofit.Results;
import ru.arnis.newcurrency.Retrofit.AskForData;
import ru.arnis.newcurrency.swipe.SwipeToDismissTouchListener;
import ru.arnis.newcurrency.swipe.adapter.ListViewAdapter;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Button button1;
    private ListView list;
    MyAdapter adapter;
    AskForData data;
    Rates rates;
    SharedPreferences storage;
    Context context;
    private FloatingActionButton fab;
    private SwipeRefreshLayout refresh;
    SwipeToDismissTouchListener<ListViewAdapter> touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rates = new Rates();
        storage = getSharedPreferences("Rates", Context.MODE_PRIVATE);
        Map<String, ?> temp = storage.getAll();
        for (Map.Entry<String,?> entry: temp.entrySet()){
            Rate r = Rate.assignValuesFromString(entry.getKey());
            r.Rate=Double.longBitsToDouble((Long)entry.getValue());
            rates.getData().add(r);
        }

        data = new AskForData();
        for (Rate r:rates.getData())
            data.addCurrency(r.id);



        refresh = (SwipeRefreshLayout)findViewById(R.id.refresher);
        list = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton)findViewById(R.id.fab);




        if (getIntent().getExtras()!=null) {
            data.addCurrency(getIntent().getExtras().getString("currency"));
            pullData();
        }


        adapter = new MyAdapter(this, rates);
        list.setAdapter(adapter);


        context=this;
        pullData();

        refresh.setOnRefreshListener(this);

        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                pullData();
            }
        });

        rates.setOnRateUpdatedListener(new OnRateUpdatedListener() {
            @Override
            public void onUpdate() {
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response<Results> a = data.getCall().execute();
//                    int i=10+10;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AddMenu.class));
            }
        });

        touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(list),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(ListViewAdapter recyclerView, int position) {
                                Log.d("happy", "onPendingDismiss: ");
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                adapter.remove(position);
                                //delete from queue
                               // data.remove(rates.getData().get(position).id);
                            }
                        });
        touchListener.setDismissDelay(1000);
        list.setOnTouchListener(touchListener);
        list.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storage = getSharedPreferences("Rates", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        editor.clear();
        for (Rate r : rates.getData())
            editor.putLong(r.id+"_"+r.Time+"_"+r.Date,Double.doubleToRawLongBits(r.Rate));

        editor.apply();

    }


    private void pullData(){
        data.getCall().enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                rates.updateRates(response.body().rate);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(context, "Data pull error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        Log.d("happy", "onRefresh: ");
        pullData();
    }
}

