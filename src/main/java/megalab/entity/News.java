package megalab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
@Entity
@Table(name = "news")
@Setter
@Getter
@NoArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(generator = "news_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "news-gen",sequenceName = "news-seq",allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private String description;
    private String text;
    private ZonedDateTime createDate;

    public News(String name, String image, String description, String text, ZonedDateTime createDate) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.text = text;
        this.createDate = createDate;
    }
}