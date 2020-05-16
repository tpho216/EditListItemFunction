package com.codificador.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditListItemActivity extends AppCompatActivity {
    String mTAG = "EditListItemActivity";
    EditText editTextName, editTextValue;
    EditText editSeekbar_max, editSeekbar_min;
    Button buttonOK;
    Button buttonCancel;
    ListItem listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_item);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() == null || getIntent().getSerializableExtra("listItem") == null){
            finish();
        }
        listItem = (ListItem) getIntent().getSerializableExtra("listItem");
        initViews();
    }

    private void initViews(){
        editTextName = findViewById(R.id.editTextName);
        editTextValue = findViewById(R.id.editTextNumber);
        editSeekbar_max = findViewById(R.id.editSeekbar_max);
        editSeekbar_min = findViewById(R.id.editSeekbar_min);

        editTextName.setText(listItem.getName());
        editTextValue.setText(listItem.getValue());

        buttonOK= findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                int value = Integer.parseInt(editTextValue.getText().toString());

                Log.d(mTAG, editSeekbar_max.getText().toString());
                if (!editSeekbar_max.getText().toString().equals("") && !editSeekbar_min.getText().toString().equals(""))
                {
                    int new_max = Integer.parseInt(editSeekbar_max.getText().toString());
                    int new_min = Integer.parseInt(editSeekbar_min.getText().toString());

                    //update seekbar max & min value
                    listItem.setSeekbar_max(new_max);
                    listItem.setSeekbar_min(new_min);
                }

                //don't update listItem id, because just we are updating the name & number

                listItem.setName(name);
                listItem.setValue(value);
                Intent intent = new Intent();
                intent.putExtra("listItem", listItem);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("listItem", listItem);
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}