package jpashop.domain.study;

import javax.persistence.*;

/**
 * packageName : jpashop.domain
 * fileName : AddressEntity
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
//@Entity
//@Table(name = "address")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

}
