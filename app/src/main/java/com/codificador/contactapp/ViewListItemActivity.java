package com.codificador.contactapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewListItemActivity extends AppCompatActivity {

    TextView textViewName, textViewPhoneNumber;
    Button buttonCancel;

    String strName, strNumber;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_item);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewName = findViewById(R.id.textViewName);
        textViewPhoneNumber = findViewById(R.id.textViewNumber);
        buttonCancel = findViewById(R.id.buttonCancel);

        if(getIntent() == null){
            finish();
        }else{
            strName = getIntent().getStringExtra("name");
            strNumber = getIntent().getStringExtra("number");
            id = getIntent().getLongExtra("id",-1);
            textViewName.setText(strName);
            textViewPhoneNumber.setText(strNumber);
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(ViewListItemActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ViewListItemActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}