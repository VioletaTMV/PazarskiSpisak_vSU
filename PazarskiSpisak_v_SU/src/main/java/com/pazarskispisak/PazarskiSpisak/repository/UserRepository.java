package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLegacyId(Short cook_userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByDisplayNickname(String displayNickname);

    List<User> findByIdBetween(int startIndex, int endIndex);

    @Modifying
    @Transactional
    @Query("update User u set u.password= :encryptedPassword where u.id = :id")
    void updateUserPasswordWithEncodedOne(String encryptedPassword, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.lastTimeLoggedIn=:currentDateTime where u.id =:id")
    void updateLastTimeLoggedIn(LocalDateTime currentDateTime, Long id);
}
