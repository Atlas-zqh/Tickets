package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.SysRole;
import com.keenan.Tickets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author keenan on 09/02/2018
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstById(Long id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    List<User> findUsersByRole(SysRole role);

}
