# GỢI Ý BÁO CÁO EXERCISE 06 & 07 — DESIGNBOOK CHAPTER 6

## 1. Mục tiêu

Bổ sung chức năng duyệt các `Show` bên trong `CompositeShow` bằng Iterator Pattern.
Xây dựng hai phương án nhằm so sánh tác động khi có và không sửa `Show`.
Hệ thống nền tảng từ Exercises 1–2 áp dụng Composite + Decorator.

## 2. Hệ thống đã xây dựng

- `Show`: API `description()`, `runningTime()`, `copy()`.
- `Movie`, `Concert`: lớp đơn lẻ (leaf).
- `CompositeShow`: chứa 2–5 `Show` theo thứ tự, cho phép composite lồng nhau.
- `IntroducedShow`: bọc một `Show`, thêm tên người giới thiệu và số phút phát biểu.

**Vị trí ảnh:** chụp các file `Show.java`, `CompositeShow.java`, `IntroducedShow.java`.

## 3. Exercise 6 — Giữ nguyên Show

`chapter6.exercise6.Show` không có `iterator()` và không kế thừa `Iterable`.
Trong khi đó, `CompositeShow implements Show, Iterable<Show>`.
Hàm `iterator()` dùng `shows.iterator()` để trả các phần tử con trực tiếp.
Client biết đối tượng cụ thể là `CompositeShow` thì có thể `for (Show child : composite)`.
Client chỉ giữ biến kiểu `Show` không thể for-each trực tiếp vì interface Show không khai báo Iterable.

**Vị trí ảnh:** `exercise6/Show.java` và `exercise6/CompositeShow.java` ở `implements`, `iterator()`.
**Ảnh demo:** Console khi chạy `Exercise06Demo`.

## 4. Exercise 7 — Thay đổi Show

`chapter6.exercise7.Show extends Iterable<Show>` và có phương thức `iterator()` mặc định
trả `Collections.emptyIterator()` cho một Show đơn lẻ. `CompositeShow` override `iterator()`
trả các thành phần trực tiếp; `IntroducedShow` override để chuyển tiếp đến Show được bọc.
Vì interface chung hỗ trợ Iterable, code có thể chạy `for (Show child : showVariable)`.
Nhược điểm là API Show thay đổi, ảnh hưởng đến tất cả implementations nếu không có default.

**Vị trí ảnh:** `exercise7/Show.java`, `CompositeShow.java`, `IntroducedShow.java`.
**Ảnh demo:** Console khi chạy `Exercise07Demo`.

## 5. Giải thích cơ chế Iterator

1. Client gọi `iterator()` để lấy iterator mới.
2. `hasNext()` kiểm tra còn phần tử chưa duyệt hay không.
3. `next()` lấy con tiếp theo theo thứ tự đã lưu.
4. Khi gặp `CompositeShow` lồng nhau, client tiếp tục duyệt đệ quy để đi tới leaf.
5. Các phần tử được giữ trong `List.copyOf(...)` nên không thể xóa bằng Iterator.

## 6. Kịch bản demo / kết quả

Tạo một Concert (60 phút), Movie 1 (90 phút), Movie 2 (105 phút);
bọc Movie 2 trong lời giới thiệu 5 phút; ghép hai phim thành Composite;
ghép Concert và Composite phim vào Composite ngoài;
bọc chương trình tổng bằng lời giới thiệu 10 phút.
Tổng `runningTime()` là **270 phút**.

- Exercise 6: duyệt 2 phần tử của Composite ngoài bằng Iterator trực tiếp.
- Exercise 7: duyệt trên biến kiểu `Show`, kể cả `IntroducedShow` bọc Composite.
- Demo giải nén đệ quy hiển thị thứ tự Concert, Movie 1, Movie 2.

**Vị trí ảnh:** chụp `DemoAll.java`, `Exercise06Demo.java`, `Exercise07Demo.java`, Console.

## 7. Kiểm thử

`Exercise06Test` (8 JUnit cases): giữ nguyên interface, thứ tự, lồng nhau,
giới hạn 2–5, iterator không sửa list, decorator, deep copy, thời gian.

`Exercise07Test` (7 JUnit cases): interface Iterable, leaf rỗng, composite,
decorator ủy quyền iterator, giới hạn số lượng, deep copy.

Ngoài ra, `VerificationRunner` chạy **7 phép kiểm tự động**, không cần JUnit/Maven.

**Vị trí ảnh:** chụp Eclipse JUnit result và `VerificationRunner` hiện `7 PASSED, 0 FAILED`.

## 8. Đối chiếu yêu cầu / kết luận

| Yêu cầu | Exercise 6 | Exercise 7 |
|---|---|---|
| Duyệt các phần tử trong composite | Có | Có |
| Giữ nguyên Show | Có | Không |
| Client duyệt bằng biến kiểu Show | Không | Có |
| Dùng Iterator | Có | Có |
| Hỗ trợ composite lồng nhau qua đệ quy | Có | Có |

Kết luận: Exercise 6 tránh thay đổi hợp đồng interface nhưng khách hàng phải biết
`CompositeShow`; Exercise 7 hỗ trợ duyệt đa hình trên `Show` nhưng phải mở rộng API chung.

## 9. Tài liệu tham khảo

Martin P. Robillard, *Introduction to Software Design with Java*, DesignBook,
Chapter 6 Exercises 1–7: https://github.com/prmr/DesignBook/blob/master/exercises/e-chapter6.md .
