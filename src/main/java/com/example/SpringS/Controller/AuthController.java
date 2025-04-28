package com.example.SpringS.Controller;

import com.example.SpringS.Entity.Enum.Otp;
import com.example.SpringS.Entity.Enum.UserAuth;
import com.example.SpringS.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/add")
    public void addUser(@RequestBody UserAuth userAuth) {
        userService.addUser(userAuth);
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody UserAuth userAuth) {
        userService.auth(userAuth);
    }

    @PostMapping("/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response) {
        if (userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
