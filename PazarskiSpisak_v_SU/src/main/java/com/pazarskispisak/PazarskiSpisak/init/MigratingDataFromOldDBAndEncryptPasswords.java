package com.pazarskispisak.PazarskiSpisak.init;

import com.pazarskispisak.PazarskiSpisak.service.SeedService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(1)
public class MigratingDataFromOldDBAndEncryptPasswords implements CommandLineRunner {

    private final SeedService seedService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public MigratingDataFromOldDBAndEncryptPasswords(SeedService seedService, PasswordEncoder passwordEncoder, UserService userService) {
        this.seedService = seedService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {

            //migrating data
         this.seedService.seedDatabase();


    }
}
