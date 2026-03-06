package com.example.sqlitedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    // =========================================================
    // TODO 1: Doi kieu field 'context': Context  →  MainActivity
    //   Ly do: MainActivity moi co method DialogSuaCongViec()
    //   Sau khi doi: xoa "import android.content.Context;" o tren
    // =========================================================
    private Context context; // TODO: doi thanh "private MainActivity context;"

    private int layout;
    private List<CongViec> congViecList;

    // =========================================================
    // TODO 2: Doi kieu tham so constructor: Context  →  MainActivity
    // =========================================================
    public CongViecAdapter(Context context, int layout, List<CongViec> congViecList) { // TODO: Context → MainActivity
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

        // =====================================================
        // TODO 3: Them click listener cho imgEdit
        //   Goi context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV())
        //   (Chi lam duoc sau khi da doi Context → MainActivity o TODO 1+2)
        // =====================================================

        // TODO: viet click listener cho holder.imgEdit o day

        return view;
    }
}
