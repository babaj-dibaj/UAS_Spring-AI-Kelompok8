package com.kelompok8.springai.dto;
import jakarta.validation.constraints.*;
public record BookRequest(@NotBlank String title, @NotBlank String author, @NotBlank String category,
                          @NotBlank String isbn, @NotBlank String synopsis, @Min(0) int totalStock) {}
