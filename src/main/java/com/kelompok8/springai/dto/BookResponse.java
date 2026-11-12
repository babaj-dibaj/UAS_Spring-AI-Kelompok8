package com.kelompok8.springai.dto;
import com.kelompok8.springai.model.Book;
public record BookResponse(Long id, String title, String author, String category, String isbn, String synopsis, int totalStock, int availableStock) {
    public static BookResponse from(Book b) { return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getCategory(), b.getIsbn(), b.getSynopsis(), b.getTotalStock(), b.getAvailableStock()); }
}
