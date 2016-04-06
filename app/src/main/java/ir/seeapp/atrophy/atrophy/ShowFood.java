package ir.seeapp.atrophy.atrophy;

/**
 * Created by Masoud on 4/4/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import Adapter.CircleImageView;
import Adapter.ResizableImageView;
import ir.seeapp.atrophy.R;
import database.DBAdapter;
import database.Food;

//import database.DBAdapter;
//import database.Food;

public class ShowFood extends ActionBarActivity {

    Context c;
    Food food;
    DBAdapter dba;
    TextView txtTitle,txtType,txtDescription,txtHeader;
    ImageView btnShare,btnLike;
    CircleImageView img;
    LinearLayout btnBack;

    SharedPreferences sh;
    int size;
    Boolean light;
    String font;
    Typeface fontFace;
    Typeface fontHoma,fontFarnaz;
    ResizableImageView imgBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();
        setContentView(R.layout.activity_show_food);

        c = getBaseContext();
        dba = new DBAdapter(c);


        sh = getSharedPreferences("setting", 0);
        size = sh.getInt("size?", 20);
        light = sh.getBoolean("light?", true);
        font = sh.getString("font?", "yekan");
        Log.i(DBAdapter.TAG, "size = " + size + "Light = " + light + "font = " + font);

        fontFace = Typeface.createFromAsset(getAssets(), font + ".ttf");
        fontHoma = Typeface.createFromAsset(getAssets(), "homa.ttf");
        fontFarnaz = Typeface.createFromAsset(getAssets(), "farnaz.ttf");



        dba.open();

        food = (Food) getIntent().getExtras().get("SelectedFood");

        food.setRead(1);

        Log.i(DBAdapter.TAG, "ghaza gerefte shod " + food.getTitle());

        btnBack = (LinearLayout)findViewById(R.id.show_btnBack);
        txtTitle = (TextView)findViewById(R.id.show_txtTitle);
        txtType = (TextView)findViewById(R.id.show_txtType);
        txtDescription = (TextView)findViewById(R.id.show_txtDescription);
        btnShare = (ImageView)findViewById(R.id.show_btnShare);
        btnLike = (ImageView)findViewById(R.id.show_btnLike);
        img = (CircleImageView)findViewById(R.id.show_img);
        imgBody = (ResizableImageView)findViewById(R.id.show_imgBody);
        txtHeader = (TextView)findViewById(R.id.show_food_txtHeader);

        txtTitle.setText(food.getTitle());
        txtTitle.setTypeface(fontHoma);
        txtType.setText(food.getType());
        txtDescription.setText(food.getDescription());
        txtDescription.setMovementMethod(new ScrollingMovementMethod());
        txtDescription.setTextSize(size);
        txtDescription.setTypeface(fontFace);
        txtHeader.setTypeface(fontFarnaz);
        txtHeader.setText(" " + txtHeader.getText());
        if(light==true)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        int imageResource = c.getResources().getIdentifier(food.getPicture(), "drawable", c.getPackageName());
        img.setImageResource(imageResource);

        int imageResourceBody = c.getResources().getIdentifier(food.getBodyPicture(), "drawable", c.getPackageName());
        imgBody.setImageResource(imageResourceBody);

        if(food.getLike()==1)
            btnLike.setImageResource(R.drawable.btn_like);

        btnShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent ShareIntent = new Intent(Intent.ACTION_SEND);
                ShareIntent.setType("text/plain");
                ShareIntent.putExtra(Intent.EXTRA_TEXT, food.getTitle() + "\n" + food.getDescription() + "\n" + "------------------------" + "\n" + "نرم افزار لاغری");
                ShareIntent.putExtra(Intent.EXTRA_SUBJECT, food.getTitle());
                startActivity(Intent.createChooser(ShareIntent, "اشتراک..."));

            }
        });
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        btnLike.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(food.getLike()==0)
                {
                    food.setLike(1);
                    btnLike.setImageResource(R.drawable.btn_like);
                } else if (food.getLike()==1)
                {
                    food.setLike(0);
                    btnLike.setImageResource(R.drawable.btn_like2);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        dba.updateContact(food);
        dba.close();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        dba.open();
    }



}
