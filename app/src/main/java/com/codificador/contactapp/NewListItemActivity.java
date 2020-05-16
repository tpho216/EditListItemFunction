package com.codificador.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewListItemActivity extends AppCompatActivity {

    EditText editTextName, editTextValue, editSeekbar_max, editSeekbar_min, editSeekbar_progress;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews(){
        editTextName = findViewById(R.id.editTextName);
        editTextValue = findViewById(R.id.editTextNumber);
        buttonAdd = findViewById(R.id.buttonAdd);
        editSeekbar_max = findViewById(R.id.editSeekbar_max);
        editSeekbar_min = findViewById(R.id.editSeekbar_min);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                ListItem listItem = null;
                if (!editTextValue.getText().toString().equals("") && !editSeekbar_max.getText().toString().equals("") && !editSeekbar_min.getText().toString().equals(""))
                {
                    int seekBar_max = Integer.parseInt(editSeekbar_max.getText().toString());
                    int seekBar_min = Integer.parseInt(editSeekbar_min.getText().toString());
                    int value = Integer.parseInt(editTextValue.getText().toString());

                    listItem = new ListItem(name, value, seekBar_max, seekBar_min);
                }
                else {
                    listItem = new ListItem(name);
                }
                Intent intent = new Intent();
                intent.putExtra("listItem", listItem);
                setResult(RESULT_OK,intent);
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
