package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/top")
    public ResponseEntity<List<BookDto>> getTop() {
        return ResponseEntity.ok(bookService.getTop());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> findByNameAndAuthor(@RequestParam(name = "value") String value) {
        return ResponseEntity.ok(bookService.findByNameAndAuthor(value));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BookDto>> getByGenre(@RequestParam(name = "genre") String genre) {
        return ResponseEntity.ok(bookService.getByGenre(genre));
    }

    @GetMapping("/new")
    public ResponseEntity<List<BookDto>> getNewBooks() {
        return ResponseEntity.ok(bookService.findNewest());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBook(@RequestParam("id") Long bookId,
                                        @AuthenticationPrincipal User user) {
        bookService.delete(bookId, user);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @ModelAttribute BookRequest book,
                                           @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookService.addBook(book, user));
    }
}
