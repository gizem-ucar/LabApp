package com.project.labapp.web;

import com.project.labapp.entities.Role;
import com.project.labapp.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class RoleWebController {

    private RoleService roleService;

    public RoleWebController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping("/web/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

}
