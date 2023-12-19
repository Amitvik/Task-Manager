package com.cs790.app.controller;

import com.cs790.app.model.User;
import com.cs790.app.model.dto.UserDto;
import com.cs790.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok().body(userService.createUser(userDto));
    }

   /*@GetMapping("/register")
   public String showRegistrationForm(Model model) {
       model.addAttribute("user", new User());

       return "signup_form";
   }*/

    @PostMapping("/process_register")
    public String processRegister(User user) {
        String message = userService.processRegister(user);

        return message;
    }

    @PostMapping("/login")
    public ResponseEntity<User> processLogin(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.processLogin(email, password);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUsers() {
        try {
               userService.deleteUsers();
            return ResponseEntity.ok(" users deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
