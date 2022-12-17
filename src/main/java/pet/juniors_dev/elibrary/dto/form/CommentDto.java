package pet.juniors_dev.elibrary.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.User;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @NotEmpty
    private String comment;

    private LocalDateTime createdAt;

    @NotNull
    private User user;

    @NotNull
    private Book book;
}
