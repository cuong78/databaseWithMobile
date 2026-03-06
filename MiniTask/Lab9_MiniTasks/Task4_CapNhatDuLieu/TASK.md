# Task 4 – Cập nhật dữ liệu (UPDATE)

## Mục tiêu
Hiểu cách Adapter gọi ngược lên Activity, và cách UPDATE dữ liệu qua Dialog.

## File cần sửa
- `app/src/main/java/com/example/sqlitedatabase/CongViecAdapter.java`
- `app/src/main/java/com/example/sqlitedatabase/MainActivity.java`

## Yêu cầu

### CongViecAdapter.java
- **TODO 1** — Đổi kiểu field `context`: `Context` → `MainActivity`
- **TODO 2** — Đổi kiểu tham số constructor tương tự
- **TODO 3** — Trong `getView()`, thêm click listener cho `holder.imgEdit`:
  gọi `context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV())`

### MainActivity.java
- **TODO** — Hoàn thiện `DialogSuaCongViec(String ten, int id)`:
  - Tạo Dialog, inflate `dialog_sua`
  - `edtTenCV.setText(ten)` để điền sẵn tên cũ
  - Nhấn XÁC NHẬN → UPDATE SQL → dismiss → `GetDataCongViec()`
  - Nhấn HỦY → `dialog.dismiss()`

## Kết quả mong đợi
Nhấn icon Edit → Dialog mở với tên cũ điền sẵn → sửa tên → nhấn XÁC NHẬN → ListView cập nhật ngay

## Gợi ý nếu bị lỗi
- `Cannot resolve method 'DialogSuaCongViec'` → chưa đổi kiểu `context` sang `MainActivity` (TODO 1 + 2)
- EditText trống khi dialog mở → quên `edtTenCV.setText(ten)`
