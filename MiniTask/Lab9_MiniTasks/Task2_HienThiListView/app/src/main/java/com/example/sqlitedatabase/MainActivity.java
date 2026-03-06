package com.example.sqlitedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khoi tao ListView, ArrayList, Adapter (da co san)
        lvCongViec = (ListView) findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        // Tao database va bang (da co san)
        database = new Database(this, "GhiChu.sqlite", null, 1);
        database.QueryData("Create table if not exists CongViec(id Integer Primary Key Autoincrement," +
                "TenCV nvarchar(200))");
        database.QueryData("Insert into CongViec values(null, 'Project Android')");
        database.QueryData("Insert into CongViec values(null, 'Design app')");
        database.QueryData("Insert into CongViec values(null, 'Fix bugs')");

        // =====================================================
        // TODO: Doc du lieu tu SQLite vao arrayCongViec roi hien thi len ListView
        //   1. Goi database.getData("Select * from CongViec") de lay Cursor
        //   2. Dung vong lap moveToNext() duyet tung hang
        //   3. Moi hang: lay id = getInt(0), ten = getString(1)
        //   4. Tao doi tuong CongViec(id, ten) va add vao arrayCongViec
        //   5. Sau vong lap: goi adapter.notifyDataSetChanged() de ListView ve lai
        // =====================================================

        // TODO: viet code vao day

    }
}
