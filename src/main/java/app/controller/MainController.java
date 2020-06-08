package app.controller;

import app.model.Role;
import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/admin")
    public String main(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/add")
    public String getAdd(ModelMap model) {
        model.addAttribute("u_role", Role.USER);
        model.addAttribute("a_role", Role.ADMIN);
        return "add";
    }

    @PostMapping("/admin/add")
    public String postAdd(@RequestParam(name = "user_role", required = false) Role userRole,
                          @RequestParam(name = "admin_role", required = false) Role adminRole,
                          User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);

        user.setRoles(roles);

        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update")
    public String getUpdate(ModelMap model) {
        model.addAttribute("u_role", Role.USER);
        model.addAttribute("a_role", Role.ADMIN);
        return "update";
    }

    @PostMapping("/admin/update")
    public String postUpdate(@RequestParam(name = "user_role", required = false) Role roleUser,
                             @RequestParam(name = "admin_role", required = false) Role roleAdmin,
                             User user) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        roles.add(roleAdmin);

        user.setRoles(roles);
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String user(ModelMap model) {
        String username = (String) session.getAttribute("username");
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }
}
