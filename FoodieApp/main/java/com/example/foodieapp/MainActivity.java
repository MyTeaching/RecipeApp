package com.example.foodieapp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ArrayList<MealItem> mealsData;
    private MealItemAdapter mealItemAdapter;
    private FoodieReceiver foodieReceiver;
    private int gridColumnCount;

    protected static final int ADDITEM_ACTIVITY = 1;
    protected static final String ACTION_I_AM_HOME = "com.example.I_AM_HOME";

    //ItemTouchHelper itemTouchHelper;
    //private int dragDirections = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "start of onCreate in MainActivity");
        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

         /*
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirections, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mealsData, from, to);
                mealItemAdapter.notifyItemMoved(from, to);
                return false;
            }


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

          */

        foodieReceiver = new FoodieReceiver();
        registerReceiver(foodieReceiver, addIntentFilters());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        mealsData = new ArrayList<>();
        mealItemAdapter = new MealItemAdapter(this, mealsData);
        recyclerView.setAdapter(mealItemAdapter);

        loadMealsData();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivityForResult(intent, ADDITEM_ACTIVITY);
            }
        });
        Log.d(TAG, "end of onCreate in MainActivity");
    }

    private IntentFilter addIntentFilters() {
        Log.d(TAG, "start of addIntentFilters in MainActivity");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_I_AM_HOME);
        Log.d(TAG, "end of addIntentFilters in MainActivity");
        return intentFilter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "start of onActivityResult in MainActivity");
        if (requestCode == ADDITEM_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                    String mealName = data.getStringExtra(AddItemActivity.NEWNAME);
                    String mealInfo = data.getStringExtra(AddItemActivity.NEWINFO);
                    String mealIngredients = data.getStringExtra(AddItemActivity.NEWINGREDIENTS);
                    String mealLink = data.getStringExtra(AddItemActivity.NEWLINK);
                    Bitmap mealImage = data.getParcelableExtra(AddItemActivity.NEWIMAGE);
                    MealItem newMeal = new MealItem(mealName, mealInfo, mealIngredients, mealLink);
                    mealsData.add(newMeal);
                    mealItemAdapter.notifyItemInserted(mealsData.size()-1);
            }
        }
        Log.d(TAG, "end of onActivityResult in MainActivity");
    }

    private void loadMealsData() {
        mealsData.clear();

        Log.d(TAG, "start of loadMealsData in MainActivity");
        String [] mealTitles = getResources().getStringArray(R.array.meal_titles);
        String [] mealDescriptions = getResources().getStringArray(R.array.meal_descriptions);
        TypedArray mealsImages = getResources().obtainTypedArray(R.array.meal_images);
        String [] mealIngredients = getResources().getStringArray(R.array.meal_ingredients);
        String [] mealLinks = getResources().getStringArray(R.array.meal_links);

        for(int i = 0; i < mealTitles.length; i++){
            mealsData.add(new MealItem(mealTitles[i], mealDescriptions[i], mealsImages.getResourceId(i, 0), mealIngredients[i], mealLinks[i]));
        }

        mealItemAdapter.notifyDataSetChanged();
        mealsImages.recycle();
        Log.d(TAG, "end of loadMealsData in MainActivity");

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "start of onDestroy in MainActivity");
        super.onDestroy();
        this.unregisterReceiver(foodieReceiver);
        Log.d(TAG, "start of onDestroy in MainActivity");

    }
}
