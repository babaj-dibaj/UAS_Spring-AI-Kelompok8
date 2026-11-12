package com.kelompok8.springai.controller;

import com.kelompok8.springai.dto.*;
import com.kelompok8.springai.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class LibraryController {
    private final LibraryService library;
    private final LibraryAiService ai;
    public LibraryController(LibraryService library, LibraryAiService ai) { this.library = library; this.ai = ai; }
    @GetMapping("/health") public Map<String, String> health() { return Map.of("application", "sistem-informasi-perpustakaan", "status", "UP"); }
    @GetMapping("/books") public List<BookResponse> books(@RequestParam(required = false) @Size(max = 100) String q) { return library.books(q); }
    @PostMapping("/books") @ResponseStatus(HttpStatus.CREATED) public BookResponse createBook(@Valid @RequestBody BookRequest r) { return library.createBook(r); }
    @PutMapping("/books/{id}") public BookResponse updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest r) { return library.updateBook(id, r); }
    @DeleteMapping("/books/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteBook(@PathVariable Long id) { library.deleteBook(id); }
    @GetMapping("/students") public List<StudentResponse> students() { return library.students(); }
    @PostMapping("/students") @ResponseStatus(HttpStatus.CREATED) public StudentResponse createStudent(@Valid @RequestBody StudentRequest r) { return library.createStudent(r); }
    @GetMapping("/loans") public List<LoanResponse> loans() { return library.loans(); }
    @PostMapping("/loans") @ResponseStatus(HttpStatus.CREATED) public LoanResponse borrow(@Valid @RequestBody LoanRequest r) { return library.borrow(r); }
    @PostMapping("/loans/{id}/return") public LoanResponse returnBook(@PathVariable Long id) { return library.returnBook(id); }
    @PostMapping("/ai/recommend") public Map<String, String> recommend(@RequestBody Map<String, String> body) { return Map.of("answer", ai.recommend(body.getOrDefault("request", ""))); }
    @PostMapping("/ai/books/{id}/summary") public Map<String, String> summarize(@PathVariable Long id) { return Map.of("answer", ai.summarize(id)); }
}
