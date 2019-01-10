package lv.helloit.lottery.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/show-login-page")
    public String showLoginPage() {
        return "login";
    }
}
