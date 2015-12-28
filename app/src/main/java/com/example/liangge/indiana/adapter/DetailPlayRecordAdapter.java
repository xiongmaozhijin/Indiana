package com.example.liangge.indiana.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.liangge.indiana.model.ResponseActivityPlayRecordEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页所有参与记录的Adapter
 * Created by baoxing on 2015/12/28.
 */
public class DetailPlayRecordAdapter extends BaseAdapter
{

    private List<ResponseActivityPlayRecordEntity> listData = new ArrayList<>();

    @Override
    public int getCount() {

        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;



    }




    private static class ViewHolder {

    }
















}
