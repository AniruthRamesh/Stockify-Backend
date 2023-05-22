package com.example.stockify.Service;

import com.example.stockify.Model.User;

import org.springframework.http.ResponseEntity;

public interface UserService  {
  ResponseEntity<String> saveUser(User user);

  ResponseEntity<String> deleteUser(String user);

  User loginAuthentication(String username,String password);
}
