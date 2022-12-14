package pet.juniors_dev.elibrary.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Genre> genres = new HashSet<>();

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "book_url")
    private String bookUrl;

    @Column(name = "book_download_url")
    private String bookDownloadUrl;

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Column(name = "count_of_reviews")
    private Integer countOfReviews;

    @ManyToOne
    private User creator;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
