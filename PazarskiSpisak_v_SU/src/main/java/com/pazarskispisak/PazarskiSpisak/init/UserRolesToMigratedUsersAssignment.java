//package com.pazarskispisak.PazarskiSpisak.init;
//
//import com.pazarskispisak.PazarskiSpisak.models.entities.User;
//import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
//import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
//import com.pazarskispisak.PazarskiSpisak.service.UserRolesService;
//import com.pazarskispisak.PazarskiSpisak.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//
//@Component
//@Order(3)
//public class UserRolesToMigratedUsersAssignment implements CommandLineRunner {
//
//    private UserService userService;
//    private UserRolesService userRolesService;
//
//    @Autowired
//    public UserRolesToMigratedUsersAssignment(UserService userService, UserRolesService userRolesService) {
//        this.userService = userService;
//        this.userRolesService = userRolesService;
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        if (!this.userService.findById(1L).get().getUserRoles().isEmpty()) {
//            return;
//        }
//
//        this.userService.assignRolesToMigratedUsers();
//
//    }
//}
