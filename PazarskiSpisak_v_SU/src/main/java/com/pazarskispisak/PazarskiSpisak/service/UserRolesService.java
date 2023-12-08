package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;

import java.util.List;

public interface UserRolesService {
    UserRoleEntity getUserRoleEntity(UserRoleEnum userRoleEnum);

    List<String> getAllUserRolesDTOList();
}
