package jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * packageName : jpashop.domain
 * fileName : Delivery
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@Entity
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery" ,fetch = LAZY)
    private Order order;
}
