# Room Database - Hướng Dẫn Chi Tiết

## 1. Room Database là gì?

**Room** là một thư viện ORM (Object-Relational Mapping) được Google phát triển để làm việc với SQLite database trên Android một cách dễ dàng và an toàn hơn.

### Tại sao sử dụng Room?
- **Type-safe**: Room kiểm tra SQL queries tại compile-time, giúp tránh lỗi runtime
- **Giảm boilerplate code**: Tự động generate code từ annotations
- **Tích hợp với LiveData/RxJava**: Hỗ trợ reactive programming
- **Migration support**: Dễ dàng nâng cấp database schema
- **Performance**: Tối ưu hóa queries và caching

## 2. Mối quan hệ giữa SQLite và Room

### SQLite là gì?
SQLite là một **embedded database engine** (cơ sở dữ liệu nhúng) được tích hợp sẵn trong Android. Nó lưu trữ dữ liệu dưới dạng file `.db` trên thiết bị.

### Room hoạt động như thế nào?
```
┌─────────────────┐
│   Your Code     │  ← Sử dụng Room API
└────────┬────────┘
         │
┌────────▼────────┐
│   Room Library  │  ← Xử lý annotations, generate code
└────────┬────────┘
         │
┌────────▼────────┐
│  SQLite Engine  │  ← Thực thi SQL queries
└────────┬────────┘
         │
┌────────▼────────┐
│   .db File      │  ← Lưu trữ dữ liệu trên thiết bị
└─────────────────┘
```

**Room là một lớp abstraction** trên SQLite:
- Bạn viết code Java/Kotlin với annotations
- Room tự động generate SQL code
- SQLite thực thi các SQL queries đó
- Dữ liệu được lưu vào file database trên thiết bị

## 3. Các thành phần chính của Room

Room có **3 thành phần cốt lõi**:

### 3.1. Entity (Thực thể)
**Entity** đại diện cho một **bảng (table)** trong database.

#### Ví dụ: Person Entity
```java
@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;
    
    // Constructors, getters, setters...
}
```

#### Các Annotation quan trọng trong Entity:

**`@Entity`**
- **Mục đích**: Đánh dấu class là một Entity (bảng trong database)
- **Tham số**:
  - `tableName`: Tên bảng trong SQLite (mặc định là tên class viết thường)
- **Ví dụ**: `@Entity(tableName = "person")` → Tạo bảng `person` trong SQLite

**`@PrimaryKey`**
- **Mục đích**: Đánh dấu field là **khóa chính** (primary key)
- **Tham số**:
  - `autoGenerate = true`: Tự động tăng giá trị (1, 2, 3, ...)
- **Ví dụ**: `@PrimaryKey(autoGenerate = true)` → `uid` sẽ tự động tăng

**`@ColumnInfo`**
- **Mục đích**: Định nghĩa tên cột trong database (nếu khác tên field)
- **Tham số**:
  - `name`: Tên cột trong SQLite
- **Ví dụ**: `@ColumnInfo(name = "first_name")` → Field `firstName` → Cột `first_name` trong SQLite

**`@Ignore`**
- **Mục đích**: Bỏ qua field này, không lưu vào database
- **Ví dụ**: `@Ignore private String tempData;`

#### SQL tương đương:
```sql
CREATE TABLE person (
    uid INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT,
    last_name TEXT
);
```

### 3.2. DAO (Data Access Object)
**DAO** chứa các **phương thức truy cập database** (CRUD operations).

#### Ví dụ: PersonDao
```java
@Dao
public interface PersonDao {
    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("SELECT * FROM person WHERE uid IN (:personId)")
    Person loadPersonById(int personId);

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);
}
```

#### Các Annotation quan trọng trong DAO:

**`@Dao`**
- **Mục đích**: Đánh dấu interface là DAO
- **Chức năng**: Room sẽ generate implementation cho interface này

**`@Query`**
- **Mục đích**: Định nghĩa SQL query tùy chỉnh
- **Tham số**: SQL query string
- **Ví dụ**:
  ```java
  @Query("SELECT * FROM person WHERE uid = :id")
  Person findById(int id);
  ```
- **Lưu ý**: 
  - Room kiểm tra SQL tại compile-time
  - Sử dụng `:parameterName` để truyền tham số
  - Có thể return `List<T>`, `T`, `LiveData<T>`, `Flow<T>`

**`@Insert`**
- **Mục đích**: Insert một hoặc nhiều entities
- **Tham số**:
  - `onConflict`: Xử lý khi có conflict (REPLACE, IGNORE, ABORT, ...)
- **Ví dụ**:
  ```java
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Person person);
  ```
- **Return**: Có thể return `long` (row ID) hoặc `long[]` (nhiều row IDs)

**`@Update`**
- **Mục đích**: Update entity dựa trên primary key
- **Ví dụ**:
  ```java
  @Update
  void update(Person person);
  ```
- **Cách hoạt động**: Room tìm entity có cùng primary key và update các field khác

**`@Delete`**
- **Mục đích**: Xóa entity dựa trên primary key
- **Ví dụ**:
  ```java
  @Delete
  void delete(Person person);
  ```

#### SQL tương đương:
```sql
-- getAll()
SELECT * FROM person;

-- loadPersonById(1)
SELECT * FROM person WHERE uid = 1;

-- insert(person)
INSERT INTO person (first_name, last_name) VALUES (?, ?);

-- update(person)
UPDATE person SET first_name = ?, last_name = ? WHERE uid = ?;

-- delete(person)
DELETE FROM person WHERE uid = ?;
```

### 3.3. Database (Cơ sở dữ liệu)
**Database** là một **abstract class** kế thừa `RoomDatabase`, đại diện cho toàn bộ database.

#### Ví dụ: AppDatabase
```java
@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
```

#### Các Annotation và Key quan trọng:

**`@Database`**
- **Mục đích**: Đánh dấu class là Room Database
- **Tham số**:
  - `entities`: Mảng các Entity classes trong database
  - `version`: Phiên bản database (tăng lên khi thay đổi schema)
  - `exportSchema`: Có export schema không (mặc định true)
- **Ví dụ**: `@Database(entities = {Person.class}, version = 1)`

**`extends RoomDatabase`**
- **Mục đích**: Kế thừa từ RoomDatabase để có các tính năng của Room
- **Chức năng**: Cung cấp các method để build database instance

**Abstract methods (DAO getters)**
- **Mục đích**: Khai báo các DAO mà database này cung cấp
- **Ví dụ**: `public abstract PersonDao personDao();`
- **Cách hoạt động**: Room tự động generate implementation

#### Tạo Database Instance:
```java
AppDatabase db = Room.databaseBuilder(
    getApplicationContext(),  // Context
    AppDatabase.class,         // Database class
    "app-database"            // Database name (tên file .db)
).build();
```

**Lưu ý quan trọng**:
- Database instance nên là **Singleton** (một instance duy nhất)
- Database operations phải chạy trên **background thread** (không phải main/UI thread)
- File database sẽ được lưu tại: `/data/data/<package>/databases/app-database`

## 4. Luồng hoạt động của Room

### 4.1. Khi Insert dữ liệu:
```
1. Bạn gọi: db.personDao().insert(person)
   ↓
2. Room kiểm tra @Insert annotation
   ↓
3. Room generate SQL: INSERT INTO person (first_name, last_name) VALUES (?, ?)
   ↓
4. Room bind giá trị: person.getFirstName(), person.getLastName()
   ↓
5. SQLite thực thi SQL query
   ↓
6. Dữ liệu được lưu vào file .db
```

### 4.2. Khi Query dữ liệu:
```
1. Bạn gọi: List<Person> persons = db.personDao().getAll()
   ↓
2. Room kiểm tra @Query annotation
   ↓
3. Room generate SQL: SELECT * FROM person
   ↓
4. SQLite thực thi query và trả về Cursor
   ↓
5. Room map Cursor → List<Person> objects
   ↓
6. Trả về List<Person> cho bạn
```

## 5. Các Key Concepts quan trọng

### 5.1. Primary Key (Khóa chính)
- **Mục đích**: Định danh duy nhất mỗi row trong bảng
- **Ví dụ**: `uid` trong Person
- **Lưu ý**: Mỗi Entity phải có ít nhất 1 primary key

### 5.2. AutoGenerate
- **Mục đích**: Tự động tăng giá trị primary key
- **Ví dụ**: `@PrimaryKey(autoGenerate = true)`
- **Cách hoạt động**: SQLite tự động tăng (1, 2, 3, ...)

### 5.3. Thread Safety (An toàn đa luồng)
- **Vấn đề**: Database operations **KHÔNG được** chạy trên UI thread
- **Giải pháp**: Sử dụng background threads
- **Ví dụ trong code**:
  ```java
  AppExecutors.getInstance().diskIO().execute(new Runnable() {
      @Override
      public void run() {
          // Database operations ở đây
          mDb.personDao().insert(person);
      }
  });
  ```

### 5.4. Database Version & Migration
- **Version**: Số phiên bản database
- **Khi nào cần tăng version**: Khi thay đổi schema (thêm/xóa/sửa cột)
- **Migration**: Code để chuyển đổi từ version cũ sang version mới
- **Ví dụ**:
  ```java
  @Database(entities = {Person.class}, version = 2) // Tăng từ 1 → 2
  ```

## 6. So sánh SQLite thuần vs Room

### SQLite thuần:
```java
// Phải viết nhiều code
SQLiteDatabase db = helper.getWritableDatabase();
ContentValues values = new ContentValues();
values.put("first_name", person.getFirstName());
values.put("last_name", person.getLastName());
db.insert("person", null, values);

// Phải tự quản lý Cursor
Cursor cursor = db.query("person", null, null, null, null, null, null);
List<Person> persons = new ArrayList<>();
while (cursor.moveToNext()) {
    Person p = new Person();
    p.setUid(cursor.getInt(0));
    p.setFirstName(cursor.getString(1));
    // ...
    persons.add(p);
}
cursor.close();
```

### Room:
```java
// Code ngắn gọn, type-safe
db.personDao().insert(person);
List<Person> persons = db.personDao().getAll();
```

## 7. Cấu trúc thư mục trong project

```
app/src/main/java/com/example/roomdatabase/
├── model/
│   └── Person.java          # Entity
├── dao/
│   └── PersonDao.java        # DAO interface
├── db/
│   └── AppDatabase.java     # Database class
├── adapter/
│   └── PersonAdapter.java   # RecyclerView adapter
├── constants/
│   └── Constants.java       # Constants
├── AppExecutors.java        # Thread management
├── PersonActivity.java      # Main activity
└── EditPersonActivity.java  # Edit activity
```

## 8. Best Practices

1. **Luôn chạy database operations trên background thread**
2. **Sử dụng Singleton pattern cho Database instance**
3. **Đặt tên table và column rõ ràng, nhất quán**
4. **Sử dụng `@ColumnInfo` khi tên field khác tên column**
5. **Tăng version khi thay đổi schema và viết Migration**
6. **Sử dụng `@Query` cho các query phức tạp**
7. **Sử dụng `@Insert(onConflict = ...)` để xử lý conflicts**

## 9. Tóm tắt

- **Room** = Lớp abstraction trên SQLite
- **Entity** = Bảng trong database (dùng `@Entity`, `@PrimaryKey`, `@ColumnInfo`)
- **DAO** = Phương thức truy cập database (dùng `@Query`, `@Insert`, `@Update`, `@Delete`)
- **Database** = Abstract class quản lý database (dùng `@Database`)
- **Room tự động generate SQL** từ annotations
- **SQLite thực thi SQL** và lưu dữ liệu vào file `.db`
- **Luôn chạy database operations trên background thread**

## 10. Tài liệu tham khảo

- [Room Persistence Library - Android Developers](https://developer.android.com/training/data-storage/room)
- [Room Database - Codelabs](https://codelabs.developers.google.com/codelabs/android-room-with-a-view)
