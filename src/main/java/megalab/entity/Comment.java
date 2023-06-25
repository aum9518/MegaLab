package megalab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Setter
@Getter
@NoArgsConstructor

public class Comment {
    @Id
    @GeneratedValue(generator = "comments_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comments_gen",sequenceName = "comments_seq",allocationSize = 1,initialValue = 5)
    private Long id;
    private String text;
    private ZonedDateTime createDate;
    private ZonedDateTime updatedDate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private News news;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;
@Builder
    public Comment(String text, ZonedDateTime createDate, ZonedDateTime updatedDate, User user, News news) {
        this.text = text;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
        this.user = user;
        this.news = news;
    }
}
