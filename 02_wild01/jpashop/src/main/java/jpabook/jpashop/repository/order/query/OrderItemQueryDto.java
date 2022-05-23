package jpabook.jpashop.repository.order.query;

import lombok.Data;

/**
 * packageName : jpabook.jpashop.repository.order.query
 * fileName : OrderItemQueryDto
 * author : haedoang
 * date : 2022-05-23
 * description :
 */
@Data
public class OrderItemQueryDto {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
