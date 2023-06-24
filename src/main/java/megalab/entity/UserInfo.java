package megalab.entity;

import jakarta.persistence.*;
import lombok.*;
import megalab.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_Info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userInfo_gen")
    @SequenceGenerator(name = "userInfo_gen",sequenceName = "userInfo_seq",allocationSize = 1,initialValue = 5)
    private Long id;
    private String nickName;
    private String password;
    private String email;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User user;
@Builder
    public UserInfo(String nickName, String password, String email, ZonedDateTime createdAt, ZonedDateTime modifiedAt, Role role) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return nickName;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
