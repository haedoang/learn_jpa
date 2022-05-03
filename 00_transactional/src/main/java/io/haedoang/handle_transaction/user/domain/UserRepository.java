package io.haedoang.handle_transaction.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName : io.haedoang.handle_transaction.user.domain
 * fileName : UserRepository
 * author : haedoang
 * date : 2022-04-04
 * description :
 */
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteByName(String name);

}
