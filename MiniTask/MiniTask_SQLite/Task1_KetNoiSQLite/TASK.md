# Task 1 – Kết nối SQLite & đọc dữ liệu bằng Toast

## Mục tiêu
Hiểu cách Database.java bọc SQLiteOpenHelper và cách truy vấn dữ liệu cơ bản.

## File cần sửa
- `app/src/main/java/com/example/sqlitedatabase/Database.java`
- `app/src/main/java/com/example/sqlitedatabase/MainActivity.java`

## Yêu cầu

### Database.java
- **TODO 1** — Viết method `QueryData(String sql)`: dùng `getWritableDatabase().execSQL(sql)`
- **TODO 2** — Viết method `getData(String sql)`: dùng `getReadableDatabase().rawQuery(sql, null)` rồi return kết quả

### MainActivity.java
- **TODO 3** — Trong click listener của Button: gọi `database.getData("SELECT * FROM CongViec")`, duyệt Cursor, hiển thị Toast từng tên công việc

## Kết quả mong đợi
Nhấn nút → Toast "SQLite Connected!" → lần lượt Toast "Project Android", "Design app", "Fix bugs"

## Gợi ý nếu bị lỗi
- `getData()` đang return null → chưa hoàn thiện TODO 2, app sẽ crash khi gọi moveToNext()
- Toast không xuất hiện sau "Connected!" → chưa viết TODO 3
