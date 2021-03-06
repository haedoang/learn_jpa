package jpabook.jpashop.domain;

import jpabook.jpashop.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

/**
 * packageName : jpabook.jpashop.domain
 * fileName : Category
 * author : haedoang
 * date : 2021/11/21
 * description :
 */
@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //연관 관계 편의 메서드
    public void addChildCategory(Category category) {
        this.child.add(category);
        category.setParent(this);
    }
}
