package com.kelompok8.springai.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private Book book;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private Student student;
    @Column(nullable = false) private LocalDate loanDate;
    @Column(nullable = false) private LocalDate dueDate;
    private LocalDate returnedDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private LoanStatus status;
    protected Loan() {}
    public Loan(Book book, Student student, LocalDate dueDate) {
        this.book = book; this.student = student; this.loanDate = LocalDate.now();
        this.dueDate = dueDate; this.status = LoanStatus.BORROWED;
    }
    public Long getId() { return id; }
    public Book getBook() { return book; }
    public Student getStudent() { return student; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public LoanStatus getStatus() { return status; }
    public void returnBook() { if (status == LoanStatus.RETURNED) throw new IllegalStateException("Buku sudah dikembalikan."); status = LoanStatus.RETURNED; returnedDate = LocalDate.now(); }
}
