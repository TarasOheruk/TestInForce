package com.example.registring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    @GetMapping ("/main")
    public String view(){
        return "index";
    }

    @GetMapping ("/register")
    public String signUp(Model model){
        model.addAttribute("user",new User());
        return "signup_form";
    }
    @PostMapping("/process_register")
    public String Regisrt(User user, RedirectAttributes ra){
        try {
            userService.saveDefaultUser(user);
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("dupl","Email already exist");
            return "redirect:/register";
        }
        return "index";
    }

    @GetMapping("/list_user")
    public String users(Model model)
    {
        List<User> listUsers = userService.listAll();

        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_edit";
    }
    @PostMapping("/users/save")
    public String saveUser(User user) {
        userService.updateUser(user);

        return "redirect:/list_user";
    }
}
