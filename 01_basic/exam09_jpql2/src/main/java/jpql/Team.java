package jpql;

import org.hibernate.annotations.BatchSize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : jpql
 * fileName : Team
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();


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