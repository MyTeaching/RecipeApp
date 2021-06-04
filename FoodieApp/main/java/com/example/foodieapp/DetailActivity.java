package com.example.foodieapp;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    private ImageView mealImage;
    private TextView mealName, mealDescription, mealIngredients, mealLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "start of onCreate in DetailActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();

        Log.d("DetailActivity", String.valueOf(R.id.tv_meal_name));
        mealName = (TextView) findViewById(R.id.tv_meal_name);
        mealDescription = findViewById(R.id.tv_meal_details);
        mealIngredients = findViewById(R.id.tv_meal_ingredients);
        mealLink = findViewById(R.id.tv_meal_link);
        mealImage = findViewById(R.id.meal_imageViewDetail);

        Log.d("DetailActivity", intent.getStringExtra(MealItemAdapter.TITLE) + intent.getStringExtra(MealItemAdapter.INFO) + intent.getStringExtra(MealItemAdapter.INGREDIENTS) + intent.getStringExtra(MealItemAdapter.LINK) );
        mealName.setText(intent.getStringExtra(MealItemAdapter.TITLE));
        mealDescription.setText(intent.getStringExtra(MealItemAdapter.INFO));
        mealIngredients.setText(intent.getStringExtra(MealItemAdapter.INGREDIENTS));
        mealLink.setText(intent.getStringExtra(MealItemAdapter.LINK));

        Glide.with(getApplicationContext())
                .load(intent.getIntExtra(MealItemAdapter.IMAGE, 0))
                .placeholder(R.drawable.img_anago)
                .error(R.drawable.img_anago)
                .into(mealImage);
        Log.d(TAG, "end of onCreate in DetailActivity");

    }
}