package org.leo.boot.data.service;

import java.util.Arrays;
import java.util.HashSet;

import org.leo.boot.data.model.Role;
import org.leo.boot.data.model.User;
import org.leo.boot.data.repository.RoleRepository;
import org.leo.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User service implementation
 * @author fahdessid
 */

public class UserServiceImpl implements UserService {

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    user.setActive(1);
    
    Role userRole = roleRepository.findByRole("ADMIN");
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    
    userRepository.save(user);
  }

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private RoleRepository roleRepository;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
}
