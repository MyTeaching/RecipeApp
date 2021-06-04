package com.example.foodieapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_PERMISSION_CODE = 1888;
    private Bitmap camMealImage;

    private EditText mealName, mealDescription, mealIngredients, mealLink;
    private Button submitButton, mealImageButton;
    private ImageView mealImage;

    protected static final String NEWNAME = "NewName";
    protected static final String NEWINFO = "NewDescription";
    protected static final String NEWINGREDIENTS = "NewIngredients";
    protected static final String NEWLINK = "NewLink";
    protected static final String NEWIMAGE = "NewImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mealName = findViewById(R.id.et_new_meal_name);
        mealDescription = findViewById(R.id.et_new_meal_description);
        mealIngredients = findViewById(R.id.et_new_meal_ingredients);
        mealLink = findViewById(R.id.et_new_meal_link);
        mealImageButton = findViewById(R.id.meal_image_button);
        mealImage = findViewById(R.id.iv_new_meal_image);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(this);
        mealImageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_button:
                if (mealName.getText() != null && mealDescription .getText() != null){
                    Intent intent = new Intent();
                    intent.putExtra(NEWNAME,mealName.getText().toString());
                    intent.putExtra(NEWINFO, mealDescription.getText().toString());
                    intent.putExtra(NEWINGREDIENTS, mealIngredients.getText().toString());
                    intent.putExtra(NEWLINK, mealLink.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    if(camMealImage != null){
                        intent.putExtra(NEWIMAGE, camMealImage);
                    }
                    finish();
                }
                break;
            case R.id.meal_image_button:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null ){
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    }else{
                        startActivityForResult(intent, CAMERA_PERMISSION_CODE);
                    }
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_PERMISSION_CODE && resultCode == Activity.RESULT_OK){
            try {
                camMealImage = (Bitmap) data.getExtras().get("data");
                Drawable drawable = new BitmapDrawable(getResources(), camMealImage);
                mealImage.setImageDrawable(drawable);
            } catch (Exception e){
                Toast.makeText(this, "Error getting Image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}