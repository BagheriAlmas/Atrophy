package ir.seeapp.atrophy.atrophy;

/**
 * Created by Masoud on 4/4/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ir.seeapp.atrophy.R;

public class Splash extends ActionBarActivity {

    ImageView img;
    TextView txt1, txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img = (ImageView) findViewById(R.id.splash_img);
        txt1 = (TextView) findViewById(R.id.splash_txt1);
        txt2 = (TextView) findViewById(R.id.splash_txt2);


        Animation in = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_in);
        in.setDuration(500);
        img.startAnimation(in);

        in.setStartOffset(2000);
        txt1.startAnimation(in);

//		in.setStartOffset(500);
//		txt2.setAnimation(in);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        }, 3000);

    }

}
