package ng.ourChemo.services;

import ng.ourChemo.data.repositories.UserRepository;
import ng.ourChemo.data.repositories.UserRepositoryImpl;
import ng.ourChemo.dtos.requests.RegisterUserRequest;
import ng.ourChemo.dtos.requests.UserLoginRequest;
import ng.ourChemo.dtos.requests.UserLogoutRequest;
import ng.ourChemo.dtos.responses.RegisterUserResponse;
import ng.ourChemo.dtos.responses.UserLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    private AuthService authService;
    private UserRepository user;
    private RegisterUserRequest request;


    @BeforeEach
    void setUp() {
        user = new UserRepositoryImpl();
        authService = new AuthServiceImpl(user);
        request = new RegisterUserRequest();
        user.deleteAll();
    }


    @Test
    void registerChemistIncreasesTheUsers() {
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());
    }

    @Test
    void registerChemistCanRegisterMultipleUsers() {
        RegisterUserRequest request2 = new RegisterUserRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request2.setUsername("ryanA10");
        request2.setPassword("password");
        request2.setFullName("Ryan Adams");
        authService.registerChemist(request2);
        assertEquals(2, user.count());
    }


    @Test
    void registerChemistReturnsResponseWithCorrectFullName() {
        request.setUsername("username");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        RegisterUserResponse response = authService.registerChemist(request);
        assertEquals("Ryan Adams", response.getFullName());
    }

    @Test
    void registerChemistWithDuplicateUsernameThrowsIllegalArgumentException() {
        RegisterUserRequest request2 = new RegisterUserRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);

        request2.setUsername("username");
        request2.setPassword("password123");
        request2.setFullName("Ryan Adams");
        assertThrows(IllegalArgumentException.class, () -> authService.registerChemist(request2));
    }

    @Test
    void aRegisteredChemistCanLoginSuccessfully() {
        UserLoginRequest request1 = new UserLoginRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("ryanA9");
        request1.setPassword("password");
        UserLoginResponse response = authService.login(request1);
        assertTrue(response.isLoggedIn());
    }

    @Test
    void loginWithWrongUsernameThrowsException() {
        UserLoginRequest request1 = new UserLoginRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("nigger99");
        request1.setPassword("password");
        assertThrows(IllegalArgumentException.class, () -> authService.login(request1));
    }

    @Test
    void loginWithIncorrectPasswordThrowsException() {
        UserLoginRequest request1 = new UserLoginRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("ryanA9");
        request1.setPassword("niggerrrrr");
        assertThrows(IllegalArgumentException.class, () -> authService.login(request1));

    }

    @Test
    void afterRegisteringCannotLoginWithAnEmptyUsername() {
        UserLoginRequest request1 = new UserLoginRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("");
        request1.setPassword("password");
        assertThrows(IllegalArgumentException.class, () -> authService.login(request1));
    }

    @Test
    void afterRegisteringCannotLoginWithAnEmptyPassword() {
        RegisterUserRequest request = new RegisterUserRequest();
        UserLoginRequest request1 = new UserLoginRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("ryanA9");
        request1.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> authService.login(request1));
    }

    @Test
    void afterRegisteringAndLoggingInUserCanLogOutSuccessfully() {
        UserLoginRequest request1 = new UserLoginRequest();
        UserLogoutRequest request2 = new UserLogoutRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("ryanA9");
        request1.setPassword("password");
        request2.setUsername("ryanA9");
        UserLoginResponse response = authService.login(request1);
        assertTrue(response.isLoggedIn());

        authService.logout(request2);
        assertFalse(user.findByUsername("ryanA9").isLoggedIn());
    }

    @Test
    void afterRegisteringAndLoggingInLoggingOutWithInvalidUsernameThrowsException() {
        UserLoginRequest request1 = new UserLoginRequest();
        UserLogoutRequest request2 = new UserLogoutRequest();
        request.setUsername("ryanA9");
        request.setPassword("password");
        request.setFullName("Ryan Adams");
        authService.registerChemist(request);
        assertEquals(1, user.count());

        request1.setUsername("ryanA9");
        request1.setPassword("password");
        UserLoginResponse response = authService.login(request1);
        assertTrue(response.isLoggedIn());

        request2.setUsername("ryanA10");
        assertThrows(IllegalArgumentException.class, () -> authService.logout(request2));
    }

}
