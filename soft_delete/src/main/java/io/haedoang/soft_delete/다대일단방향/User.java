package io.haedoang.soft_delete.다대일단방향;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

/**
 * author : haedoang
 * date : 2022/06/07
 * description :
 */
@Entity
@Getter
@Table(name = "tb_user")
@SQLDelete(sql = "UPDATE tb_user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
// 권한에 따라 조건을 부여할 수 있음
//@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public static User valueOf(String name, int age) {
        return new User(name, age);
    }

    public void update(User updateUser) {
        this.name = updateUser.name;
        this.age = updateUser.age;
    }

    public void addTeam(Team team) {
        this.team = team;
    }

    public void removeTeam() {
        this.team = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
