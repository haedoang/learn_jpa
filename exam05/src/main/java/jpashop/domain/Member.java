package jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * packageName : jpashop.domain
 * fileName : Member
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @ManyToOne
    private Team team;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {

        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
