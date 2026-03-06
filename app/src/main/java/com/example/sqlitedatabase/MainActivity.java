package com.example.sqlitedatabase;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        // Ánh xạ view
        lvCongViec = findViewById(R.id.listviewCongViec);

        // Khởi tạo list + adapter
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this, "GhiChu.sqlite", null, 1);

        // Tạo bảng CongViec (nếu chưa có)
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenCV NVARCHAR(200))");

        // (Tuỳ chọn) Insert dữ liệu mẫu lần đầu
        database.QueryData("INSERT INTO CongViec VALUES(null, 'Project Android')");
        database.QueryData("INSERT INTO CongViec VALUES(null, 'Design app')");

        // Load dữ liệu từ SQLite lên ListView
        getDataCongViec();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataCongViec() {
        Cursor cursor = database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            arrayCongViec.add(new CongViec(id, ten));
        }

        adapter.notifyDataSetChanged();
    }

    // Hàm cập nhật công việc
    public void DialogSuaCongViec(String ten, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);

        EditText edtTenCV = dialog.findViewById(R.id.editTextTenCV);
        Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuyEdit = dialog.findViewById(R.id.buttonHuyEdit);

        // set lại tên cũ lên ô nhập
        edtTenCV.setText(ten);

        btnXacNhan.setOnClickListener(view -> {
            String tenMoi = edtTenCV.getText().toString().trim();
            database.QueryData("UPDATE CongViec SET TenCV = '" + tenMoi + "' WHERE Id = " + id);
            Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            getDataCongViec();
        });

        btnHuyEdit.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    // Hàm xóa công việc
    public void DialogXoaCongViec(String tencv, int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc \"" + tencv + "\" không?");

        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = " + id);
                Toast.makeText(MainActivity.this, "Đã xóa " + tencv, Toast.LENGTH_SHORT).show();
                getDataCongViec();
            }
        });

        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialogXoa.show();
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtTen = dialog.findViewById(R.id.editTextTenCV);
        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        // Bắt sự kiện cho button Thêm
        btnThem.setOnClickListener(view -> {
            String tencv = edtTen.getText().toString().trim();

            // Kiểm tra chuỗi rỗng -> khi người dùng không nhập dữ liệu
            if (tencv.equals("")) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc !", Toast.LENGTH_SHORT).show();
            } else {
                database.QueryData("Insert into CongViec values(null, '" + tencv + "')");
                Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // tắt hộp thoại sau khi đã thêm xong dữ liệu

                // Show dữ liệu trên listview
                getDataCongViec();
            }
        });

        btnHuy.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}

