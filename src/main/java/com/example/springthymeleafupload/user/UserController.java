package com.example.springthymeleafupload.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value="/index")
    public String index(){
        return "index";
    }

    @PostMapping(value="/save_user")
    public String saveUser(User user, Model model){
        System.out.println(user);
        userService.save(user);
        model.addAttribute("user", userService.findAll());
        return "redirect:list_user";
    }

    @RequestMapping(value="/delete_user", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(name="id") Long id){
        System.out.println("delete");
        userService.deleteById(id);
        return "redirect:/list_user";
    }

    @GetMapping(value="/users")
    public String listUser(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value="/edit_user")
    public String editUser(Model model, @RequestParam(name="id") Long id){
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "edit_user";
    }

    /*@PostMapping(value="/edit_user")
    public String editUser(User user, Model model){
        User userDb = userService.findById(user.getId());
        userDb.setName(os.getName());
        osDb.setDeveloper(os.getDeveloper());
        osService.save(osDb);
        model.addAttribute("os", osService.findAll());
        return "redirect:list_os";
    }*/
}
