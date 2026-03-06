# Lab9 – SQLite Mini Tasks

Repo này chứa **5 project Android Studio** tương ứng với 5 bài của Lab 9.
M��i project đã có sẵn code nền, chỉ cần **điền vào phần TODO** để hoàn thiện chức năng.

---

## Cấu trúc repo

```
Lab9_MiniTasks/
├── Task1_KetNoiSQLite/      ← Bài 1: Hoàn thiện Database.java + đọc dữ liệu Toast
├── Task2_HienThiListView/   ← Bài 2: Hoàn thiện CongViecAdapter + hiển thị ListView
├── Task3_ThemDuLieu/        ← Bài 3: Viết DialogThem() + GetDataCongViec()
├── Task4_CapNhatDuLieu/     ← Bài 4: Đổi Context→MainActivity + viết DialogSuaCongViec()
└── Task5_XoaDuLieu/         ← Bài 5: Thêm imgDelete listener + viết DialogXoaCongViec()
```

---

## Cách mở project

1. Mở **Android Studio**
2. **File → Open** → chọn thư mục của task cần làm (ví dụ: `Task1_KetNoiSQLite`)
3. Chờ Gradle sync xong
4. Tìm các comment `// TODO` trong file Java để biết cần viết gì

---

## Tổng quan từng Task

| Task | File cần sửa | Cần làm |
|------|-------------|---------|
| **Task 1** | `Database.java`, `MainActivity.java` | Viết `QueryData()`, `getData()`, đọc DB hiển thị Toast |
| **Task 2** | `CongViecAdapter.java`, `MainActivity.java` | Hoàn thiện `getView()`, tải dữ liệu lên ListView |
| **Task 3** | `MainActivity.java` | Viết `DialogThem()` và `GetDataCongViec()` |
| **Task 4** | `CongViecAdapter.java`, `MainActivity.java` | Đổi `Context→MainActivity`, thêm Edit listener, viết `DialogSuaCongViec()` |
| **Task 5** | `CongViecAdapter.java`, `MainActivity.java` | Thêm Delete listener, viết `DialogXoaCongViec()` |

---

## Lưu ý khi chạy

- Nếu app crash lúc khởi động → kiểm tra TODO chưa hoàn thiện làm return null
- Nếu dữ liệu bị trùng lặp → **Settings → Apps → [tên app] → Clear Data**
- Nếu thiếu icon edit/delete → **File → New → Vector Asset** tạo 2 icon tên `edit` và `delete`
- Gặp lỗi gì → ghi lại thông báo lỗi trong Logcat để trao đổi

---

## Thứ tự làm

Làm **tuần tự từ Task 1 → Task 5**, vì mỗi task phát triển tiếp khái niệm của task trước.
