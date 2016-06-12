package ru.arnis.newcurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.arnis.newcurrency.Retrofit.Results;
import ru.arnis.newcurrency.Retrofit.AskForData;

public class MainActivity extends AppCompatActivity {

    //Map<String, Double> rates = new HashMap<>();
    private Button button1;
    private ListView list;
    Results exec;
    //Rates rates;
    //AskForData retro;
    MyAdapter adapter;
    AskForData data;
    Rates rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        list = (ListView) findViewById(R.id.list);

        data = new AskForData("USDRUB");
        rates = new Rates(data);

//        adapter = new MyAdapter(this, null);
//        list.setAdapter(adapter);




        data.getCall().enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
             //   rates.updateRates(response.body());
//                rates.put(response.body().rate.get(0).id, response.body().rate.get(0).Rate);
//                rates.put(response.body().rate.get(1).id, response.body().rate.get(1).Rate);
//
              // Log.d("happy", "onResponse: " + rates.get("USDRUB"));
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

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
                adapter.notifyDataSetChanged();
            }
        });

    }



    }

