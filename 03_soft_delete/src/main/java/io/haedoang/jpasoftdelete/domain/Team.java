package io.haedoang.jpasoftdelete.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * fileName : Team
 * author : haedoang
 * date : 2022-06-07
 * description :
 */
@Entity
@Getter
@Table(name = "tb_team")
@SQLDelete(sql = "UPDATE tb_team SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Team(String name) {
        this.name = name;
    }

    public static Team valueOf(String name) {
        return new Team(name);
    }

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.addTeam(this);
    }
}
