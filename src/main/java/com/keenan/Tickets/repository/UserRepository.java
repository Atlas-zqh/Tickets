package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 09/02/2018
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

}
