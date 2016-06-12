package ru.arnis.newcurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.arnis.newcurrency.Retrofit.Main;
import ru.arnis.newcurrency.Retrofit.Results;

public class MainActivity extends AppCompatActivity {

    Map<String, Double> Rates = new HashMap<>();


    private ListView lv;
    ArrayList<String> listItems = new ArrayList<>();
    private Button button1;
    private TextView text;
    private int count = 0;
    private ListView list;
    List<Double> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // listItems.addAll(Rates.entrySet());

        doThis();

        button1 = (Button) findViewById(R.id.button1);
        text = (TextView) findViewById(R.id.text);
        list = (ListView) findViewById(R.id.list);

        Map<String,Double> ex = new HashMap<>();
        ex.put("EURUSD",1.2);
        ex.put("USDRUB",66.3);
      //  while (Rates.size()==0)
            test = new ArrayList<>(ex.values());


        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,test);
        list.setAdapter(adapter);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(Rates.get("USDRUB").toString());
            }
        });

    }


    public void doThis() {
        Call<Results> call = Main.pullAPI().getList(Main.requestURL);

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Rates.put(response.body().rate.get(0).id, response.body().rate.get(0).Rate);
                Rates.put(response.body().rate.get(1).id, response.body().rate.get(1).Rate);

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });

    }
}
