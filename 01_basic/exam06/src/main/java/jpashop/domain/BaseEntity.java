package jpashop.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * packageName : jpashop.domain
 * fileName : BaseEntity
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@MappedSuperclass
public abstract class BaseEntity {

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;


}
