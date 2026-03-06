# Task 2 – Hiển thị dữ liệu lên ListView

## Mục tiêu
Hiểu cách Model – Adapter – ListView phối hợp để hiển thị danh sách dữ liệu.

## File cần sửa
- `app/src/main/java/com/example/sqlitedatabase/CongViecAdapter.java`
- `app/src/main/java/com/example/sqlitedatabase/MainActivity.java`

## Yêu cầu

### CongViecAdapter.java
- **TODO 1** — `getCount()`: trả về `congViecList.size()`
- **TODO 2** — `getView()`: inflate layout nếu view == null, dùng ViewHolder, gán `holder.txtTen.setText(congViec.getTenCV())`

### MainActivity.java
- **TODO** — Sau khi insert dữ liệu: lấy Cursor, duyệt vòng lặp, add `CongViec(id, ten)` vào `arrayCongViec`, gọi `adapter.notifyDataSetChanged()`

## Kết quả mong đợi
App mở ra → ListView hiển thị 3 công việc, mỗi dòng có tên + icon edit + icon delete

## Gợi ý nếu bị lỗi
- ListView trống → quên gọi `notifyDataSetChanged()` hoặc `getCount()` đang return 0
- App crash lúc mở → thiếu file icon `edit.xml` hoặc `delete.xml` trong res/drawable
