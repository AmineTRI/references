package org.leo.boot.data.service;

import org.leo.boot.data.model.User;

/**
 * User service for custom repository actions
 * @author fahdessid
 */
public interface UserService {

  public User findUserByEmail(String email);

  public void saveUser(User user);
}
