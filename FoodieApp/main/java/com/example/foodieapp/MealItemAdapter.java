package com.example.foodieapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.MealItemHolder>{

    public static final String TAG = "MealItemAdapter";

    private ArrayList<MealItem> mealsData;
    private Context context;

    protected static final String TITLE = "Title";
    protected static final String INFO = "Description";
    protected static final String INGREDIENTS = "Ingredients";
    protected static final String LINK = "Link";
    protected static final String IMAGE = "Image";
    protected static final String IMAGEMAP = "Bitmap";


    public MealItemAdapter(Context context, ArrayList<MealItem> mealsData){
        this.context = context;
        this.mealsData = mealsData;
    }

    @NonNull
    @Override
    public MealItemAdapter.MealItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealItemHolder((LayoutInflater.from(context).inflate(R.layout.list_item,parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull MealItemAdapter.MealItemHolder holder, int position) {
        Log.d(TAG, "start of onBindViewHolder in MealItemAdapter");
        MealItem currentMeal = mealsData.get(position);
        holder.bindItem(currentMeal);
        Log.d(TAG, "end of onBindViewHolder in MealItemAdapter");

    }

    @Override
    public int getItemCount() {
        return mealsData.size();
    }

    protected class MealItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public static final String TAG = "MealItemHolder";

        private TextView textTitle, textDescription;
        private ImageView imageViewMeal;


        public MealItemHolder(View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.meal_title);
            imageViewMeal = itemView.findViewById(R.id.meal_imageView);
            textDescription = itemView.findViewById(R.id.meal_description);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindItem(MealItem currentMeal){
            Log.d(TAG, "start of onBindViewHolder in MealItemHolder");
            textTitle.setText(currentMeal.getTitle());
            textDescription.setText(currentMeal.getDescription());

            /*
            if(currentMeal.getImageBitmap() != null){
                Log.d("Inside bindItem", "Bitmap");
                Drawable drawable = new BitmapDrawable(context.getResources(), currentMeal.getImageBitmap());
                imageViewMeal.setImageDrawable(drawable);
            }else { */
                Log.d("Inside bindItem", "Int");
                Glide.with(context).load(currentMeal.getImageId()).into(imageViewMeal);
                Glide.with(context)
                        .load(currentMeal.getImageId())
                        .placeholder(R.drawable.img_anago)
                        .error(R.drawable.img_anago)
                        .into(imageViewMeal);
           // }
            Log.d(TAG, "end of onBindViewHolder in MealItemHolder");

        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "start of onClick in MealItemHolder");

            MealItem currentMeal = mealsData.get(getAdapterPosition());
            Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);

            intent.putExtra(TITLE, currentMeal.getTitle());
            intent.putExtra(INFO, currentMeal.getDescription());
            intent.putExtra(INGREDIENTS, currentMeal.getIngredients());
            intent.putExtra(LINK, currentMeal.getLink());
            intent.putExtra(IMAGE, currentMeal.getImageId());
            context.startActivity(intent);
            Log.d(TAG, "end of onClick in MealItemHolder");

        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "start of onLongClick in MealItemHolder");

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Meal")
                    .setMessage("Are you sure you want to delete this meal")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mealsData.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create();
            AlertDialog alertDialog = builder.show();
            Log.d(TAG, "end of onLongClick in MealItemHolder");
            return false;
        }
    }
}
