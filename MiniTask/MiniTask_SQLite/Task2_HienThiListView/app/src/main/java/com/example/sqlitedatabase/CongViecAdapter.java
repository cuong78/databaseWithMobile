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
    private Context context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(Context context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    // =========================================================
    // TODO 1: Tra ve so phan tu trong congViecList
    // =========================================================
    @Override
    public int getCount() {
        return 0; // TODO: sua lai cho dung
    }

    @Override
    public Object getItem(int i) { return null; }

    @Override
    public long getItemId(int i) { return 0; }

    // Inner class ViewHolder - giu tham chieu cac View trong 1 dong
    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    // =========================================================
    // TODO 2: Hoan thien getView() - chuc nang inflate va gan du lieu
    //
    //   Buoc A: Kiem tra if (view == null)
    //     - Neu null: tao ViewHolder moi, dung LayoutInflater inflate layout
    //       roi gan cac View vao holder (texviewTen, imageviewDelete, imageviewEdit)
    //       roi goi view.setTag(holder)
    //     - Neu khong null: lay holder tu view.getTag()
    //
    //   Buoc B: Lay doi tuong CongViec tai vi tri i tu congViecList
    //
    //   Buoc C: Gan ten cong viec vao holder.txtTen.setText(...)
    //
    //   Buoc D: return view
    // =========================================================
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        // TODO: viet phan Buoc A vao day


        // TODO: viet phan Buoc B + C vao day


        return view; // giu dong nay, chi them code phia tren
    }
}
