package pet.juniors_dev.elibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("book")
    private BookDto book;

    @JsonProperty("user")
    private UserDto user;
}
