package jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName : jpashop.domain
 * fileName : Delivery
 * author : haedoang
 * date : 2021/11/09
 * description :
 */
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    private String city;
    private String zipcode;
    private String street;
    private DeliveryStatus status;

}
