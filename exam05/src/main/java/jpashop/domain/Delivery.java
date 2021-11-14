package jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * packageName : jpashop.domain
 * fileName : Delivery
 * author : haedoang
 * date : 2021/11/14
 * description :
 */
@Entity
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = LAZY, mappedBy = "delivery")
    private Order order;

    private String city;

    private String street;

    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
