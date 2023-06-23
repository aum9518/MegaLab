package megalab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_gen")
    @SequenceGenerator(name = "user_gen",sequenceName = "user_seq",allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String image;
    private boolean isBlock;
}
