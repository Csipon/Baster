package com.team.baster.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.badlogic.gdx.utils.Array;
import com.team.baster.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smeet on 31.10.2017.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private List<Long> scores;

    MyAdapter(Context context, List<Long> scores) {
        this.context = context;
        this.scores = scores;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.score_list, parent, false);
        }

        long score = getScore(position);
        ((TextView) view.findViewById(R.id.textScore)).setText(String.valueOf(score));

        return view;
    }

    private Long getScore(int position) {
        return (Long) getItem(position);
    }
}
