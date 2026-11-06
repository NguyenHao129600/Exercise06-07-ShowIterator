# Exercise 6 & 7 — Iterator for CompositeShow (DesignBook, Chapter 6)

Project Java được viết độc lập dựa trên yêu cầu ở `DesignBook-master.zip`:
`exercises/e-chapter6.md` và cấu trúc API ở `exercise-code/chapter6/`.

**Nguồn tham khảo:** Martin P. Robillard, *Introduction to Software Design with Java*,
DesignBook, Chapter 6 — https://github.com/prmr/DesignBook .
Project này sử dụng mã triển khai tự viết, không chép nguyên các file của sách.

## Yêu cầu và giải pháp

- **Exercise 1 (nền tảng):** `CompositeShow` chứa 2–5 `Show`, hỗ trợ lồng composite.
- **Exercise 2 (nền tảng):** `IntroducedShow` là Decorator thêm lời giới thiệu (thời lượng cố định).
- **Exercise 6:** `Show` giữ nguyên ba phương thức `description()`, `runningTime()`, `copy()`;
  chỉ `CompositeShow implements Iterable<Show>` để công khai Iterator.
- **Exercise 7:** sửa `Show extends Iterable<Show>`; lớp atomic có Iterator rỗng,
  `CompositeShow` trả về iterator duyệt con trực tiếp; `IntroducedShow` chuyển tiếp Iterator
  đến Show được bọc. Nhờ vậy một biến **kiểu `Show`** vẫn có thể dùng vòng `for-each`.

**Quy ước:** Iterator trả về các *phần tử con trực tiếp*, KHÔNG tự động làm phẳng
toàn bộ cây. Để mở một composite lồng nhau, client lặp đệ quy trên các nút con.
`IntroducedShow` giữ phần giới thiệu của nó ở `description()`/`runningTime()`;
khi duyệt children của decorator trong Exercise 7, chỉ lấy components của Show bên trong.

## Cấu trúc

```text
src/main/java/
  chapter6/exercise6/{Show,Movie,Concert,CompositeShow,IntroducedShow}.java
  chapter6/exercise7/{Show,Movie,Concert,CompositeShow,IntroducedShow}.java
  demo/{Exercise06Demo,Exercise07Demo,DemoAll,VerificationRunner}.java
src/test/java/
  chapter6/exercise6/Exercise06Test.java
  chapter6/exercise7/Exercise07Test.java
run.ps1 / run.cmd       # Windows: compile + demo + checks (no Maven needed)
pom.xml                 # Maven + JUnit 5
docs/BAO_CAO_NGAN.md   # Gợi ý báo cáo và vị trí chụp ảnh
docs/class-diagrams.md # UML bằng Mermaid
```

## Cách 1 — chạy ngay trên Windows PowerShell (không cần Maven)

Yêu cầu JDK **17 trở lên** và `java`, `javac` chạy được từ PowerShell.

```powershell
cd "D:\Exercise06-07-ShowIterator"
.\run.ps1
```

Nếu PowerShell chặn script, có thể dùng:

```powershell
powershell -ExecutionPolicy Bypass -File .\run.ps1
```

Hoặc chạy thủ công bằng lệnh PowerShell:

```powershell
$files = @(Get-ChildItem -Recurse src/main/java -Filter *.java | % FullName)
javac -encoding UTF-8 -d out @files
java -cp out demo.DemoAll
java -cp out demo.VerificationRunner
```

Bạn cũng có thể nhấp đúp `run.cmd` để chạy cùng quy trình.

Đã chạy và xác minh `VerificationRunner`: `RESULT: 7 PASSED, 0 FAILED`.
15 phương thức test trong hai file JUnit cũng đã được kiểm tra bằng harness cục bộ;
chưa chạy bằng Maven/JUnit 5 thực tế trong môi trường tạo project.

## Cách 2 — Eclipse

1. File -> Import -> Maven -> Existing Maven Projects -> chọn thư mục có `pom.xml` -> Finish.
2. Mở `src/main/java/demo/DemoAll.java` -> **Run As -> Java Application**.
3. Mở `src/main/java/demo/VerificationRunner.java` -> **Run As -> Java Application**.
4. Mở `src/test/java/chapter6/exercise6/Exercise06Test.java` hoặc `exercise7/Exercise07Test.java`
   -> **Run As -> JUnit Test** (Maven cần tải dependencies JUnit 5 lần đầu).

Không cần JavaFX hoặc MySQL.

## Cách 3 — Maven

```powershell
mvn clean test
mvn exec:java -Dexec.mainClass=demo.DemoAll
mvn exec:java -Dexec.mainClass=demo.VerificationRunner
```

## Ví dụ kết quả

- Exercise 6: Iterator trên `CompositeShow` trả `Concert`, tiếp theo là `CompositeShow` hai phim.
- Exercise 7: Iterator có thể gọi trên biến `Show introducedProgram`.
- Hai demo đều có cách unpack recursively: Concert → Movie 1 → Movie 2 (có giới thiệu).
- Tổng thời lượng minh họa: `60 + 90 + (105 + 5) + 10 = 270 phút`.

## Chú ý khi giải thích với giảng viên

1. **Tại sao hai package?** Vì `Show` bài 6 tuyệt đối không thay đổi,
   còn `Show` bài 7 có API khác. Hai thiết kế không thể dùng chung một interface.
2. **Dùng design pattern gì?** Composite, Decorator từ bài 1–2, Iterator cho bài 6–7.
3. **Iterator có sửa dữ liệu được không?** Không, do danh sách con bất biến.
4. **Vì sao `iterator()` trả về 2 phần tử thay vì 3?** Vì composite ngoài có hai con:
   một concert và một composite chứa hai movies. Muốn cả 3 chương trình atomic thì duyệt đệ quy.
5. **Lưu ý lỗi đề:** Exercise 7 ghi “described in Exercise 7” (tự tham chiếu),
   được hiểu theo ngữ cảnh là nhắc tới chức năng Exercise 6.
