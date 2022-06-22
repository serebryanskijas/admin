package com.example.springthymeleafupload.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    List<User> findAll(){
        return userService.findAll();
    }

}
