package com.hand.avssign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hand.avssign.R;
import com.hand.avssign.view.ThicknessBrowseView;

import java.util.ArrayList;

public class ThicknessAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Integer> objects;

    public ThicknessAdapter(Context context, ArrayList<Integer> thicknesses) {
        objects = thicknesses;
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
        if (view == null) view = inflater.inflate(R.layout.thickness_item, parent, false);
        ThicknessBrowseView thicknessView = view.findViewById(R.id.view_thickness);
        thicknessView.setThickness(getThickness(position));
        thicknessView.setTag(position);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private Integer getThickness(int position) {
        return (Integer) getItem(position);
    }

}
