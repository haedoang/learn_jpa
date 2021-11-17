package jpashop.domain;

import jpashop.domain.base.BaseEntity;
import jpashop.domain.status.DeliveryStatus;

import javax.persistence.*;

/**
 * packageName : jpashop.domain
 * fileName : Delivery
 * author : haedoang
 * date : 2021/11/10
 * description :
 */
@Entity
public class Delivery extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
