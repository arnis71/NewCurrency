package ru.arnis.newcurrency;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.arnis.newcurrency.Retrofit.Results;
import ru.arnis.newcurrency.Retrofit.AskForData;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private ListView list;
    MyAdapter adapter;
    AskForData data;
    Rates rates;
    SharedPreferences storage;
    Context context;
    private ArrayList<String> currencyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //----------------------------leak canary--------------------------
        RefWatcher watcher = LeakCanary.install(getApplication());
       // watcher.watch(this);

        //------------------------------------------------------------------


        button1 = (Button) findViewById(R.id.button1);
        list = (ListView) findViewById(R.id.list);

        data = new AskForData("USDRUB");
        rates = new Rates();

        adapter = new MyAdapter(this, rates);
        list.setAdapter(adapter);


        context=this;
        pullData();


        rates.setOnRateUpdatedListener(new OnRateUpdatedListener() {
            @Override
            public void onUpdate() {
                adapter.notifyDataSetChanged();
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

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullData();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storage = getSharedPreferences("Rates", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        for (Map.Entry<String,Double> entry:rates.getMapEntry()){
            editor.putLong(entry.getKey(),Double.doubleToRawLongBits(entry.getValue()));
        }
        editor.apply();

    }

    @Override
    protected void onResume() {
        super.onResume();
        storage = getSharedPreferences("Rates", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = storage.edit();
//        editor.remove("default");
//        editor.apply();
        Map<String, ?> temp = storage.getAll();
        for (Map.Entry<String,?> entry: temp.entrySet()){
            rates.getMap().put(entry.getKey(),Double.longBitsToDouble((Long)entry.getValue()));

        }
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
}

