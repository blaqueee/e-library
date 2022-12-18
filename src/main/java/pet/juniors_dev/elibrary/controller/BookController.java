package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.service.BookService;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @ModelAttribute BookRequest book,
                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookService.addBook(book, user));
    }
}
