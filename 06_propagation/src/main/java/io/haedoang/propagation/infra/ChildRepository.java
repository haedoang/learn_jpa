package io.haedoang.propagation.infra;

import io.haedoang.propagation.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
public interface ChildRepository extends JpaRepository<Child, Long> {
}
