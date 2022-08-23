package io.haedoang.transactional.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
