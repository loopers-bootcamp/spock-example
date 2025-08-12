package com.loopers.spock.example.domain.user;

import com.loopers.spock.example.domain.user.attribute.Email;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByName(String name);

    List<User> findByEmail(Email email);

    User saveUser(User user);

}
