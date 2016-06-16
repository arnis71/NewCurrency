package ru.arnis.newcurrency;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arnis on 15.06.2016.
 */
public class AddMenu extends AppCompatActivity {
    private ListView listView;
    int sch=0;
    private TextView from;
    private TextView to;
    private Button add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);

//        getActionBar().hide();


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout((int)(dm.widthPixels*0.8),(int)(dm.heightPixels*0.8));

        from = (TextView)findViewById(R.id.from);
        to = (TextView) findViewById(R.id.to);
        add = (Button)findViewById(R.id.button_add);
        listView = (ListView)findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sch<3) {
                    sch++;
                    listView.setChoiceMode(position);
                    switch (sch) {
                        case 1:
                            from.setText(parent.getAdapter().getItem(position).toString());
                            break;
                        case 2:
                            to.setText(parent.getAdapter().getItem(position).toString());
                            break;
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = from.getText().toString()+to.getText().toString();
                startActivity(new Intent(v.getContext(),MainActivity.class).putExtra("currency",str));
            }
        });

        String[] codes = getResources().getStringArray(R.array.currency_code);
        String[] contries = getResources().getStringArray(R.array.currency_country);
        String[] names = getResources().getStringArray(R.array.currency_name);
        Bitmap[] flags = new Bitmap[1];

        MenuAdapter  menuAdapter = new MenuAdapter(this,codes,contries,names,flags);
        listView.setAdapter(menuAdapter);





    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
