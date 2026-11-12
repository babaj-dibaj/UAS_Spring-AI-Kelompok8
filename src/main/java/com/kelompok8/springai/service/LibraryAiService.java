package com.kelompok8.springai.service;

import com.kelompok8.springai.dto.BookResponse;
import com.kelompok8.springai.repository.BookRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class LibraryAiService {
    private final ChatClient client;
    private final BookRepository books;
    public LibraryAiService(ChatClient client, BookRepository books) {
        this.client = client; this.books = books;
    }
    public String recommend(String request) {
        List<BookResponse> catalog = books.findAll().stream().map(BookResponse::from).toList();
        return ask("""
                Kamu adalah pustakawan digital. Rekomendasikan buku hanya dari katalog berikut.
                Berikan alasan singkat dan informasikan stok jika relevan. Jawab bahasa Indonesia.
                KATALOG: %s
                KEBUTUHAN PEMUSTAKA: %s
                """.formatted(catalog, request));
    }
    public String summarize(Long bookId) {
        BookResponse book = books.findById(bookId).map(BookResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Buku tidak ditemukan."));
        return ask("Buat ringkasan sinopsis buku berikut dalam bahasa Indonesia, maksimal 5 poin:\n" + book);
    }
    private String ask(String prompt) {
        try {
            String result = client.prompt().system("Kamu membantu sistem informasi perpustakaan. Jangan mengarang data katalog.")
                    .user(prompt).call().content();
            if (result == null || result.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "AI tidak memberikan jawaban.");
            return result;
        } catch (ResponseStatusException e) { throw e; }
        catch (RuntimeException e) { throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Ollama tidak dapat memproses permintaan. Pastikan aplikasi Ollama aktif dan model llama3.2 tersedia.", e); }
    }
}
