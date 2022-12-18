package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.service.BookService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @ModelAttribute BookRequest book,
                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookService.addBook(book, user));
    }

    @GetMapping("/top")
    public ResponseEntity<Page<BookDto>> getTop(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(bookService.getTop(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDto>> findByNameAndAuthor(@RequestParam(name = "value") String value,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(bookService.findByNameAndAuthor(value, pageable));
    }
}
