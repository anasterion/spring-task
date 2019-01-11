package lv.helloit.lottery.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/show-login-page")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping(value = "/show-admin-page")
    public String showAdminPage() {
        return "adminMenu";
    }

    @GetMapping(value = "/show-user-page")
    public String shoUserPage() {
        return "userMenu";
    }

    @GetMapping(value = "/")
    public String showIndexPage() {
        return "index";
    }
}
