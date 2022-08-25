package io.haedoang.propagation.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@ToString
@Getter
@Entity
@Table(name = "tb_parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public Parent() {
    }
}
