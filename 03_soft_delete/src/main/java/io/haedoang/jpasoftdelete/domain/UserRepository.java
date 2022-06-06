package io.haedoang.jpasoftdelete.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : haedoang
 * date : 2022/06/07
 * description :
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
