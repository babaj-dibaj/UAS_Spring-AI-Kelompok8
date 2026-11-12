package com.kelompok8.springai.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record LoanRequest(@NotNull Long bookId, @NotNull Long studentId, @NotNull @FutureOrPresent LocalDate dueDate) {}
