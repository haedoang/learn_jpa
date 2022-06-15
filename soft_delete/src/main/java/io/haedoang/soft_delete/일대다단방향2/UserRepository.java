package io.haedoang.soft_delete.일대다단방향2;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : haedoang
 * date : 2022/06/07
 * description :
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
