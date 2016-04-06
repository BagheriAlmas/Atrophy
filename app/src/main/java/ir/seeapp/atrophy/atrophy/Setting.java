package ir.seeapp.atrophy.atrophy;

/**
 * Created by Masoud on 4/4/2016.
 */

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import database.DBAdapter;
import ir.seeapp.atrophy.R;
import database.DBAdapter;

public class Setting extends ActionBarActivity {

    ImageView imgLight,btnFontUp,btnFontDown;
    Spinner spinFonts;
    SharedPreferences sh;
    int size;
    Boolean light;
    String font;
    TextView txtTest;
    Typeface fontFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        imgLight = (ImageView)findViewById(R.id.setting_imgLight);
        btnFontDown = (ImageView)findViewById(R.id.setting_btnFontDown);
        btnFontUp = (ImageView)findViewById(R.id.setting_btnFontUp);
        spinFonts = (Spinner)findViewById(R.id.setting_spinFont);
        txtTest = (TextView)findViewById(R.id.setting_txtTest);


        spinFonts.setOnItemSelectedListener(new cOnItemSelectedListener());

        sh = getSharedPreferences("setting", 0);
        size = sh.getInt("size?", 20);
        light = sh.getBoolean("light?", true);
        font = sh.getString("font?", "yekan");
        Log.i(DBAdapter.TAG, "size = " + size + "Light = " + light + "font = " + font);

        fontFace = Typeface.createFromAsset(getAssets(), font + ".ttf");
//		String xxxxx = Typeface.createFromAsset(getAssets(), font + ".ttf").toString();
        if(font.equals("nazanin"))
        {
            spinFonts.setSelection(2);
        }else if (font.equals("yaghout")){
            spinFonts.setSelection(1);
        }else if (font.equals("yekan")){
            spinFonts.setSelection(0);
        }else if (font.equals("homa")){
            spinFonts.setSelection(3);
        }

        txtTest.setTextSize(size);
        txtTest.setTypeface(fontFace);

        if(light)
        {
            imgLight.setImageResource(android.R.drawable.alert_light_frame);
        }else
        {

            imgLight.setImageResource(android.R.drawable.alert_dark_frame);

        }



        btnFontDown.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                size--;
                txtTest.setTextSize(size);
            }
        });

        btnFontUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                size++;
                txtTest.setTextSize(size);
            }
        });



        imgLight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(light)
                {
                    light=false;
                    imgLight.setImageResource(android.R.drawable.alert_dark_frame);
                    Toast.makeText(getBaseContext(), "Light off After Seconds", Toast.LENGTH_SHORT).show();
                }else
                {
                    light=true;
                    imgLight.setImageResource(android.R.drawable.alert_light_frame);
                    Toast.makeText(getBaseContext(), "Light Never Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class cOnItemSelectedListener implements OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View arg1, int post,
                                   long id) {
            // TODO Auto-generated method stub
            font = parent.getItemAtPosition(post).toString();
            fontFace = Typeface.createFromAsset(getAssets(), font + ".ttf");
            txtTest.setTypeface(fontFace);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        SharedPreferences.Editor ed = sh.edit();
        ed.putBoolean("light?", light);
        ed.putInt("size?", size);
        ed.putString("font?", font);
        ed.commit();
    }
}

