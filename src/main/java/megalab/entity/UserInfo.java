package megalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "userInfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userInfo_gen")
    @SequenceGenerator(name = "userInfo_gen",sequenceName = "userInfo_seq",allocationSize = 1)
    private Long id;
    private String nickName;
    private String password;
    private String email;
    private String image;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
}
