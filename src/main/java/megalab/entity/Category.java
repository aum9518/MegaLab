package megalab.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "categories_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "categories_gen",sequenceName = "categories_seq",allocationSize = 1,initialValue = 5)
    private Long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
