package jpashop.domain.old;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : jpashop.domain
 * fileName : Member
 * author : haedoang
 * date : 2021-11-09
 * description :
 */
//@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")  //spring boot는 camelCase를 db에 underscore로 변환해준다.(기본설정)
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    /**
    ########## 일대다 단방향 테스트
    @OneToMany
    @JoinColumn(name = "MEMBER_ID")
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    **/

    /** 주테이블에 외래키 단뱡향
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
     */



    //양방향 매핑
//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
