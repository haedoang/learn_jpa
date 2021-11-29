package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName : jpabook.jpashop.controller
 * fileName : BookForm
 * author : haedoang
 * date : 2021-11-29
 * description :
 */
@Getter @Setter
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;



}
