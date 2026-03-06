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

        GetDataCongViec();
    }

    // Da co san - ket qua tu Task 3
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Da co san - ket qua tu Task 3
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    // Da co san - ket qua tu Task 3
    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);
        EditText edtTen = (EditText) dialog.findViewById(R.id.editTextTenCV);
        Button btnThem  = (Button)   dialog.findViewById(R.id.buttonThem);
        Button btnHuy   = (Button)   dialog.findViewById(R.id.buttonHuy);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tencv = edtTen.getText().toString();
                if (tencv.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui long nhap ten cong viec !", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("Insert into CongViec values(null, '" + tencv + "')");
                    Toast.makeText(MainActivity.this, "Da them", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.dismiss(); }
        });
        dialog.show();
    }

    // =====================================================
    // TODO: Viet method DialogSuaCongViec()
    //   - Duoc Adapter goi khi nguoi dung nhan icon Edit
    //   - Tham so: String ten (ten hien tai), int id (ID hang can UPDATE)
    //
    //   Buoc 1: tao Dialog, inflate layout dialog_sua
    //   Buoc 2: lay EditText (editTextTenCV), Button (buttonXacNhan), Button (buttonHuyEdit)
    //   Buoc 3: edtTenCV.setText(ten)  → dien san ten cu
    //   Buoc 4: buttonXacNhan onClick:
    //             doc tenMoi = edtTenCV.getText().toString().trim()
    //             chay: UPDATE CongViec SET TenCV = '[tenMoi]' WHERE id = '[id]'
    //             Toast "Da cap nhat" → dialog.dismiss() → GetDataCongViec()
    //   Buoc 5: buttonHuyEdit onClick: dialog.dismiss()
    //   Buoc 6: dialog.show()
    //
    //   Luu y: method la PUBLIC de Adapter goi duoc tu ben ngoai
    // =====================================================
    public void DialogSuaCongViec(String ten, int id) {
        // TODO: viet code vao day

    }

    // Da co san - ket qua tu Task 3
    public void GetDataCongViec() {
        Cursor dataCongViec = database.getData("Select * from CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()) {
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }
}
