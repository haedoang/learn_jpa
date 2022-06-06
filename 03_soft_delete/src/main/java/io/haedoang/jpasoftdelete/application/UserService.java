package io.haedoang.jpasoftdelete.application;

import io.haedoang.jpasoftdelete.domain.User;
import io.haedoang.jpasoftdelete.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * author : haedoang
 * date : 2022/06/07
 * description :
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EntityManager em;


    /**
     * 권한에 따라 조회 조건을 부여하기
     * @param isDeleted
     * @return
     */
    List<User> findUserAllByDeleted(boolean isDeleted) {
        Session session = em.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProductFilter");
        filter.setParameter("isDeleted", isDeleted);
        final List<User> users = userRepository.findAll();
        session.disableFilter("deletedProductFilter");

        return users;
    }
}
