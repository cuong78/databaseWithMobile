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
    public int getCount() { return congViecList.size(); }

    @Override
    public Object getItem(int i) { return null; }

    @Override
    public long getItemId(int i) { return 0; }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTen    = (TextView)  view.findViewById(R.id.texviewTen);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete);
            holder.imgEdit   = (ImageView) view.findViewById(R.id.imageviewEdit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CongViec congViec = congViecList.get(i);
        holder.txtTen.setText(congViec.getTenCV());

        // Su kien Edit - da co san
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        // =====================================================
        // TODO: Them click listener cho imgDelete
        //   Khi nguoi dung nhan icon Delete:
        //   - Goi context.DialogXoaCongViec(congViec.getTenCV(), congViec.getIdCV())
        //   Tuong tu cach viet click listener cho imgEdit o phia tren
        // =====================================================

        // TODO: viet click listener cho holder.imgDelete o day

        return view;
    }
}
