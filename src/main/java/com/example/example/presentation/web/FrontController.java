package com.example.example.presentation.web;

import com.example.example.application.UserService;
import com.example.example.presentation.api.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class FrontController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup.html";
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup(@Valid @ModelAttribute("signupRequest") SignupRequest signupRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup.html";
        }

        userService.signup(signupRequest);

        return "index.html";
    }

    @GetMapping("/success")
    public String success() {
        return "success.html";
    }
}
