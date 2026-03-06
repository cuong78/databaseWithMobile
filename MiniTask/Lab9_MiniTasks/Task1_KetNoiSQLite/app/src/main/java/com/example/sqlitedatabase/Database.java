package com.example.sqlitedatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // =========================================================
    // TODO 1: Viết method QueryData(String sql)
    //   - Dùng cho câu lệnh KHÔNG trả kết quả: CREATE, INSERT, UPDATE, DELETE
    //   - Gợi ý: dùng getWritableDatabase() rồi gọi execSQL(sql)
    // =========================================================
    public void QueryData(String sql) {
        // TODO: viết code vào đây

    }

    // =========================================================
    // TODO 2: Viết method getData(String sql)
    //   - Dùng cho câu lệnh CÓ trả kết quả: SELECT
    //   - Trả về kiểu Cursor
    //   - Gợi ý: dùng getReadableDatabase() rồi gọi rawQuery(sql, null)
    // =========================================================
    public Cursor getData(String sql) {
        // TODO: viết code vào đây
        // Nhớ return kết quả!
        return null; // xoa dong nay sau khi viet xong
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
