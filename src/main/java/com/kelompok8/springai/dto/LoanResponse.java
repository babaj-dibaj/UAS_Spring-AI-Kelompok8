package com.kelompok8.springai.dto;
import com.kelompok8.springai.model.*;
import java.time.LocalDate;
public record LoanResponse(Long id, Long bookId, String bookTitle, Long studentId, String studentName, LocalDate loanDate, LocalDate dueDate, LocalDate returnedDate, LoanStatus status) {
    public static LoanResponse from(Loan l) { return new LoanResponse(l.getId(), l.getBook().getId(), l.getBook().getTitle(), l.getStudent().getId(), l.getStudent().getName(), l.getLoanDate(), l.getDueDate(), l.getReturnedDate(), l.getStatus()); }
}
