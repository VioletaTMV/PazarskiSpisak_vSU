package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoleEntity, Long> {

    long count();

    UserRoleEntity findByUserRole(UserRoleEnum userRoleEnum);
}
