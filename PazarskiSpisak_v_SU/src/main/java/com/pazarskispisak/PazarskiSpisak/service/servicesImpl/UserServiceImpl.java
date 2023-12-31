package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.AdminUserViewDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserRegisterDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.UserRepository;
import com.pazarskispisak.PazarskiSpisak.service.UserRolesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import com.pazarskispisak.PazarskiSpisak.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private UserRolesService userRolesService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRolesService userRolesService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRolesService = userRolesService;
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

    //Долното или да го променя, да не следи самия процес по логин, защото римембърме куки не би го отразило...
    //ами ...или да го махна, а пък да си следя кой има активен пазарски списък...
    //ако го махам, да го махна и като поле в ентитито
    private void updateLastLogTimeForUserInDB(User user) {

        this.userRepository.updateLastTimeLoggedIn(LocalDateTime.now(), user.getId());
    }

    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {

        User newUser = this.modelMapper.map(userRegisterDTO, User.class);

        newUser.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));

        UserRoleEntity userRoleUser = this.userRolesService.getUserRoleEntity(UserRoleEnum.USER);
        UserRoleEntity userRoleAdmin = this.userRolesService.getUserRoleEntity(UserRoleEnum.ADMIN);

        if (this.userRepository.count() == 0){
            newUser.setUserRoles(Set.of(userRoleUser, userRoleAdmin));
        }

        newUser.setUserRoles(Set.of(userRoleUser));

        this.userRepository.save(newUser);

        return true;
    }

    @Override
    public Optional<User> findByDisplayNickname(String displayNickname) {
        return this.userRepository.findByDisplayNickname(displayNickname);
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

    @Override
    public List<AdminUserViewDTO> getUsersInfoOrderedByRegistrationDateReversedWithAdminsFirst() {


        List<User> users = this.userRepository.findByOrderByUserRolesCountThenByRegistrationDate();

        List<AdminUserViewDTO> adminUserViewDTOList = Arrays.stream(this.modelMapper.map(users, AdminUserViewDTO[].class)).toList();

        return adminUserViewDTOList;
    }

    @Override
    public AdminUserViewDTO getUserToUpdate(Long userId) {

        Optional<User> byId = this.userRepository.findById(userId);
        AdminUserViewDTO mapped = this.modelMapper.map(byId, AdminUserViewDTO.class);

        return mapped;
    }

    @Override
    @Transactional
    public void updateUserRoles(AdminUserViewDTO adminUserViewDTO) {

        Optional<User> byId = this.userRepository.findById(adminUserViewDTO.getId());

        if (byId.isEmpty()){
            throw new ObjectNotFoundException("User with ID " + adminUserViewDTO.getId() + " not found.");
        }

        Set<UserRoleEntity> collect = adminUserViewDTO.getUserRoles().stream()
                .map(r -> userRolesService.getUserRoleEntity(UserRoleEnum.valueOf(r)))
                .collect(Collectors.toSet());

        byId.get().setUserRoles(collect);

    }


}
