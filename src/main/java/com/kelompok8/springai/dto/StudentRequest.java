package com.kelompok8.springai.dto;
import jakarta.validation.constraints.NotBlank;
public record StudentRequest(@NotBlank String name, @NotBlank String studentNumber, @NotBlank String studyProgram) {}
