package com.example.stockify.dao;

import com.example.stockify.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
  User save(User user);
  User findByEmail(String email);
  User findByUsername(String username);
  void delete(User user);
}
