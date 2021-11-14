package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : entity
 * fileName : Team
 * author : haedoang
 * date : 2021/11/08
 * description :
 */
@Entity
public class Team {

    @Id @GeneratedValue
    private Long id;
    private String name;

    //@OneToMany(mappedBy = "team") //연관관계의 주인이 아니야~
    @OneToMany
    @JoinColumn(name = "MEMBER_ID")
    List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
