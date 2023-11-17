package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserRegisterDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {


    boolean areImported();

    void saveAll(List<User> usersToImportToDB);

    void save(User user);

    User findByLegacyId(Short cook_userId);

    Optional<User> findByEmail(String email);

    //надолу са вече след импорта на старите данни

    //login i logout ги изтриваме, защото вече ги имплементираме посредством Spring security!!! Закоментирам ги за да имам за справка ако реша
//    void login(UserLoginDTO userDTO);
//
//    void logout();

    boolean register(UserRegisterDTO userRegisterDTO);

    Optional<User> findByDisplayNickname(String displayNickname);


    Optional<UserBasicDTO> getBasicUserData(String email);

    Optional <User> findById(Long id);
}
