package jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * packageName : jpashop.domain
 * fileName : Orders
 * author : haedoang
 * date : 2021/11/09
 * description :
 */
@Entity
@Table(name = "Orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;

    //연관관계 매핑은 외래키가 있는쪽에 설정하자~
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    //1대1 외래키 단방향
    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

}
