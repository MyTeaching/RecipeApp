package com.example.foodieapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.util.MarkEnforcingInputStream;

import java.util.ArrayList;

public class FoodieReceiver extends BroadcastReceiver {

    public static final String TAG = "FoodieReceiver";

    public Context context;

    private ArrayList<MealItem> mealsData;
    private MealItemAdapter mealItemAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "start of onReceive in FoodieReceiver");
        this.context = context;
        String intentAction = intent.getAction();

        switch (intentAction){
            case MainActivity.ACTION_I_AM_HOME:
                mealsData = new ArrayList<>();
                loadMealsData();
                startRandomDetail();
                Toast.makeText(context, "Happy Cooking ", Toast.LENGTH_LONG);
                break;
        }
        Log.d(TAG, "end of onReceive in FoodieReceiver");

    }

    private void startRandomDetail() {
        Log.d(TAG, "start of startRandomDetail in FoodieReceiver");

        Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
        MealItem randMealItem = mealsData.get((int) (Math.random()*mealsData.size()));

        intent.putExtra(MealItemAdapter.TITLE, randMealItem.getTitle());
        intent.putExtra(MealItemAdapter.INFO, randMealItem.getDescription());
        intent.putExtra(MealItemAdapter.INGREDIENTS, randMealItem.getIngredients());
        intent.putExtra(MealItemAdapter.LINK, randMealItem.getLink());
        intent.putExtra(MealItemAdapter.IMAGE, randMealItem.getImageId());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.getApplicationContext().startActivity(intent);
        Log.d(TAG, "end of startRandomDetail in FoodieReceiver");

    }

    private void loadMealsData() {
        Log.d(TAG, "start of loadMealsData in FoodieReceiver");

        String [] mealTitles = context.getResources().getStringArray(R.array.meal_titles);
        String [] mealDescriptions = context.getResources().getStringArray(R.array.meal_descriptions);
        TypedArray mealsImages = context.getResources().obtainTypedArray(R.array.meal_images);
        String [] mealIngredients = context.getResources().getStringArray(R.array.meal_ingredients);
        String [] mealLinks = context.getResources().getStringArray(R.array.meal_links);

        for(int i = 0; i < mealTitles.length; i++){
            this.mealsData.add(new MealItem(mealTitles[i], mealDescriptions[i], mealsImages.getResourceId(i, 0), mealIngredients[i], mealLinks[i]));
        }

        mealsImages.recycle();
        Log.d(TAG, "end of loadMealsData in FoodieReceiver");

    }
}
