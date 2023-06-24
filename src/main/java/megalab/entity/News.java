package megalab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@Setter
@Getter
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(generator = "news_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "news_gen",sequenceName = "news_seq",allocationSize = 1,initialValue = 5)
    private Long id;
    private String name;
    private String image;
    private String description;
    private String text;
    private ZonedDateTime createDate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User user;

    @OneToMany(mappedBy = "news",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Comment> comments;

    @OneToMany(mappedBy = "news",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Favorite> favorites;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Category>categories;


@Builder
    public News(String name, String image, String description, String text, ZonedDateTime createDate) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.text = text;
        this.createDate = createDate;
    }
}
