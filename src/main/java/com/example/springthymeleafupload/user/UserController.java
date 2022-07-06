package com.example.springthymeleafupload.user;

import com.example.springthymeleafupload.role.Role;
import com.example.springthymeleafupload.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value ={"/index","/"})
    public String index() {
        return "index";
    }

    @GetMapping(value ="/users" )
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value ="/edit_user")
    public String editUser(Model model, @RequestParam(name="id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles",roleService.findAll());
        return "edit_user";
    }

    @PutMapping(value="/update_user")
    public String updateUser(User user, Model model, @RequestParam(value="role", required = true) long[] roles) {
        Set<Role> rolesNew = new HashSet<>();
        if (roles!=null){
            for (int i=0; i<roles.length; i++)
                rolesNew.add(roleService.findById(roles[i]));
        }
        User userDb = userService.findById(user.getId());
        userDb.setEnabled(user.isEnabled());
        userDb.setRoles(rolesNew);
        userService.save(userDb);
        model.addAttribute("users", userService.findAll());
        return "redirect:/users";
    }

    //Регистрация
    @GetMapping(value ="/signup")
    public String signup(Model model) {
        model.addAttribute("user",new User()); //Если не добавить, то не будет выполняться парсинг шаблона исходной страницы
        return "signup";
    }

    @PostMapping(value="/signup")
    public String saveUser(User user, Model model, HttpServletResponse response,
            @RequestParam(value="password2", required = true) String password2
    ){
        if (user==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fill all fields");
        if (userService.getUserByUsername(user.getUsername())!=null || userService.getUserByEmail(user.getEmail())!=null){
            throw new ResponseStatusException(HttpStatus.FOUND, "User with this username or email already exists");
        }
        if(!user.getPassword().equals(password2)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Passwords do not match");
        }
        Set<Role> rolesNew = new HashSet<>();
        rolesNew.add(roleService.findById(3L));
        user.setRoles(rolesNew);
        user.setEnabled(true);
        User userNew = userService.save(user);

        long id = userNew.getId();
        response.addHeader("id", String.valueOf(id));
        return "index";
    }

    @GetMapping(value = "/delete_user")
    public String deleteUser(@RequestParam(name="id")Long id) { userService.deleteById(id);
        return "redirect:/users";
    }

    //Вход
    @GetMapping(value ="/signin")
    public String signin() {
        return "signin";
    }

    @GetMapping(value ="/signin-error")
    public String signinError(Model model) {
        model.addAttribute("loginError",true);
        return "signin";
    }

    @PostMapping(value="/signin")
    public String signinUser(HttpServletResponse response,
            @RequestParam(name="username")String username,
            @RequestParam(name="password")String password){
        User user = userService.getUserByUsername(username);
        System.out.println(username);
        System.out.println(password);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        if (user==null || !user.getPassword().equals(password)) return "redirect:/signin-error";
        else return "index";
    }

}