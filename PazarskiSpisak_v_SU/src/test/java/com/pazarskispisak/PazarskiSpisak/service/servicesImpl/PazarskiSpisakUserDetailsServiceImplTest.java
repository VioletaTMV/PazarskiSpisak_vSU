package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.text.html.parser.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PazarskiSpisakUserDetailsServiceImplTest {

    private PazarskiSpisakUserDetailsServiceImpl serviceToTest;
    @Mock
    private UserRepository mockedUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new PazarskiSpisakUserDetailsServiceImpl(mockedUserRepository);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("pesho@pesho.com"));
    }

    @Test
    void testUserFound() {
        //Arrange
        User testUser = createTestUser();

        when(mockedUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        //Act
        UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getEmail());

        //Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(
                testUser.getEmail(),
                userDetails.getUsername(),
                "User Details username (user email) is not populated correctly.");
        Assertions.assertEquals(
                testUser.getPassword(),
                userDetails.getPassword(),
                "User Details password is not populated correctly.");
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER),
                "The user has no USER role.");
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
                "The user has no ADMIN role.");
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority){
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(auth -> expectedAuthority.equals(auth.getAuthority()));
    }

    private static User createTestUser() {
        User testUser = new User();
        testUser.setEmail("something@test.com");
        testUser.setDisplayNickname("nick");
        testUser.setPassword("nickPass");
        testUser.setUserRoles(Set.of(
                new UserRoleEntity().setUserRole(UserRoleEnum.USER),
                new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN)
        ));
        testUser.setRegisteredOn(LocalDate.of(2023, 11, 5));

        return testUser;
    }

}