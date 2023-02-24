package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepository {

    Role getRole(String name);

    Role getRoleByName(String name);

    List<Role> listAllRoles();

}
