package Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import database.Food;
import ir.seeapp.atrophy.R;
/**
 * Created by Mahdi on 3/5/2016.
 */

public class FoodAdapter extends ArrayAdapter<Food> {

    List<Food> foods;
    Context c;

    public FoodAdapter(Context c, List<Food> foods) {
        super(c, android.R.id.content, foods);
//        super(c, R.id.content, foods);
        this.c = c;
        this.foods = foods;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item, null);
        Food food = foods.get(position);
        TextView txtTitle = (TextView) view.findViewById(R.id.item_txtTitle);
        TextView txtType = (TextView) view.findViewById(R.id.item_txtType);
        ImageView imgLike = (ImageView) view.findViewById(R.id.item_imgLike);
        ImageView imgRead = (ImageView) view.findViewById(R.id.item_imgRead);
        CircleImageView img = (CircleImageView) view.findViewById(R.id.item_img);
        Typeface fontFace = Typeface.createFromAsset(c.getAssets(), "homa.ttf");

        txtTitle.setText( food.getTitle());
        txtTitle.setTypeface(fontFace);
        txtType.setText(food.getType());

        int imageResource2 = c.getResources().getIdentifier(food.getPicture(), "drawable", c.getPackageName());
        img.setImageResource(imageResource2);


        if (food.getLike() == 0) {
            int imageResource = c.getResources().getIdentifier("btn_like2", "drawable", c.getPackageName());
            imgLike.setImageResource(imageResource);
        } else {
            int imageResource = c.getResources().getIdentifier("btn_like", "drawable", c.getPackageName());
            imgLike.setImageResource(imageResource);
        }

        if (food.getRead() == 0) {
            int imageResource = c.getResources().getIdentifier("btn_see2", "drawable", c.getPackageName());
            imgRead.setImageResource(imageResource);
        } else {
            int imageResource = c.getResources().getIdentifier("btn_see", "drawable", c.getPackageName());
            imgRead.setImageResource(imageResource);
        }


        return view;

    }
}
