package com.codificador.contactapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.warkiz.widget.IndicatorSeekBar;

import java.util.ArrayList;

/**
 * Created by Seng on 11/14/2017.
 */

public class ListItemAdapter extends BaseAdapter{

    ArrayList <ListItem> listItems;
    LayoutInflater inflater;



    public ListItemAdapter(Context context, ArrayList<ListItem> list){
        listItems = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = inflater.inflate(R.layout.list_item_row,viewGroup,false);
        TextView textViewName = rootView.findViewById(R.id.textViewName);
        TextView textViewValue = rootView.findViewById(R.id.textViewValue);

        IndicatorSeekBar indicatorSeekBar = rootView.findViewById(R.id.indicatorSeekBar);
        indicatorSeekBar.setMin(listItems.get(i).getSeekbar_min());
        indicatorSeekBar.setMax(listItems.get(i).getSeekbar_max());
        indicatorSeekBar.setProgress(listItems.get(i).getValue());

        indicatorSeekBar.hideThumb(true);
        indicatorSeekBar.setUserSeekAble(false);
        indicatorSeekBar.setClickable(false);
        indicatorSeekBar.setFocusable(false);

        ListItem listItem = listItems.get(i);
        textViewName.setText(listItem.getName());
        textViewValue.setText(String.valueOf(listItem.getValue()));
        return rootView;
    }

    public void addContact(ListItem c){
        listItems.add(c);
    }

    public void removeContact(int position){
        listItems.remove(position);
    }

    public void updateContact(ListItem listItem, int position){
        listItems.set(position, listItem);
    }
}