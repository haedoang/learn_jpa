package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName : jpabook.jpashop
 * fileName : Member
 * author : haedoang
 * date : 2021/11/17
 * description :
 */
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String username;



}
