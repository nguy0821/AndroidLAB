package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText emailField;
    SharedPreferences mPrefs;
    Button loginBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        emailField = findViewById(R.id.typeEmail);
        mPrefs = getSharedPreferences("filename", Context.MODE_PRIVATE);
        String savedString = mPrefs.getString("ReserveName", "Default value");

        emailField.setHint(savedString);

        loginBtn = findViewById(R.id.myButton);
        loginBtn.setOnClickListener( c -> {

            Intent goToProfile  = new Intent(this, ProfileActivity.class);
            EditText et = findViewById(R.id.typeEmail);
            goToProfile.putExtra("EMAIL", et.getText().toString());
            startActivityForResult( goToProfile , 456);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mPrefs.edit();
        emailField = findViewById(R.id.typeEmail);
        String saveEmail = emailField.getText().toString();
        editor.putString("ReserveName", saveEmail);
        editor.commit();

    }
}
