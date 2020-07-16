package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    ImageButton mImageButton;
    Button goToWeatherBtn;
    public  void  gotoChat(View v){
        Log.i("go", "gotochat");
        Intent chatActivity = new Intent(v.getContext(),ChatRoomActivity.class);
        startActivity(chatActivity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button gotoChatBtn = findViewById(R.id.gotoChat);

        Intent fromMain = getIntent();
        String emailTyped = fromMain.getStringExtra("EMAIL");

        //Put the string that was sent from FirstActivity into the edit text:
        EditText emailEditText = (EditText)findViewById(R.id.enterEmail);
        emailEditText.setText(emailTyped);

        mImageButton = (ImageButton) findViewById(R.id.image);
        mImageButton.setOnClickListener(c -> {
            dispatchTakePictureIntent();
//
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }

        });


        goToWeatherBtn = (Button)findViewById(R.id.GoToWeatherPage);
        goToWeatherBtn.setOnClickListener(c -> {
            Intent goToMenuPage = new Intent(ProfileActivity.this, WeatherForecast.class);

            startActivityForResult(goToMenuPage, 234);

        });



        Log.e(ACTIVITY_NAME, "In function: onCreate()");

        gotoChatBtn.setOnClickListener(this::gotoChat);



    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME,  "In function: onActivityResult()");


    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: onStart()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: onDestroy()");
    }


}