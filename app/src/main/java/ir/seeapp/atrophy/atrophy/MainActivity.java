package ir.seeapp.atrophy.atrophy;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


import ir.seeapp.atrophy.R;
import Adapter.FoodAdapter;
import database.DBAdapter;
import database.Food;

public class MainActivity extends ListActivity {
    DBAdapter dba;
    LinearLayout btnLikes,btnAll,btnSetting,btnAbout;
    ImageView btnSearch;
    List<Food> foods;
    ListView lst;
    Boolean isAll;
    Typeface fontFarnaz,fontHoma;
    TextView txtHeader;
    EditText txtSeach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst = getListView();
        dba = new DBAdapter(getBaseContext());
        dba.open();
        foods = dba.getAllContacts();
        isAll=true;
        fontFarnaz = Typeface.createFromAsset(getAssets(), "farnaz.ttf");
        fontHoma = Typeface.createFromAsset(getAssets(), "homa.ttf");
        btnSearch = (ImageView)findViewById(R.id.main_btnSearch);
        btnLikes = (LinearLayout)findViewById(R.id.main_btnLikes);
        btnAll = (LinearLayout)findViewById(R.id.main_btnAll);
        btnSetting = (LinearLayout)findViewById(R.id.main_btnSetting);
        btnAbout = (LinearLayout)findViewById(R.id.main_btnAbout);
        txtHeader = (TextView)findViewById(R.id.main_txtHeader);
        txtSeach = (EditText)findViewById(R.id.main_txtSearch);
        txtHeader.setTypeface(fontFarnaz);
        txtHeader.setText(" " + txtHeader.getText());
        txtSeach.setTypeface(fontHoma);

        btnSetting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getBaseContext(),Setting.class);
                startActivity(i);
            }
        });
        btnLikes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                isAll=false;
                foods = dba.findFAVContacts();
                refreshDisplay();
            }
        });

        btnAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                isAll=true;
                foods = dba.getAllContacts();
                refreshDisplay();
            }
        });
        if(foods.size()==0){

            String destPath = "/data/data/" + getPackageName() + "/databases";
            try {
                CopyDB(getBaseContext().getAssets().open("mydb"), new FileOutputStream(destPath + "/foods"));
                Log.i(DBAdapter.TAG, "DB Copy Shod");
                foods = dba.getAllContacts();
                refreshDisplay();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
        {
            refreshDisplay();
        }


        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent (getBaseContext(),Search.class);
                startActivity(i);
            }
        });


        btnAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),About.class);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        Food food = foods.get(position);
        Intent i = new Intent(getBaseContext(),ShowFood.class);
        i.putExtra("SelectedFood", food);
        startActivity(i);
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        // ---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }


    public void refreshDisplay(){
        Log.i(DBAdapter.TAG, foods.size() + "== tedad dastan ha");
        ArrayAdapter<Food> adapter = new FoodAdapter(this, foods);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        foods = dba.getAllContacts();
        if(isAll==true){
            foods = dba.getAllContacts();
        }else
        {
            foods = dba.findFAVContacts();
        }
        refreshDisplay();
    }
}
