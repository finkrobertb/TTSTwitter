package com.tts.TechTalentTwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class AuthorizationController
{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String login()
    {
        // Use the login.html template
        return "login";
    }

    @GetMapping(value = "/signup")
    public String registration(Model model)
    {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping(value = "/signup")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model)
    {
        
        // If user exists, Username is already taken
        User userExists = userService.findByUsername(user.getUsername());
        if(userExists != null)
        {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
        }
        
        // if no errors, sign up is successful, reset form
        if(!bindingResult.hasErrors())
        {
            userService.saveNewUser(user);
            model.addAttribute("success", "Sign up successful!");
            model.addAttribute("user", new User());
        }
        
        return "registration"; // registration.html template
    }

}