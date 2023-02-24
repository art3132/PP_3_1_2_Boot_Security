package ru.kata.spring.boot_security.demo.repositories;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUser(Long id);
    void deleteUser(Long id);
    User findByUsername(String username);
}
