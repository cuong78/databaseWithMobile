package com.example.sqlitedatabase;

import android.app.Dialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
        database.QueryData("Insert into CongViec values(null, 'Fix bugs')");

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

    // Da co san - ket qua tu Task 4
    public void DialogSuaCongViec(String ten, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);
        EditText edtTenCV  = (EditText) dialog.findViewById(R.id.editTextTenCV);
        Button btnXacNhan  = (Button)   dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy      = (Button)   dialog.findViewById(R.id.buttonHuyEdit);
        edtTenCV.setText(ten);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '" + tenMoi + "' WHERE id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Da cap nhat", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.dismiss(); }
        });
        dialog.show();
    }

    // =====================================================
    // TODO: Viet method DialogXoaCongViec()
    //   - Duoc Adapter goi khi nguoi dung nhan icon Delete
    //   - Tham so: String tencv (ten cong viec), int Id (ID hang can xoa)
    //
    //   Khac voi Dialog thuong: dung AlertDialog.Builder (khong can layout XML rieng)
    //
    //   Buoc 1: AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this)
    //   Buoc 2: dialogXoa.setMessage("Ban co muon xoa cong viec " + tencv + " khong?")
    //   Buoc 3: dialogXoa.setPositiveButton("Yes", ...) →
    //             DELETE FROM CongViec WHERE Id = '[Id]'
    //             Toast "Da xoa [tencv]"
    //             GetDataCongViec()
    //   Buoc 4: dialogXoa.setNegativeButton("No", ...) → de trong
    //   Buoc 5: dialogXoa.show()
    //
    //   Luu y: method la PUBLIC de Adapter goi duoc
    //   Import da co san: DialogInterface, AlertDialog
    // =====================================================
    public void DialogXoaCongViec(String tencv, int Id) {
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
