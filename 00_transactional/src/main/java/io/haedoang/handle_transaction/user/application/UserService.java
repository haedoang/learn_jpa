package io.haedoang.handle_transaction.user.application;

import io.haedoang.handle_transaction.user.domain.User;
import io.haedoang.handle_transaction.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * packageName : io.haedoang.handle_transaction.user.application
 * fileName : UserService
 * author : haedoang
 * date : 2022-04-04
 * description :
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }


    public void update(User user) {
        System.out.println("invoke");
//        User savedUser = findById(user.getId());
        User savedUser = userRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
        savedUser.update(user);
    }


    public void slowUpdate(User user) throws InterruptedException {
        System.out.println("invoke....");
        User savedUser = findById(user.getId());
//        User savedUser = userRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
        Thread.sleep(5_000);
        savedUser.update(user);
    }
}
