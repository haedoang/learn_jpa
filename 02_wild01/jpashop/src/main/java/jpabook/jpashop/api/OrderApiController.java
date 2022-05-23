package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  권장 순서
 *  1. 엔티티로 조회
 *   1.1 페치조인으로 쿼리 수 최적화
 *   1.2 컬렉션 최적화
 *    1.2.1 페이징 필요 hibernate.default_batch_fetch_size, @BatchSize
 *    1.2.2 페이징 필요x fetch 조인
 *
 *  2. 엔티티 조회 방식으로 해결 안되면 DTO 조회 방식 사용
 *  3. DTO 조회 방식으로 해결안되면 NativeSQL or 스프링 jdbcTemplate
 *
 */

/**
 * packageName : jpabook.jpashop.api
 * fileName : OrderApiController
 * author : haedoang
 * date : 2022-05-23
 * description :
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     *
     * 1) 엔티티를 조회해서 그대로 반환하기
     *
     * 엔티티 스펙이 외부로 노출된다
     * @return
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            // orderItem proxy 강제 초기화
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(it -> it.getItem().getName());
        }

        return all;
    }

    /**
     * 2) 엔티티 조회 후 DTO로 변환
     *
     * 엔티티 스펙을 외부로 노출하지 말자 => dto 사용
     * N + 1 이슈 발생
     * @return
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2() {
        return orderRepository.findAllByString(new OrderSearch())
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * 3) fetch 조인으로 쿼리 수 최적화
     *
     * collection 객체를 fetch join 할 경우
     * 컬렉션 내 개수만큼 N + 1 이슈가 발생함
     * => distinct fetch join 최적화 (1대다 조인 처리)
     * => 애플리케이션에서 중복을 걸러준다
     *
     * => 단점 :: 페이징 불가능
     * 하이버네이트는 모든 데이터를 DB에서 읽어오고 메모리에서 페이징한다 (매우 위험함)
     * => 컬렉션 페치조인은 1개만 사용 가능
     * @return
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3() {
        return orderRepository.findAllWithItem()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * 3.1) 컬렉션 페이징과 한계 돌파
     *
     * 컬렉션 페치조인은 페이징이 불가능하다
     * => 페이징 처리를 위한 처리
     * hibernate.default_batch_fetch_size 옵션 ( ~ 1000)
     * => SQL in 절 전략. 옵션 값이 클수록 DB에 부하를 줄 수 있음
     * => 메모리 사용량은 같다
     *
     * 1) toOne 오브젝트는 fetch 조인으로 가져온다
     * 2) 나머지(toMany)부분은 hibernate.default_batch_fetch_size 로 최적화한다
     *
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "limit", defaultValue = "100") int limit ) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        return orders.stream()
                .map(OrderDto::new).collect(Collectors.toList());
    }

    /**
     *
     * 4) DTO에서 직접 조회하기
     *
     * Dto 조회하기
     * => n+1 이슈 발생
     * @return
     */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    /**
     * 5) 컬렉션 조인 최적화하기:: 일대다 관계 컬렉션을 IN 절을 활용해 메모리에 조회하여 최적화
     *
     * N + 1 문제를 in query로 해결 하기
     * orderItem 조회 시 in query로 조회하여 메모리에 적재한 뒤 파싱한다
     *
     * @return
     */
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    /**
     *
     * 6) JOIN 결과를 그대로 조회 후 애플리케이션에서 원하는 모양으로 직접 변환하기
     *
     * order, orderItem 을 한번에 조회하기
     * -> 중복데이터가 발생할 시 성능 문제가 발생할 수 있다
     * -> api spec 으로 변환하는 작업이 추가로 필요하다
     * -> 페이징 처리가 불가능하다 (orderItem 즉, 하위 데이터가 기준이 됨)
     * @return
     */
    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> ordersV6() {
        return orderQueryRepository.findAllByDto_flat();
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        //private List<OrderItem> orderItems;

        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            //order.getOrderItems().forEach(it -> it.getItem().getName());
            //orderItems = order.getOrderItems();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    public static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
