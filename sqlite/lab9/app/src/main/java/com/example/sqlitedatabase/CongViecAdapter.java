package com.example.sqlitedatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int i) {
        return congViecList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            holder.txtTen = view.findViewById(R.id.texviewTen);
            holder.imgDelete = view.findViewById(R.id.imageviewDelete);
            holder.imgEdit = view.findViewById(R.id.imageviewEdit);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CongViec congViec = congViecList.get(i);
        holder.txtTen.setText(congViec.getTenCV());

        // Bắt sự kiện Sửa
        holder.imgEdit.setOnClickListener(v ->
                context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV())
        );

        // Bắt sự kiện Xóa
        holder.imgDelete.setOnClickListener(v ->
                context.DialogXoaCongViec(congViec.getTenCV(), congViec.getIdCV())
        );

        return view;
    }
}