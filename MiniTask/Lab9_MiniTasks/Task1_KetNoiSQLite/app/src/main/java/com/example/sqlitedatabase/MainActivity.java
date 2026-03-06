package com.example.sqlitedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Tao database GhiChu (da co san, khong can sua)
        database = new Database(this, "GhiChu.sqlite", null, 1);

        // Tao table CongViec (da co san, khong can sua)
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenCV nvarchar(200))");

        // Insert du lieu mau chi khi bang trong (da co san, khong can sua)
        Cursor check = database.getData("SELECT COUNT(*) FROM CongViec");
        if (check.moveToFirst() && check.getInt(0) == 0) {
            database.QueryData("INSERT INTO CongViec VALUES(null, 'Project Android')");
            database.QueryData("INSERT INTO CongViec VALUES(null, 'Design app')");
            database.QueryData("INSERT INTO CongViec VALUES(null, 'Fix bugs')");
        }
        check.close();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Toast.makeText(this, "SQLite Connected!", Toast.LENGTH_SHORT).show();

            // =====================================================
            // TODO 3: Doc du lieu tu bang CongViec va hien thi Toast
            //   - Goi database.getData("SELECT * FROM CongViec") de lay Cursor
            //   - Dung vong lap moveToNext() de duyet tung hang
            //   - Moi hang: lay cot thu 1 (TenCV) bang getString(1)
            //   - Hien thi Toast voi noi dung TenCV
            //   - Dong Cursor sau khi xong
            // =====================================================

            // TODO: viet code vao day

        });
    }
}
