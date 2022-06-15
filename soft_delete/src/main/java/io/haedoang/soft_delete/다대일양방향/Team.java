package io.haedoang.soft_delete.다대일양방향;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
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

    @OneToMany(mappedBy = "team")
    private List<User> users = new ArrayList<>();

    private Team(String name) {
        this.name = name;
    }

    public static Team valueOf(String name) {
        return new Team(name);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUsers() {
        this.users = Collections.emptyList();
    }
}
