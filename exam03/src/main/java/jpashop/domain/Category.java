package jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : jpashop.domain
 * fileName : Category
 * author : haedoang
 * date : 2021/11/09
 * description :
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;  //상위 카테고리

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); //자식 카테고리

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();
}
