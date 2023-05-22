package com.example.stockify.Service;

import com.example.stockify.Model.User;
import com.example.stockify.dao.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDAO userDAO;

  @Override
  public ResponseEntity<String> saveUser(com.example.stockify.Model.User user) {
    if (userDAO.findByEmail(user.getEmail()) != null || userDAO.findByUsername(user.getUsername()) != null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("Username or Email already Exists");
    }

    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

    // Save the user with the hashed password
    user.setPassword(hashedPassword);
    // Save the user
    userDAO.save(user);
    return ResponseEntity.ok("User Registered");
  }

  @Override
  public ResponseEntity<String> deleteUser(String user){
    User username = userDAO.findByUsername(user);
    if(username==null){
      return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("Cannot Find User");
    }

    userDAO.delete(username);
    return ResponseEntity.ok("User deleted");
  }

  @Override
  public User loginAuthentication(String username, String password){
    User user = userDAO.findByUsername(username);
    if(user!=null && BCrypt.checkpw(password, user.getPassword())){
      return user;
    }
    return null;
  }

}
