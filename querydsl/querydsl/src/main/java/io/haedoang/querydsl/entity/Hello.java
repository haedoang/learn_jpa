package io.haedoang.querydsl.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName : io.haedoang.querydsl.entity
 * fileName : Hello
 * author : haedoang
 * date : 2022-05-03
 * description :
 */
@Entity
@Getter @Setter
public class Hello {

    @Id
    @GeneratedValue
    private Long id;
}
