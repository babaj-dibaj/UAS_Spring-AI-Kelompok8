package com.kelompok8.springai.service;

import com.kelompok8.springai.dto.*;
import com.kelompok8.springai.model.*;
import com.kelompok8.springai.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class LibraryService {
    private final BookRepository books;
    private final StudentRepository students;
    private final LoanRepository loans;
    public LibraryService(BookRepository books, StudentRepository students, LoanRepository loans) {
        this.books = books; this.students = students; this.loans = loans;
    }
    @Transactional(readOnly = true)
    public List<BookResponse> books(String query) {
        List<Book> result = query == null || query.isBlank() ? books.findAll() :
                books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query, query);
        return result.stream().map(BookResponse::from).toList();
    }
    @Transactional
    public BookResponse createBook(BookRequest r) { return BookResponse.from(books.save(new Book(r.title(), r.author(), r.category(), r.isbn(), r.synopsis(), r.totalStock()))); }
    @Transactional
    public BookResponse updateBook(Long id, BookRequest r) { Book b = book(id); b.update(r.title(), r.author(), r.category(), r.isbn(), r.synopsis(), r.totalStock()); return BookResponse.from(b); }
    @Transactional
    public void deleteBook(Long id) { books.delete(book(id)); }
    @Transactional(readOnly = true)
    public List<StudentResponse> students() { return students.findAll().stream().map(StudentResponse::from).toList(); }
    @Transactional
    public StudentResponse createStudent(StudentRequest r) { return StudentResponse.from(students.save(new Student(r.name(), r.studentNumber(), r.studyProgram()))); }
    @Transactional(readOnly = true)
    public List<LoanResponse> loans() { return loans.findAllByOrderByLoanDateDesc().stream().map(LoanResponse::from).toList(); }
    @Transactional
    public LoanResponse borrow(LoanRequest r) {
        Book b = book(r.bookId()); Student s = students.findById(r.studentId()).orElseThrow(() -> notFound("Mahasiswa tidak ditemukan."));
        b.borrow(); return LoanResponse.from(loans.save(new Loan(b, s, r.dueDate())));
    }
    @Transactional
    public LoanResponse returnBook(Long id) {
        Loan loan = loans.findById(id).orElseThrow(() -> notFound("Peminjaman tidak ditemukan."));
        loan.returnBook(); loan.getBook().giveBack(); return LoanResponse.from(loan);
    }
    private Book book(Long id) { return books.findById(id).orElseThrow(() -> notFound("Buku tidak ditemukan.")); }
    private ResponseStatusException notFound(String message) { return new ResponseStatusException(HttpStatus.NOT_FOUND, message); }
}
