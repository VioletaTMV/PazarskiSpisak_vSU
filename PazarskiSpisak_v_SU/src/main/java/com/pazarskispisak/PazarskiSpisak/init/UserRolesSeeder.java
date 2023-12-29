//package com.pazarskispisak.PazarskiSpisak.init;
//
//import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
//import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
//import com.pazarskispisak.PazarskiSpisak.repository.UserRolesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@Order(2)
//public class UserRolesSeeder implements CommandLineRunner {
//
//    private UserRolesRepository userRolesRepository;
//
//    @Autowired
//    public UserRolesSeeder(UserRolesRepository userRolesRepository) {
//        this.userRolesRepository = userRolesRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        if (this.userRolesRepository.count() == 0){
//
//            List<UserRoleEntity> userRoleEntityList = Arrays.stream(UserRoleEnum.values())
//                    .map(UserRoleEntity::new)
//                    .collect(Collectors.toList());
//
//            this.userRolesRepository.saveAll(userRoleEntityList);
//
//        }
//
//    }
//}
