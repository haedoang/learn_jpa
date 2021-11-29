package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : jpabook.jpashop.repository
 * fileName : OrderSearch
 * author : haedoang
 * date : 2021-11-29
 * description :
 */
@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
