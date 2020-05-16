package com.codificador.contactapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListItemAdapter adapter;
    DatabaseHelper databaseHelper;
    Timer mTimer;
    AutoAdjustSeekBarTask mAutoAdjustSeekBarTask;
    static final int NEW_CONTACT_REQUEST_CODE = 1;
    static final int EDIT_CONTACT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        //XmlResourceParser xml = getResources().getXml(R.xml.default_config);
    }

    private void initViews(){
        listView = findViewById(R.id.listView);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        adapter = new ListItemAdapter(MainActivity.this,databaseHelper.getAllContacts());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewListItem((ListItem) adapter.getItem(i));
                selectedPos = i;
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdd:
                Intent intent = new Intent(MainActivity.this, NewListItemActivity.class);
                startActivityForResult(intent, NEW_CONTACT_REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewListItem(ListItem listItem){
        Intent viewContactIntent = new Intent(MainActivity.this, ViewListItemActivity.class);
        viewContactIntent.putExtra("id", listItem.getId());
        viewContactIntent.putExtra("name", listItem.getName());
        viewContactIntent.putExtra("value", listItem.getValue());
        startActivity(viewContactIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_CONTACT_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                ListItem listItem = (ListItem) data.getSerializableExtra("listItem");
                long insertedId = databaseHelper.insertContact(listItem);
                listItem.setId(insertedId);
                adapter.addContact(listItem);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "New listItems added successfully.", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == EDIT_CONTACT_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                ListItem listItem = (ListItem) data.getSerializableExtra("listItem");
                int rows = databaseHelper.update(listItem);
                if(rows > 0){
                    adapter.updateContact(listItem,selectedPos);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "ListItem updated successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.option_menu,menu);
        menu.setHeaderTitle("Options");

    }

    int selectedPos = -1;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedPos = menuInfo.position;
        switch (item.getItemId()){
            case R.id.actionAutoAdjust:
                auto_adjust((ListItem) adapter.getItem(selectedPos));

                break;
            case R.id.actionEdit:
                edit((ListItem) adapter.getItem(selectedPos));
                break;
            case R.id.actionDelete:
                delete((ListItem) adapter.getItem(selectedPos),selectedPos);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void auto_adjust(ListItem listItem){
        mTimer = new Timer();
        mAutoAdjustSeekBarTask = new AutoAdjustSeekBarTask(listItem);
        mTimer.schedule(mAutoAdjustSeekBarTask, 100, 1000);
    }

    private void edit(ListItem listItem){
        Intent intent = new Intent(MainActivity.this, EditListItemActivity.class);
        intent.putExtra("listItem", listItem);
        startActivityForResult(intent, EDIT_CONTACT_REQUEST_CODE);
    }

    private void delete(ListItem listItem, int position){
        int rows = databaseHelper.delete(listItem.getId());
        if(rows > 0){
            adapter.removeContact(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "ListItem successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);
            return false;
        }
        return true;
    }
}