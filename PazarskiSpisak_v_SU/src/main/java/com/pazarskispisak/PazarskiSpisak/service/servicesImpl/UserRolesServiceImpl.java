package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.UserRolesRepository;
import com.pazarskispisak.PazarskiSpisak.service.UserRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRolesServiceImpl implements UserRolesService {

    private UserRolesRepository userRolesRepository;
    private ModelMapper modelMapper;

    public UserRolesServiceImpl(UserRolesRepository userRolesRepository, ModelMapper modelMapper) {
        this.userRolesRepository = userRolesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleEntity getUserRoleEntity(UserRoleEnum userRoleEnum) {

        return this.userRolesRepository.findByUserRole(userRoleEnum);
    }

    @Override
    public List<String> getAllUserRolesDTOList() {

        List<UserRoleEntity> all = this.userRolesRepository.findAll();
        List<String> allDTOs = Arrays.stream(this.modelMapper.map(all, String[].class)).toList();

        return allDTOs;

    }
}
