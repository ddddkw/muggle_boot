package web02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web02.entity.Role;
import web02.service.RoleService;

/**
 * @author dkw
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public void createRole(@RequestBody Role role){


        roleService.saveRole(role);
    }
}
