package jpashop.domain.old;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * packageName : jpashop.domain
 * fileName : Locker
 * author : haedoang
 * date : 2021/11/09
 * description :
 */
//@Entity
public class Locker {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /*** 주테이블에 외래키 양방향 mappedBy 읽기전용
    @OneToOne(mappedBy = "locker")
    private Member member;
    */

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
