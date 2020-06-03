package com.example.androidlabs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        Button mbutton = findViewById(R.id.myButton);
        mbutton.setOnClickListener(v -> Toast.makeText(MainActivity.this
                , getResources().getString(R.string.toast_message)
                , Toast.LENGTH_LONG).show());


        CheckBox mycheckBox = findViewById(R.id.checkbox);
        mycheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            Snackbar.make(mycheckBox, getResources().getString(R.string.snackbar_checkbox), Snackbar.LENGTH_LONG )
                    .setAction(getResources().getString(R.string.undo), v->mycheckBox.setChecked(false))
                    .show();
        }));

        Switch mySwitch = findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            Snackbar.make(mySwitch, getResources().getString(R.string.snackbar_switch), Snackbar.LENGTH_INDEFINITE )
                    .setAction(getResources().getString(R.string.undo), v->mySwitch.setChecked(false))
                    .show();
        }));
    }
}