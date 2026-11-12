package com.kelompok8.springai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, length = 200)
    private String title;
    @NotBlank @Column(nullable = false, length = 150)
    private String author;
    @NotBlank @Column(nullable = false, length = 100)
    private String category;
    @NotBlank @Column(nullable = false, unique = true, length = 20)
    private String isbn;
    @NotBlank @Column(nullable = false, columnDefinition = "TEXT")
    private String synopsis;
    @Min(0) @Column(nullable = false)
    private int totalStock;
    @Min(0) @Column(nullable = false)
    private int availableStock;

    protected Book() {}
    public Book(String title, String author, String category, String isbn, String synopsis, int totalStock) {
        this.title = title; this.author = author; this.category = category; this.isbn = isbn;
        this.synopsis = synopsis; this.totalStock = totalStock; this.availableStock = totalStock;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public String getSynopsis() { return synopsis; }
    public int getTotalStock() { return totalStock; }
    public int getAvailableStock() { return availableStock; }
    public void update(String title, String author, String category, String isbn, String synopsis, int totalStock) {
        int borrowed = this.totalStock - this.availableStock;
        if (totalStock < borrowed) throw new IllegalArgumentException("Stok total tidak boleh kurang dari jumlah yang sedang dipinjam.");
        this.title = title; this.author = author; this.category = category; this.isbn = isbn;
        this.synopsis = synopsis; this.totalStock = totalStock; this.availableStock = totalStock - borrowed;
    }
    public void borrow() { if (availableStock < 1) throw new IllegalStateException("Stok buku sedang habis."); availableStock--; }
    public void giveBack() { if (availableStock < totalStock) availableStock++; }
}
