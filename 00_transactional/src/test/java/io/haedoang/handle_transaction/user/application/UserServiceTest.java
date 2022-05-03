package io.haedoang.handle_transaction.user.application;

import io.haedoang.handle_transaction.user.domain.User;
import io.haedoang.handle_transaction.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.haedoang.handle_transaction.user.fixture.UserFixture.user1;
import static io.haedoang.handle_transaction.user.fixture.UserFixture.user2;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : io.haedoang.handle_transaction.user.application
 * fileName : UserServiceTest
 * author : haedoang
 * date : 2022-04-04
 * description :
 */
@SpringBootTest
//@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        //userService.save(user1);
        //userService.save(user2);
    }

    @Test
    @DisplayName("save user")
    public void saveUser() {
        // given
        User user = User.valueOf("해동", 34);

        // when
        User savedUser = userService.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
    }


    @Test
    @DisplayName("")
    public void update() {
        // given
        userService.update(new User(1L, "updateName2", 0));
    }

    @Test
    @DisplayName("updateUser")
    public void updateUser() throws InterruptedException {
        Worker fastWorker = new Worker(userService, new User(1L, "A", 30), true);
        Worker slowWorker = new Worker(userService, new User(1L, "B", 30), false);
        Thread slowThread = new Thread(slowWorker);
        Thread fastThread = new Thread(fastWorker);

        slowThread.start();
        fastThread.start();

    }


    @Test
    @DisplayName("")
    public void deleteTest() {
        // given
        userRepository.deleteByName("hi");

        // when

        // then
    }

}

class Worker implements Runnable {
    private final UserService userService;
    private final User user;
    private final boolean flag;

    public Worker(UserService userService, User user, boolean flag) {
        this.userService = userService;
        this.user = user;
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println("run!!" );
        if (flag) {
            userService.update(user);
            return;
        }
        try {
            userService.slowUpdate(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
