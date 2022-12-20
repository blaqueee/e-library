package pet.juniors_dev.elibrary.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Genres")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
