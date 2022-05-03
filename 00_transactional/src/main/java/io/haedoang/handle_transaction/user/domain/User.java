package io.haedoang.handle_transaction.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName : io.haedoang.handle_transaction.user.domain
 * fileName : User
 * author : haedoang
 * date : 2022-04-04
 * description :
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;

    private User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static User valueOf(String name, int age) {
        return new User(name, age);
    }

    public void update(User user) {
        this.name = user.name;
        this.age = user.age;
    }
}
