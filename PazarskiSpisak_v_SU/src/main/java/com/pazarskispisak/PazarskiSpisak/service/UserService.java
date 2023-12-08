package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.AdminUserViewDTO;
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

    boolean register(UserRegisterDTO userRegisterDTO);

    Optional<User> findByDisplayNickname(String displayNickname);


    Optional<UserBasicDTO> getBasicUserData(String email);

    Optional <User> findById(Long id);

    List<AdminUserViewDTO> getUsersInfoOrderedByRegistrationDateReversedWithAdminsFirst();

    AdminUserViewDTO getUserToUpdate(Long userId);

    void updateUserRoles(AdminUserViewDTO adminUserViewDTO);
}
