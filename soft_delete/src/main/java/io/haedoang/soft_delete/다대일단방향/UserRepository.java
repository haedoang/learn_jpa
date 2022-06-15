package io.haedoang.soft_delete.다대일단방향;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * author : haedoang
 * date : 2022/06/07
 * description :
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.team.id =:teamId")
    List<User> findByTeamId(Long teamId);
}
