package pet.juniors_dev.elibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String authors;

    @NotEmpty
    private String language;

    @NotEmpty
    private int year;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private List<Comment> comments = new java.util.ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "downloaded_user_id")
    private User downloadedUser;



//    private String urlOnServer;
}
