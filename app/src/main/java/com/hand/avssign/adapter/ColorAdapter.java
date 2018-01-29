package com.hand.avssign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hand.avssign.R;

import java.util.ArrayList;

public class ColorAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Integer> objects;

    public ColorAdapter(Context context, ArrayList<Integer> colors) {
        objects = colors;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = inflater.inflate(R.layout.color_item, parent, false);
        int color = getColor(position);
        View colorView = view.findViewById(R.id.view_color);
        colorView.setBackgroundColor(color);
        colorView.setTag(position);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private Integer getColor(int position) {
        return (Integer) getItem(position);
    }

}
