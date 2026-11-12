package com.kelompok8.springai.dto;
import com.kelompok8.springai.model.Student;
public record StudentResponse(Long id, String name, String studentNumber, String studyProgram) {
    public static StudentResponse from(Student s) { return new StudentResponse(s.getId(), s.getName(), s.getStudentNumber(), s.getStudyProgram()); }
}
