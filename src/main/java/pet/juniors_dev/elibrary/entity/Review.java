package pet.juniors_dev.elibrary.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne
    private User commentator;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
