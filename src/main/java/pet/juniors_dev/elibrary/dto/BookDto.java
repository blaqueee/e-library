package pet.juniors_dev.elibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("genres")
    private Set<GenreDto> genres;

    @JsonProperty("author")
    private String author;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("book_url")
    private String bookUrl;

    @JsonProperty("book_download_url")
    private String bookDownloadUrl;

    @JsonProperty("rating")
    private BigDecimal rating;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("creator")
    private UserDto creator;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
