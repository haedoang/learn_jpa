package jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName : jpashop.domain
 * fileName : Album
 * author : haedoang
 * date : 2021/11/10
 * description :
 */
@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
}
