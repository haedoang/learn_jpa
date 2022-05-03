package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : jpabook.jpashop.api
 * fileName : OrderSimpleApiController
 * author : haedoang
 * date : 2021-12-03
 * description : xToOne(ManyToOne, OneToOne)최적화
 *               Order -> Member
 *               Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/sample-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //강제 초기화하기
        for(Order order : all) {
            order.getMember().getName(); //member 초기화 
            order.getDelivery().getAddress();  //delivery 초기화
        }
        return all;
    }
}
