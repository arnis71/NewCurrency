package ru.arnis.newcurrency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.arnis.newcurrency.Retrofit.Rate;
import ru.arnis.newcurrency.Retrofit.Results;
import ru.arnis.newcurrency.Retrofit.AskForData;
import ru.arnis.newcurrency.swipe.SwipeToDismissTouchListener;
import ru.arnis.newcurrency.swipe.adapter.ListViewAdapter;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public DragListView list;
   // MyAdapter adapter;
    AskForData askForData;
    Rates rates;
    SharedPreferences storage;
    Context context;
   // private FloatingActionButton fab;
    private SwipeRefreshLayout refresh;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        askForData = new AskForData();
        rates = new Rates(askForData);
        getPrefs();
        list = (DragListView) findViewById(R.id.list);
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresher);


        //adapter = new MyAdapter(this, rates);

        list.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(rates.getData(),R.layout.tab_layout,R.id.currency_icon,false);
        list.setAdapter(itemAdapter,true);
        list.setCanDragHorizontally(false);
        list.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
                refresh.setEnabled(false);
            }

            @Override
            public void onItemDragging(int itemPosition, float x, float y) {

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                refresh.setEnabled(true);
            }
        });

        //fab = (FloatingActionButton)findViewById(R.id.fab);


        //list.setAdapter(adapter);


        refresh.setOnRefreshListener(this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                pullData();
            }
        });
        rates.setOnRateUpdatedListener(new OnRateUpdatedListener() {
            @Override
            public void onUpdate() {
                itemAdapter.setItemList(rates.getData());
                itemAdapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rates.addToCall("EURUSD");
//                pullData();
//            }
//        });
//
//        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
//                new SwipeToDismissTouchListener<>(
//                        new ListViewAdapter(list),
//                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
//                            @Override
//                            public boolean canDismiss(int position) {
//                                return true;
//                            }
//
//                            @Override
//                            public void onPendingDismiss(ListViewAdapter recyclerView, int position) {
//
//                            }
//
//                            @Override
//                            public void onDismiss(ListViewAdapter view, int position) {
//                                //adapter.remove(position);
//                                rates.remove(position);
//                               // askForData.remove(rates.getData().get(position-1).id);
//                            }
//                        });
//        touchListener.setDismissDelay(1000);
//        list.setOnTouchListener(touchListener);
       // list.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (touchListener.existPendingDismisses())
//                    touchListener.undoPendingDismiss();
//
//            }
//        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("happy", "onDestroy: ");
        storage = getSharedPreferences("Rates", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        editor.clear();
        for (Rate r : rates.getData())
            editor.putLong(r.id+"_"+r.Time+"_"+r.Date,Double.doubleToRawLongBits(r.Rate));
        editor.apply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu,menu);
        SearchView search = (SearchView)menu.findItem(R.id.search).getActionView();
        //search.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings)
            System.exit(0);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        Log.d("happy", "onRefresh: ");
        pullData();
    }


    private void pullData(){
        refresh.setRefreshing(true);
        askForData.getCall().enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                rates.updateRates(response.body().rate);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(context, "Data pull error", Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });
    }
    private void getPrefs() {
        storage = getSharedPreferences("Rates", Context.MODE_PRIVATE);
        Map<String, ?> temp = storage.getAll();
        for (Map.Entry<String,?> entry: temp.entrySet()){
            Rate r = Rate.assignValuesFromString(entry.getKey());
            r.Rate=Double.longBitsToDouble((Long)entry.getValue());
            rates.addToData(r);
        }
    }

}

