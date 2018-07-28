package org.leo.boot.data.repository;

import org.leo.boot.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role repository
 * @author fahdessid
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, String> {
  
  Role findByRole(String role);
}
