package jpabook.jpashop.service;

import jpabook.jpashop.item.Book;
import jpabook.jpashop.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : jpabook.jpashop.service
 * fileName : ItemServicee
 * author : haedoang
 * date : 2021-11-29
 * description :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    //merge 보단 dirty checking으로 사용하자.
    @Transactional
    public Item updateItem(Long itemId, Book book) {
        Item item = itemRepository.findOne(itemId);
        item.setPrice(book.getPrice());
        item.setName(book.getName());
        item.setStockQuantity(book.getStockQuantity());
        return item;
    }
}
