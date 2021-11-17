package jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName : jpashop.domain
 * fileName : Album
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@DiscriminatorValue("A")
@Entity
public class Album extends Item {
    private String artist;
    private String etc;
}
