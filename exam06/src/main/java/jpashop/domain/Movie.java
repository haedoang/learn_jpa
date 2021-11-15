package jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName : jpashop.domain
 * fileName : Movie
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@DiscriminatorValue("M")
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
