package com.example.sqlitedatabase;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        lvCongViec = (ListView) findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        database = new Database(this, "GhiChu.sqlite", null, 1);
        database.QueryData("Create table if not exists CongViec(id Integer Primary Key Autoincrement," +
                "TenCV nvarchar(200))");
        database.QueryData("Insert into CongViec values(null, 'Project Android')");
        database.QueryData("Insert into CongViec values(null, 'Design app')");

        // Goi GetDataCongViec() de hien thi du lieu (da co san)
        GetDataCongViec();
    }

    // Menu inflate - da co san, khong can sua
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Xu ly khi nhan icon tren Toolbar - da co san, khong can sua
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    // =====================================================
    // TODO 1: Hoan thien method DialogThem()
    //   Buoc 1: Tao Dialog, setContentView voi layout dialog_them_cong_viec
    //   Buoc 2: Lay tham chieu EditText (id: editTextTenCV),
    //           Button them (id: buttonThem), Button huy (id: buttonHuy)
    //   Buoc 3: Gan su kien cho buttonThem:
    //     - Doc text tu EditText
    //     - Neu rong: Toast canh bao "Vui long nhap ten cong viec !"
    //     - Neu co noi dung: chay INSERT SQL, Toast "Da them",
    //       goi dialog.dismiss() va GetDataCongViec()
    //   Buoc 4: Gan su kien cho buttonHuy: chi goi dialog.dismiss()
    //   Buoc 5: Goi dialog.show()
    // =====================================================
    private void DialogThem() {
        // TODO: viet code vao day

    }

    // =====================================================
    // TODO 2: Hoan thien method GetDataCongViec()
    //   Buoc 1: Goi database.getData("Select * from CongViec")
    //   Buoc 2: Goi arrayCongViec.clear() de xoa du lieu cu
    //   Buoc 3: Vong lap moveToNext(), lay id va ten, add CongViec vao arrayCongViec
    //   Buoc 4: Goi adapter.notifyDataSetChanged()
    // =====================================================
    private void GetDataCongViec() {
        // TODO: viet code vao day

    }
}
