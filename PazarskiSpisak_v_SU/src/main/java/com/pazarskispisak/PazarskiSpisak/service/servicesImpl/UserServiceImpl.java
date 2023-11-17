package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserRegisterDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.repository.UserRepository;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public void saveAll(List<User> usersToImportToDB) {
        this.userRepository.saveAll(usersToImportToDB);
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User findByLegacyId(Short cook_userId) {

        return this.userRepository.findByLegacyId(cook_userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    //login i logout ги изтриваме, защото вече ги имплементираме посредством Spring security!!! Закоментирам ги за да имам за справка ако реша
//    @Override
//    public void login(UserLoginDTO userLoginDTO) {
//
//        User currentUser = this.userRepository.findByEmail(userLoginDTO.getEmail()).get();
//
//        doLogin(currentUser);
//
////        Optional<User> userOpt = findByEmail(userLoginDTO.getEmail());
////
////        if (userOpt.isEmpty()) {
////            // върни съобщение за грешка
////            LOGGER.info("User with name [{}] not found", userLoginDTO.getEmail());
////            return false;
////        }
////
////        String rawPassword = userLoginDTO.getPassword();
////        String existingUserEncryptedPassword = userOpt.get().getPassword();
////
////        boolean passwordsAreEqual = this.passwordEncoder.matches(rawPassword, existingUserEncryptedPassword);
//
////        if (passwordsAreEqual) {
////            doLogin(userOpt.get());
////        }
////        else {
////            logout();
////        }
////
////        return passwordsAreEqual;
//    }
//
//
//    private void doLogin(User user) {
//
//        this.currentUser.setDisplayNickname(user.getDisplayNickname()).setLoggedIn(true);
//
//        updateLastLogTimeForUserInDB(user);
//    }

    private void updateLastLogTimeForUserInDB(User user) {

        this.userRepository.updateLastTimeLoggedIn(LocalDateTime.now(), user.getId());
    }

    //login i logout ги изтриваме, защото вече ги имплементираме посредством Spring security!!! Закоментирам ги за да имам за справка ако реша
//    @Override
//    public void logout() {
//        this.currentUser.clearCurrentUser();
//    }

    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {

        User newUser = this.modelMapper.map(userRegisterDTO, User.class);

        newUser.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));

        this.userRepository.save(newUser);

        //вече не можем да правим до логин
//        doLogin(newUser);

        return true;
    }

    @Override
    public void encryptPasswordMigratedUsers() {
        List<User> migratedUsers = this.userRepository.findByIdBetween(1, 15);

        for (int i = 0; i < migratedUsers.size(); i++) {
            User currentUser = migratedUsers.get(i);
            this.userRepository.updateUserPasswordWithEncodedOne(this.passwordEncoder.encode(currentUser.getPassword()), currentUser.getId());
        }

    }

    @Override
    public Optional<User> findByDisplayNickname(String displayNickname) {

        Optional<User> byDisplayNickname = this.userRepository.findByDisplayNickname(displayNickname);

        return byDisplayNickname;
    }

    @Override
    public Optional<UserBasicDTO> getBasicUserData(String email) {

        Optional<User> byEmailOpt = findByEmail(email);

        if (byEmailOpt.isEmpty()){
            return Optional.empty();
        }

        UserBasicDTO userBasicDTO = this.modelMapper.map(byEmailOpt.get(), UserBasicDTO.class);

        return Optional.of(userBasicDTO);
    }

    @Override
    public Optional<User> findById(Long id) {

        return this.userRepository.findById(id);

    }


}
