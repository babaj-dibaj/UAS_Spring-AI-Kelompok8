# Sistem Informasi Perpustakaan - Spring AI
---

**Mata Kuliah:** Rekayasa Perangkat Lunak Lanjut  
**Kelompok:** 8  
**Program Studi:** Teknik Informatika

---
## Anggota Kelompok

| No. | Nama Anggota | NIM |
|:---:|---| --- |
| 1 | Shofwatunnisa | 12409011050121 |
| 2 | Siti Dibaj Trikusuma | 12409011050117 |
| 3 | Rusmin Nuryadin | 12409011050120 |

---

Aplikasi web untuk mengelola buku, mahasiswa, peminjaman, dan pengembalian buku. Aplikasi ini dibuat menggunakan Spring Boot, Spring AI, Hibernate/JPA, MySQL, dan Ollama sebagai AI lokal.

## Fitur Utama

- Mengelola katalog buku:
  - Judul, penulis, kategori, ISBN, sinopsis, dan stok
  - Pencarian berdasarkan judul, penulis, atau kategori
- Mengelola data mahasiswa:
  - Nama, NIM, dan program studi
- Mengelola transaksi peminjaman dan pengembalian:
  - Validasi ketersediaan stok
  - Stok berkurang saat buku dipinjam
  - Stok bertambah saat buku dikembalikan
  - Status transaksi `BORROWED` atau `RETURNED`
- Fitur Spring AI melalui Ollama:
  - Rekomendasi buku berdasarkan kebutuhan pemustaka
  - Ringkasan sinopsis buku
- Hibernate/JPA untuk ORM dan relasi database
- Dashboard web responsif dengan tema marun, cokelat soft, dan cream

## Teknologi

| Komponen | Teknologi |
|---|---|
| Bahasa | Java 17+ |
| Backend | Spring Boot 4.1.2 |
| AI integration | Spring AI 2.0.1 |
| Model AI | Ollama `llama3.2` |
| Database | MySQL |
| ORM | Hibernate/JPA |
| Frontend | HTML, CSS, JavaScript |
| Build tool | Maven Wrapper |

## Prasyarat

Pastikan software berikut sudah terpasang:

1. Java 17 atau lebih baru.
2. MySQL Server atau XAMPP.
3. Ollama.
4. IntelliJ IDEA (opsional, tetapi direkomendasikan untuk menjalankan konfigurasi project).

## Persiapan Ollama

Pastikan Ollama sedang berjalan, kemudian cek atau unduh model:

```text
ollama pull llama3.2
ollama run llama3.2
```

Ollama secara default berjalan pada:

```text
http://localhost:11434
```

Untuk mengecek model yang tersedia:

```text
ollama list
```

Tidak diperlukan OpenAI API key atau billing OpenAI. Proses AI berjalan secara lokal melalui Ollama.

## Persiapan Database MySQL

Buat database MySQL melalui phpMyAdmin atau MySQL client. Nama database yang digunakan oleh konfigurasi project adalah `spring ai`.

```sql
CREATE DATABASE `spring ai`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

Jika database sudah ada, tidak perlu membuatnya lagi.

### Konfigurasi Database

Konfigurasi MySQL berada di:

```text
src/main/resources/application-mysql.properties
```

Konfigurasi default:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring%20ai?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

Jika MySQL memiliki password, isi environment variable `DB_PASSWORD` di Run Configuration IntelliJ. Jangan commit password ke repository.

Hibernate menggunakan:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Jadi tabel akan dibuat otomatis saat aplikasi pertama kali berhasil terhubung ke MySQL. Tidak perlu membuat tabel secara manual di phpMyAdmin.

## Menjalankan dari IntelliJ IDEA

1. Buka project ini di IntelliJ IDEA.
2. Pastikan MySQL dan Ollama sedang aktif.
3. Buka `Run > Edit Configurations`.
4. Pilih konfigurasi `AiStudyMentor`.
5. Pastikan active profile bernilai `mysql`.
6. Jika MySQL memakai password, tambahkan environment variable:

```text
DB_PASSWORD=password_mysql_anda
```

7. Klik **Run**.
8. Buka browser:

```text
http://localhost:8080
```

Konfigurasi IntelliJ tersedia di:

```text
.idea/runConfigurations/AiStudyMentor.xml
```

## Menjalankan dari Terminal

Windows:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

Linux/macOS:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

Jika menggunakan environment variable dari terminal Windows PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="password_mysql_anda"
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

## Endpoint REST

| Method | Endpoint | Fungsi |
|---|---|---|
| GET | `/api/health` | Mengecek status aplikasi |
| GET | `/api/books` | Menampilkan semua buku |
| GET | `/api/books?q=java` | Mencari buku |
| POST | `/api/books` | Menambah buku |
| PUT | `/api/books/{id}` | Mengubah buku |
| DELETE | `/api/books/{id}` | Menghapus buku |
| GET | `/api/students` | Menampilkan mahasiswa |
| POST | `/api/students` | Menambah mahasiswa |
| GET | `/api/loans` | Menampilkan transaksi peminjaman |
| POST | `/api/loans` | Membuat peminjaman |
| POST | `/api/loans/{id}/return` | Mengembalikan buku |
| POST | `/api/ai/recommend` | Mendapatkan rekomendasi buku dari AI |
| POST | `/api/ai/books/{id}/summary` | Membuat ringkasan buku |

Contoh request rekomendasi AI:

```http
POST /api/ai/recommend
Content-Type: application/json
```

```json
{
  "request": "Saya ingin belajar database dari dasar"
}
```

Contoh request tambah buku:

```json
{
  "title": "Spring Boot di Dunia Nyata",
  "author": "Andi Wijaya",
  "category": "Web Development",
  "isbn": "978-003",
  "synopsis": "Membangun REST API, dependency injection, JPA, dan deployment dengan Spring Boot.",
  "totalStock": 2
}
```

## Struktur Database

Hibernate membuat tiga entity utama:

```text
BOOK
├── id
├── title
├── author
├── category
├── isbn
├── synopsis
├── total_stock
└── available_stock

STUDENT
├── id
├── name
├── student_number
└── study_program

LOAN
├── id
├── book_id
├── student_id
├── loan_date
├── due_date
├── returned_date
└── status
```

Relasi:

- Satu buku dapat memiliki banyak transaksi peminjaman.
- Satu mahasiswa dapat memiliki banyak transaksi peminjaman.
- Setiap transaksi peminjaman terhubung ke satu buku dan satu mahasiswa.

Dokumentasi 5W + 1H, UML, sequence diagram, ERD, dan eksperimen tersedia di:

```text
docs/PROJECT-DOCUMENTATION.md
```

## Skenario Pengujian

1. Buka tab **Katalog Buku** dan pastikan data buku tampil.
2. Gunakan pencarian dengan kata `database`.
3. Tambahkan mahasiswa baru.
4. Catat peminjaman buku.
5. Pastikan stok buku berkurang.
6. Kembalikan buku.
7. Pastikan status transaksi menjadi `RETURNED` dan stok bertambah.
8. Buka tab **AI Perpustakaan**.
9. Masukkan kebutuhan, misalnya `Saya ingin belajar Python dari dasar`.
10. Pastikan rekomendasi dari Ollama tampil.
11. Klik **Ringkas dengan AI** pada salah satu buku.

## Troubleshooting

### Halaman tidak dapat dibuka

Pastikan aplikasi berjalan dan port `8080` tidak sedang digunakan proses lain. Buka:

```text
http://localhost:8080
```

### Database gagal terhubung

Periksa:

- MySQL/XAMPP sedang aktif.
- Nama database adalah `spring ai`.
- Username dan password MySQL benar.
- Port MySQL adalah `3306`.
- Active profile IntelliJ adalah `mysql`.

### Tabel belum muncul di phpMyAdmin

Jalankan aplikasi sampai startup selesai, lalu refresh database `spring ai` di phpMyAdmin. Tabel dibuat oleh Hibernate ketika koneksi berhasil.

### AI tidak memberikan jawaban

Pastikan Ollama berjalan dan model tersedia:

```text
ollama list
ollama run llama3.2
```

Kemudian restart aplikasi Spring Boot.

### Error `OPENAI_API_KEY`

Project ini sudah menggunakan Ollama lokal. Tidak perlu mengisi `OPENAI_API_KEY`. Pastikan konfigurasi yang dijalankan adalah versi project terbaru dan profile `mysql` aktif.

## Struktur Project

```text
src/main/java/com/kelompok8/springai
├── config
│   ├── AiConfiguration.java
│   └── DataSeeder.java
├── controller
│   ├── ApiExceptionHandler.java
│   ├── LibraryController.java
│   └── PageController.java
├── dto
├── model
│   ├── Book.java
│   ├── Loan.java
│   ├── LoanStatus.java
│   └── Student.java
├── repository
├── service
│   ├── LibraryAiService.java
│   └── LibraryService.java
└── SpringAiKelompok8Application.java

src/main/resources
├── application.properties
├── application-dev.properties
├── application-mysql.properties
├── application-test.properties
└── static/index.html
```

## Catatan Keamanan

- Jangan commit password MySQL ke GitHub.
- Jangan commit API key atau token rahasia apa pun.
- Gunakan environment variable untuk credential.
- Model Ollama berjalan lokal dan tidak membutuhkan API key OpenAI.

## Konteks Penugasan UAS

Project ini dibuat untuk memenuhi penugasan Spring AI dengan:

- Tulisan 5W + 1H.
- Aplikasi Spring AI dengan studi kasus bebas.
- Dokumentasi eksperimen atau percobaan.
- Dokumentasi UML dan sequence diagram.
- Hibernate/JPA sebagai ORM.
- ERD/database model.
- Dokumentasi konfigurasi dan cara menjalankan aplikasi.

