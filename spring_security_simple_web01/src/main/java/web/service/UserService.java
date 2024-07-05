package web.service;

import org.springframework.stereotype.Service;
import web.form.CreateUserForm;

/**
 * @author dkw
 */
public interface UserService{
    Boolean createUser(CreateUserForm user);
}
