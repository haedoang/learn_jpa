package io.haedoang.soft_delete.다대일단방향;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * fileName : TeamRepository
 * author : haedoang
 * date : 2022-06-07
 * description :
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
}
