package com.example.stockify.Controller;

import com.example.stockify.Model.User;
import com.example.stockify.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173",allowCredentials ="true")
public class HomeController {


  @Autowired
  private UserService userService;

  @Autowired
  private HttpSession httpSession;

  @GetMapping("/")
  public String home(){
    return "hello world from spring";
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody User user) {

    ResponseEntity<String> output = userService.saveUser(user);

    return output;
  }

  @DeleteMapping("/users/{username}")
  public ResponseEntity<String> deleteUser(@PathVariable("username") String username){
    ResponseEntity<String> output = userService.deleteUser(username);

    return output;
  }


  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
    String username = credentials.get("username");
    String password = credentials.get("password");
    User user = userService.loginAuthentication(username,password);
    if(user!=null){
      httpSession.setAttribute("user",user);
      return ResponseEntity.ok("Login Successful");
    }else{
      return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("Username or password is incorrect");
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    httpSession.invalidate();
    return ResponseEntity.status(200).body("User logged out");
  }

  @GetMapping("/currentUser")
  public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      User user = (User) session.getAttribute("user");
      return ResponseEntity.ok(user.getUsername());
    } else {
      return ResponseEntity.status(404).body("Not logged in");
    }
  }




}
