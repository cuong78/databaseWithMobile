# Task 5 – Xóa dữ liệu (DELETE) – Hoàn thiện CRUD

## Mục tiêu
Hiểu cách dùng AlertDialog.Builder để xác nhận trước khi xoá, và thực hiện DELETE.

## File cần sửa
- `app/src/main/java/com/example/sqlitedatabase/CongViecAdapter.java`
- `app/src/main/java/com/example/sqlitedatabase/MainActivity.java`

## Yêu cầu

### CongViecAdapter.java
- **TODO** — Trong `getView()`, sau phần xử lý `imgEdit`, thêm click listener cho `holder.imgDelete`:
  gọi `context.DialogXoaCongViec(congViec.getTenCV(), congViec.getIdCV())`

### MainActivity.java
- **TODO** — Viết method `DialogXoaCongViec(String tencv, int Id)`:
  - Tạo `AlertDialog.Builder` (không cần layout XML)
  - `setMessage()` với câu hỏi xác nhận có tên công việc
  - `setPositiveButton("Yes")` → DELETE SQL → Toast → `GetDataCongViec()`
  - `setNegativeButton("No")` → để trống
  - Gọi `.show()`
  - **Lưu ý**: method phải là `public`

## Kết quả mong đợi
Nhấn icon Delete → AlertDialog hỏi xác nhận → nhấn Yes → item biến khỏi ListView → nhấn No → không có gì thay đổi

## Gợi ý nếu bị lỗi
- `Cannot resolve symbol 'AlertDialog'` → kiểm tra import: phải dùng `androidx.appcompat.app.AlertDialog` (đã có sẵn trong file)
- Nhấn Delete không hiện gì → quên gọi `.show()` ở cuối method
- Xoá xong ListView không cập nhật → quên gọi `GetDataCongViec()` trong callback "Yes"
