package com.kelompok8.springai.config;

import com.kelompok8.springai.model.*;
import com.kelompok8.springai.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(BookRepository books, StudentRepository students) {
        return args -> {
            if (books.count() == 0) {
                books.save(new Book("Pemrograman Java Modern", "Budi Raharjo", "Pemrograman", "978-001",
                        "Panduan konsep Java, OOP, collection, dan praktik pengembangan aplikasi.", 3));
                books.save(new Book("Basis Data untuk Pemula", "Siti Aminah", "Database", "978-002",
                        "Pengenalan SQL, desain relasional, normalisasi, dan transaksi database.", 2));
                books.save(new Book("Spring Boot di Dunia Nyata", "Andi Wijaya", "Web Development", "978-003",
                        "Membangun REST API, dependency injection, JPA, dan deployment dengan Spring Boot.", 2));
            }
            if (students.count() == 0) {
                students.save(new Student("Ahmad Fauzan", "2024001", "Informatika"));
                students.save(new Student("Dina Permata", "2024002", "Sistem Informasi"));
            }
        };
    }
}
