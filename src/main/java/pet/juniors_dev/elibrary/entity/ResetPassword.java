package pet.juniors_dev.elibrary.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "password_reset_tokens")
public class ResetPassword {
    private static final long TOKEN_EXPIRATION_IN_MINUTES = 1440;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Transient
    public static long getExpirationInMinutes() {
        return TOKEN_EXPIRATION_IN_MINUTES;
    }
}
