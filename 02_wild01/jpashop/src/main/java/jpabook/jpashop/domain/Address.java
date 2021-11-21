package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * packageName : jpabook.jpashop.domain
 * fileName : Address
 * author : haedoang
 * date : 2021/11/21
 * description : 값 타입은 생성자로만 값을 넣을 수 있게 해야 한다. 변경이 되어선 안 됨.
 *               protected 접근 제한자: 같은 패키지 또는 자식 클래스에서 사용
 */
@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
