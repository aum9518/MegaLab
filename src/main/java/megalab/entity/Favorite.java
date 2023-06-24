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
public class Favorite {
    @Id
    @GeneratedValue(generator = "favorite_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "favorite_gen",sequenceName = "favorite_seq",allocationSize = 1,initialValue = 5)
    private Long id;
    private ZonedDateTime createDate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User user;

    @ManyToOne (cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private News news;

    public Favorite(ZonedDateTime createDate) {
        this.createDate = createDate;
    }
}
