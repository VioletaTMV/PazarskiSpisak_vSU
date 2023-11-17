package com.pazarskispisak.PazarskiSpisak.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminUsersController {

    @GetMapping("/admin-users")
    public String adminUsers(){

        return "redirect:/admin-users-view";
    }

    @GetMapping("/admin-users-view")
    public String adminUsersView(){

        return "/admin-users-view";
    }

    @GetMapping("/admin-users-edit")
    public String adminUsersEdit(){

        return "/admin-users-edit";
    }

    @GetMapping("/admin-users-delete")
    public String adminUsersDelete(){

        return "/admin-users-delete";
    }

}
