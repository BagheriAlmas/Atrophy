package ir.seeapp.atrophy.atrophy;

/**
 * Created by Masoud on 4/4/2016.
 */

import java.util.List;

//import android.widget.Adapter.FoodAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import Adapter.FoodAdapter;
import database.DBAdapter;
import database.Food;
//import database.DBAdapter;
//import database.Food;
import ir.seeapp.atrophy.R;
public class Search extends ListActivity{

    DBAdapter dba;
    List<Food> foods;
    ListView lst;
    EditText txtSearch;
    RadioButton radioFoodName,radioFoodType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lst = getListView();
        txtSearch = (EditText)findViewById(R.id.search_txtSearch);
        radioFoodName = (RadioButton)findViewById(R.id.search_radioFoodName);
        radioFoodType = (RadioButton)findViewById(R.id.search_radioFoodType);
        dba = new DBAdapter(getBaseContext());
        dba.open();
//		dba.findContacts(nam, row)

        txtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if(radioFoodName.isChecked())
                {
                    foods = dba.findContacts(txtSearch.getText().toString(), DBAdapter.KEY_TITLE);
                }else
                {
                    foods = dba.findContacts(txtSearch.getText().toString(), DBAdapter.KEY_TYPE);
                }
                refreshDisplay();
            }
        });

        radioFoodName.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                txtSearch.setText("");
                txtSearch.requestFocus();
            }
        });


        radioFoodType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                txtSearch.setText("");
                txtSearch.requestFocus();
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

    public void refreshDisplay(){
        Log.i(DBAdapter.TAG, foods.size() + "== tedad dastan ha");
        ArrayAdapter<Food> adapter = new FoodAdapter(this, foods);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(radioFoodName.isChecked())
        {
            foods = dba.findContacts(txtSearch.getText().toString(), DBAdapter.KEY_TITLE);
        }else
        {
            foods = dba.findContacts(txtSearch.getText().toString(), DBAdapter.KEY_TYPE);
        }
        refreshDisplay();
    }
}
