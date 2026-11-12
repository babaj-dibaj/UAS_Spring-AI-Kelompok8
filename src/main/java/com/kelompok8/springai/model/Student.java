package com.kelompok8.springai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, length = 120)
    private String name;
    @NotBlank @Column(nullable = false, unique = true, length = 30)
    private String studentNumber;
    @NotBlank @Column(nullable = false, length = 120)
    private String studyProgram;
    protected Student() {}
    public Student(String name, String studentNumber, String studyProgram) {
        this.name = name; this.studentNumber = studentNumber; this.studyProgram = studyProgram;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getStudentNumber() { return studentNumber; }
    public String getStudyProgram() { return studyProgram; }
}
