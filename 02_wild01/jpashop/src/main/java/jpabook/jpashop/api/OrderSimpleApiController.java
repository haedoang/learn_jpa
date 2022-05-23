package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import jpabook.jpashop.repository.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * packageName : jpabook.jpashop.api
 * fileName : OrderSimpleApiController
 * author : haedoang
 * date : 2021-12-03
 * description : xToOne(ManyToOne, OneToOne)최적화
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 양방향 관계에서는 순환 참조에 의한 무한루프가 발생할 수 있음
     *
     * @return
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //강제 초기화하기
        for (Order order : all) {
            order.getMember().getName(); //member 초기화 
            order.getDelivery().getAddress();  //delivery 초기화
        }
        return all;
    }

    /**
     * n + 1 문제가 발생.
     *
     * @return
     */
    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> ordersV2() {
        return orderRepository.findAllByString(new OrderSearch())
                .stream()
                .map(OrderSimpleQueryDto::new)
                .collect(toList());
    }

    /**
     * fetch join
     *
     * @return
     */
    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(OrderSimpleQueryDto::new)
                .collect(toList());
    }

    /**
     * select 절을 최적화 할 수 있음. 재사용성이 떨어짐
     *
     * @return
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }
}
