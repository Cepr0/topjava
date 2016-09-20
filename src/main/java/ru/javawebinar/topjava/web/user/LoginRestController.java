package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LoginRestController extends AbstractUserController {
    public LoginRestController(UserService service) {
        super(service);
    }

    @Override
    public List<User> getAll() {
        return super.getAll().stream()
                .map(user -> new User(
                        user.getId(),
                        user.getName(),
                        null,
                        null,
                        user.getRoles().contains(Role.ROLE_ADMIN) ? Role.ROLE_ADMIN : Role.ROLE_USER))
                .collect(Collectors.toList());
    }

    @Override
    public User get(int id) {
        return super.get(id);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }
}
