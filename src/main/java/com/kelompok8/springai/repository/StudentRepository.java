package com.kelompok8.springai.repository;
import com.kelompok8.springai.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, Long> {}
