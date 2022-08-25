package io.haedoang.propagation.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.*;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@ToString
@Entity
@Table(name = "tb_child")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long parentId;

    public Child(Long parentId) {
        this.parentId = parentId;
    }
}
