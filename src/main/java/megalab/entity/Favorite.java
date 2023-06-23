package megalab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
@Entity
@Table(name = "favorites")
@Setter
@Getter
@NoArgsConstructor
@Builder
public class Favorite {
    @Id
    @GeneratedValue(generator = "favorite_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "favorite_gen",sequenceName = "favorite_seq",allocationSize = 1)
    private Long id;
    private ZonedDateTime createDate;

    public Favorite(ZonedDateTime createDate) {
        this.createDate = createDate;
    }
}
