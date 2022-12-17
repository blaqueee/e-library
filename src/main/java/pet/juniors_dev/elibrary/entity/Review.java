package pet.juniors_dev.elibrary.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotEmpty
    private String review;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Min(value = 1)
    @Max(value = 10)
    private int rating;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

}
