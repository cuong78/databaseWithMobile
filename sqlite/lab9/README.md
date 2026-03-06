## Tổng quan về SQLite trong Android (SqliteDatabase)

### 1. SQLite là gì? Bản chất trong Android

- **SQLite** là hệ quản trị cơ sở dữ liệu quan hệ (RDBMS) **nhẹ, nhúng (embedded)**, lưu dữ liệu trong **một file duy nhất** trên bộ nhớ thiết bị.
- Trên Android, mỗi ứng dụng có thể tạo **nhiều file database riêng**, nằm trong thư mục nội bộ của app (`/data/data/<package>/databases/`) → **app khác không truy cập được**.
- SQLite hỗ trợ:
  - **Bảng, cột, khóa chính, khóa ngoại**.
  - Câu lệnh **SQL chuẩn**: `CREATE TABLE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE`, …
- Dữ liệu **không mất** khi thoát app; chỉ mất khi:
  - Gỡ cài đặt ứng dụng.
  - Xoá data ứng dụng hoặc xoá file database bằng code.

Trong project này, file database tên **`GhiChu.sqlite`** được quản lý qua class `Database`.

---

### 2. Kiến trúc tổng thể của bài lab

- **`Database`**: lớp trợ giúp truy vấn SQLite (extends `SQLiteOpenHelper`).
- **`CongViec`**: model dữ liệu (Id + Tên công việc).
- **`MainActivity`**:
  - Khởi tạo database.
  - Tạo bảng `CongViec`.
  - Load dữ liệu từ SQLite lên **`ListView`**.
  - Xử lý thêm / sửa / xoá công việc.
- **`CongViecAdapter`** (BaseAdapter):
  - Gắn `ArrayList<CongViec>` lên `ListView` với layout item `dong_cong_viec`.
  - Bắt sự kiện bấm nút **sửa** / **xoá** trên từng dòng.
- Các layout:
  - `activity_main.xml`: chứa `ListView` hiển thị danh sách.
  - `dong_cong_viec.xml`: layout 1 dòng công việc (tên + icon sửa/xoá).
  - `dialog_them_cong_viec.xml`: dialog thêm công việc.
  - `dialog_sua.xml`: dialog cập nhật công việc.
- Menu:
  - `add_congviec.xml`: menu trên ActionBar với icon **dấu +** để mở dialog thêm.

---

### 3. Class `Database` – làm việc với SQLite

**Mục tiêu**: ẩn đi phần “kết nối database” và cung cấp 2 hàm đơn giản:

- `QueryData(String sql)`: chạy các câu lệnh **không trả kết quả**:
  - Dùng cho: `CREATE TABLE`, `INSERT`, `UPDATE`, `DELETE`.
- `GetData(String sql)`: chạy câu lệnh **trả kết quả** (thường là `SELECT`) và trả về `Cursor`.

**Quy trình tạo database / bảng:**

1. Trong `MainActivity`:
   - `database = new Database(this, "GhiChu.sqlite", null, 1);`
   - Nếu `GhiChu.sqlite` chưa tồn tại → Android tạo file mới.
2. Tạo bảng:
   - `database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV NVARCHAR(200))");`

**Đọc dữ liệu:**

- `Cursor cursor = database.GetData("SELECT * FROM CongViec");`
- Duyệt `cursor.moveToNext()`, lấy:
  - `id = cursor.getInt(0);`
  - `ten = cursor.getString(1);`
- Thêm vào `ArrayList<CongViec>` và gọi `adapter.notifyDataSetChanged()`.

---

### 4. Model `CongViec`

- Thể hiện **1 bản ghi** trong bảng `CongViec`.
- Thuộc tính:
  - `int IdCV`
  - `String TenCV`
- Có **constructor**, **getter** và **setter**.
- Giúp code rõ ràng, dễ truyền dữ liệu giữa Adapter và Activity.

---

### 5. `MainActivity` – luồng làm việc chính

**5.1. Khởi tạo và hiển thị dữ liệu**

- Ánh xạ `ListView`:
  - `lvCongViec = findViewById(R.id.listviewCongViec);`
- Khởi tạo danh sách + adapter:
  - `arrayCongViec = new ArrayList<>();`
  - `adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);`
  - `lvCongViec.setAdapter(adapter);`
- Khởi tạo database, tạo bảng, (có thể) insert dữ liệu mẫu, sau đó gọi `getDataCongViec()` để:
  - Đọc tất cả bản ghi `CongViec`.
  - Đổ vào `arrayCongViec`.
  - Cập nhật `ListView`.

**5.2. Menu thêm công việc**

- `onCreateOptionsMenu`:
  - Inflate file `add_congviec.xml` → tạo icon dấu **+** trên ActionBar.
- `onOptionsItemSelected`:
  - Khi click `menuAdd` → gọi `DialogThem()`.

**5.3. Thêm công việc – `DialogThem()`**

- Tạo `Dialog`, set layout `dialog_them_cong_viec`.
- Ánh xạ:
  - `EditText editTextTenCV`
  - Button `Thêm`, Button `Hủy`.
- Khi bấm **Thêm**:
  - Lấy chuỗi nhập vào, kiểm tra rỗng.
  - Nếu hợp lệ → `INSERT INTO CongViec VALUES(null, 'tên')`.
  - Hiện Toast *“Đã thêm”*, đóng dialog.
  - Gọi `getDataCongViec()` để refresh ListView.

---

### 6. `CongViecAdapter` – hiển thị ListView + bắt sự kiện

**Nhiệm vụ:**
- Nói cho `ListView` biết:
  - Có bao nhiêu item (`getCount`).
  - Layout của mỗi item (`dong_cong_viec.xml`).
  - Dữ liệu hiển thị cho từng vị trí.
- Sử dụng **ViewHolder pattern**:
  - Tránh gọi `findViewById` liên tục → tối ưu hiệu năng.

**Trong `getView`:**

- Inflate layout nếu `view == null`.
- Ánh xạ:
  - `TextView txtTen`
  - `ImageView imgEdit`, `imgDelete`
- Set dữ liệu:
  - `txtTen.setText(congViec.getTenCV());`
- Bắt sự kiện:
  - **Sửa**: `imgEdit` → gọi `context.DialogSuaCongViec(ten, id);`
  - **Xóa**: `imgDelete` → gọi `context.DialogXoaCongViec(ten, id);`

Nhờ đó, mọi xử lý logic (SQL, dialog) nằm trong `MainActivity`, còn Adapter chỉ chịu trách nhiệm “gửi sự kiện” và hiển thị.

---

### 7. Cập nhật (Update) công việc – `DialogSuaCongViec`

Khi bấm icon **sửa**:

1. Adapter gọi `MainActivity.DialogSuaCongViec(tenCV, idCV)`.
2. `DialogSuaCongViec`:
   - Tạo `Dialog`, dùng layout `dialog_sua`.
   - Điền sẵn tên cũ vào `EditText`.
   - Nút **XÁC NHẬN**:
     - Lấy tên mới.
     - Chạy câu lệnh:
       - `UPDATE CongViec SET TenCV = 'tên mới' WHERE Id = id;`
     - Thông báo Toast “Đã cập nhật”.
     - Đóng dialog, gọi `getDataCongViec()` để refresh.
   - Nút **HỦY** → đóng dialog, không thay đổi dữ liệu.

**Ý nghĩa:** minh họa thao tác **UPDATE** trong CRUD với SQLite trong Android.

---

### 8. Xóa (Delete) công việc – `DialogXoaCongViec`

Khi bấm icon **delete**:

1. Adapter gọi `MainActivity.DialogXoaCongViec(tenCV, idCV)`.
2. `DialogXoaCongViec`:
   - Tạo **`AlertDialog.Builder`**:
     - `setMessage("Bạn có muốn xóa công việc \"tên\" không?")`
   - Nút **Yes**:
     - Chạy:
       - `DELETE FROM CongViec WHERE Id = id;`
     - Thông báo “Đã xóa tên công việc”.
     - Gọi `getDataCongViec()` để load lại danh sách.
   - Nút **No**:
     - Đóng dialog, không xóa.

**Ý nghĩa:** thể hiện thao tác **DELETE** với xác nhận từ người dùng, tránh xóa nhầm.

---

### 9. Tóm tắt ý chính để thuyết trình

- **SQLite trong Android**:
  - Là database nhúng, lưu local trong file `.sqlite`.
  - Mỗi app có database riêng, quản lý qua `SQLiteOpenHelper`.
- **Kiến trúc ứng dụng**:
  - `Database` quản lý kết nối & thực thi SQL.
  - `MainActivity` điều khiển luồng nghiệp vụ + UI chính.
  - `CongViecAdapter` hiển thị ListView, kết nối dữ liệu ↔ giao diện.
  - Model `CongViec` đại diện cho một bản ghi trong bảng.
- **Chức năng CRUD**:
  - **Create**: Dialog thêm → `INSERT`.
  - **Read**: `getDataCongViec()` → `SELECT` + hiển thị ListView.
  - **Update**: Dialog sửa → `UPDATE` theo `Id`.
  - **Delete**: AlertDialog xác nhận → `DELETE` theo `Id`.
- **Điểm nhấn**:
  - Kết hợp SQLite + ListView + Adapter + Dialog/AlertDialog.
  - Dữ liệu thay đổi trong database được phản ánh ngay lên UI nhờ `getDataCongViec()` và `adapter.notifyDataSetChanged()`.

