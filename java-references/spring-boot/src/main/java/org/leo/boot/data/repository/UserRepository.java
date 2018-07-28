package org.leo.boot.data.repository;

import org.leo.boot.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository
 * @author fahdessid
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {
  
  User findByEmail(String email);
}
