package megalab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
@Entity
@Table(name = "comments")
@Setter
@Getter
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(generator = "comments_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comments_gen",sequenceName = "comments_seq",allocationSize = 1)
    private Long id;
    private String text;
    private ZonedDateTime createDate;
    private ZonedDateTime updatedDate;

    public Comment(String text, ZonedDateTime createDate, ZonedDateTime updatedDate) {
        this.text = text;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }
}
