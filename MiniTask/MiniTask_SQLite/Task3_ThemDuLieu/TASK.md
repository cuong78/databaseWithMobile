# Task 3 – Thêm dữ liệu qua Dialog (INSERT)

## Mục tiêu
Hiểu cách mở Dialog, đọc input từ EditText, chạy INSERT và làm mới ListView.

## File cần sửa
- `app/src/main/java/com/example/sqlitedatabase/MainActivity.java`

## Yêu cầu

### MainActivity.java
- **TODO 1** — Hoàn thiện `DialogThem()`:
  - Tạo Dialog, inflate `dialog_them_cong_viec`
  - Gán sự kiện cho Button Thêm: validate → INSERT → dismiss → `GetDataCongViec()`
  - Gán sự kiện cho Button Hủy: `dialog.dismiss()`
  - Gọi `dialog.show()`

- **TODO 2** — Hoàn thiện `GetDataCongViec()`:
  - `arrayCongViec.clear()`
  - Duyệt Cursor, add CongViec vào array
  - `adapter.notifyDataSetChanged()`

## Kết quả mong đợi
Nhấn icon + → Dialog mở → nhập tên → nhấn Thêm → Dialog đóng → tên mới xuất hiện trong ListView ngay lập tức

## Gợi ý nếu bị lỗi
- Dialog mở nhưng nhấn Thêm không có gì xảy ra → kiểm tra id XML khớp với `dialog.findViewById()`
- Dữ liệu trùng sau khi thêm → thiếu `arrayCongViec.clear()` trong `GetDataCongViec()`
