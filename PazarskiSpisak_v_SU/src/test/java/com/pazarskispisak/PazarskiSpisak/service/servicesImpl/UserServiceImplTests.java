package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserRegisterDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    private UserServiceImpl serviceToTest;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private PasswordEncoder mockedPasswordEncoder;


    @BeforeEach
    void setUp() {
        serviceToTest = new UserServiceImpl(mockedUserRepository, mockedModelMapper, mockedPasswordEncoder);
    }

    @Test
    void testFindByEmailOptionalUserEmpty() {
        Assertions.assertTrue(() -> serviceToTest.findByEmail("nonexistent@test.com").isEmpty());
    }

    @Test
    void testFindByEmailOptionalUser() {
//        //Arrange
        User testUser = createTestUser();

        when(mockedUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        //Act
        Optional<User> userOpt = serviceToTest.findByEmail(testUser.getEmail());

        //Assert
        Assertions.assertTrue(userOpt.isPresent());
        assertEquals(testUser.getPassword(), userOpt.get().getPassword());
        assertEquals(testUser.getDisplayNickname(), userOpt.get().getDisplayNickname());
        assertEquals(testUser.getRegisteredOn(), userOpt.get().getRegisteredOn());
        assertEquals(2, userOpt.get().getUserRoles().size());
        assertTrue(containsAuthority(userOpt.get(), "USER"),
                "The user has no USER role.");
        assertTrue(containsAuthority(userOpt.get(), "ADMIN"),
                "The user has no ADMIN role.");
    }

    @Test
    void testRegister() {
        //Arrange
        UserRegisterDTO testUserRegisterDTO = createTestUserRegisterDTO();
        User testUser = createTestUser();

        when(mockedModelMapper.map(testUserRegisterDTO, User.class))
                .thenReturn(testUser);
        when(mockedPasswordEncoder.encode(testUserRegisterDTO.getPassword()))
                .thenReturn(testUserRegisterDTO.getPassword());

        //Act
        boolean register = serviceToTest.register(testUserRegisterDTO);

        //Assert
        assertEquals(testUserRegisterDTO.getEmail(), testUser.getEmail());
        assertEquals(mockedPasswordEncoder.encode(testUserRegisterDTO.getPassword()), testUser.getPassword());
        assertEquals(testUserRegisterDTO.getDisplayNickname(), testUser.getDisplayNickname());
        assertEquals(testUserRegisterDTO.getPassword(), testUserRegisterDTO.getPasswordRepeat());
    }

    @Test
    void testFindByDisplayNickname_whenNonExistingUser(){
        assertTrue(() -> serviceToTest.findByDisplayNickname("nonexistent").isEmpty());
    }

    @Test
    void testFindByDisplayNickname_whenUserExists(){
        //Arrange
        User testUser = createTestUser();
        when(mockedUserRepository.findByDisplayNickname(testUser.getDisplayNickname()))
                .thenReturn(Optional.of(testUser));

        //Act
        Optional<User> userOpt = serviceToTest.findByDisplayNickname(testUser.getDisplayNickname());

        //Assert
        assertTrue(userOpt.isPresent());
        assertEquals(testUser.getEmail(), userOpt.get().getEmail());
       assertEquals(testUser.getPassword(), userOpt.get().getPassword());
       assertEquals(testUser.getRegisteredOn(), userOpt.get().getRegisteredOn());

    }


    @Test
    void test_getBasicUserData_whenExistingUser(){
        //Arrange
        User testUser = createTestUser();
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO();
        when(mockedUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));
        when(mockedModelMapper.map(testUser, UserBasicDTO.class))
                .thenReturn(testUserBasicDTO);

        //Act
        Optional<UserBasicDTO> basicUserDataOpt = serviceToTest.getBasicUserData(testUser.getEmail());

        //Assert
        assertTrue(basicUserDataOpt.isPresent());
        assertEquals(testUserBasicDTO.getDisplayNickname(), basicUserDataOpt.get().getDisplayNickname());

    }

    private boolean containsAuthority(User user, String expectedAuthority) {
        return user.getUserRoles()
                .stream()
                .anyMatch(userRoleEntity -> expectedAuthority.equals(userRoleEntity.getUserRole().name()));
    }

    private static User createTestUser() {
        User testUser = new User();
        testUser.setEmail("something@test.com");
        testUser.setDisplayNickname("nick");
        testUser.setPassword("pass");
        testUser.setUserRoles(Set.of(
                new UserRoleEntity().setUserRole(UserRoleEnum.USER),
                new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN)
        ));
        testUser.setRegisteredOn(LocalDate.of(2023, 11, 5));

        return testUser;
    }

    private static UserRegisterDTO createTestUserRegisterDTO() {
        UserRegisterDTO testUserRegisterDTO = new UserRegisterDTO();
        testUserRegisterDTO.setEmail("something@test.com");
        testUserRegisterDTO.setDisplayNickname("nick");
        testUserRegisterDTO.setPassword("pass");
        testUserRegisterDTO.setPasswordRepeat("pass");

        return testUserRegisterDTO;
    }

    private static UserBasicDTO createTestUserBasicDTO(){

        UserBasicDTO testUserBasicDTO = new UserBasicDTO();
        testUserBasicDTO.setDisplayNickname("nick");

        return testUserBasicDTO;
    }



//    @Test
//    void testMock(){
//
//        User user = new User();
//        user.setDisplayNickname("tralala");
//
//        when(mockedUserRepository.findByEmail("tralala@test.com"))
//                .thenReturn(Optional.of(user));
//
//        Optional<User> userOpt = mockedUserRepository.findByEmail("tralala@test.com");
//
//        Assertions.assertEquals("tralala", userOpt.get().getDisplayNickname());
//    }


}
