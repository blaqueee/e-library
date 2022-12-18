package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.BookmarkDto;
import pet.juniors_dev.elibrary.dto.form.BookmarkRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.service.BookmarkService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<BookmarkDto> addBook(@Valid @RequestBody BookmarkRequest bookmarkRequest,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookmarkService.addBookmark(bookmarkRequest, user));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam(name = "id") Long id){
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.ok("ok");
    }

    @GetMapping()
    public ResponseEntity<Page<BookDto>> getBookmarks(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                                      @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookmarkService.getBookmarksByUser(user, pageable));
    }
}
