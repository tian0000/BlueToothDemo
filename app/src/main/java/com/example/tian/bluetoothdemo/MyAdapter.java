package com.example.tian.bluetoothdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @name yanantian
 * @motto 莫羡他人谢语花, 腹有诗书气自华
 * @E-mail 1173568715@qq.com
 * @WX 15978622391
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Bean> list;

    public MyAdapter(ArrayList<Bean> list, MainActivity mainActivity) {
        this.list = list;
        this.context = mainActivity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item, null);
            vh.name = view.findViewById(R.id.name);
            vh.content = view.findViewById(R.id.content);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.name.setText(list.get(i).getName());
        vh.content.setText(list.get(i).getContent());
        return view;
    }

    static class ViewHolder {
        TextView name;
        TextView content;
    }


}
