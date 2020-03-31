package com.example.classapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Database.DBHandler;

public class MainActivity extends AppCompatActivity {
    Button addBtn,singBtn,UpdtBtn,dltBtn,slctBtn;
    EditText username;
    EditText password;
    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database= new DBHandler(this);
        username = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        addBtn=findViewById(R.id.add);
        singBtn=findViewById(R.id.singin);
        UpdtBtn=findViewById(R.id.update);
        dltBtn=findViewById(R.id.delete);
        slctBtn=findViewById(R.id.button);

    }

    public void onResume() {
        super.onResume();
        
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.addInfo(username.getText().toString(), password.getText().toString());
            }
        });


        singBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor newRowId = database.readAllInfo();
                if(newRowId!= null) {
                    while (newRowId.moveToNext()) {
                        Log.d("User record", "User" + newRowId.getString(2) + newRowId.getString(2));
                    }

                }else
                    showMessage();
            }

        });


    }

    private void showMessage() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Error");
        builder.setMessage("No user available");
        builder.show();
    }





}
