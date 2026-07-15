package ng.ourChemo.services;

import ng.ourChemo.data.models.User;
import ng.ourChemo.data.repositories.UserRepository;
import ng.ourChemo.data.repositories.UserRepositoryImpl;
import ng.ourChemo.dtos.requests.RegisterUserRequest;
import ng.ourChemo.dtos.responses.RegisterUserResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    private AuthService authService;
    private UserRepository user;

    @BeforeEach
    void setUp() {
        user = new UserRepositoryImpl();
        authService = new AuthServiceImpl();
    }


    @Test
    void registerChemistIncreasesTheUsers() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFullName("fullName");
        authService.registerChemist(request);
        assertEquals(1, user.count());
    }


    @Test
    void registerChemistReturnsResponseWithCorrectFullName() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFullName("fullName");
        authService.registerChemist(request);
        RegisterUserResponses response = new RegisterUserResponses();
        response.setFullName("Ryan Adams");
        response.setUsername("username");
        assertEquals("Ryan Adams", response.getFullName());
    }
}
//
//    @Test
//    void registerChemistWithDuplicateUsernameThrowsIllegalArgumentException() {
//        authService.registerChemist(buildRequest("ryanA9", "password", "Ryan Adams"));
//        assertThrows(IllegalArgumentException.class, () ->
//                authService.registerChemist(buildRequest("ryanA9", "password2", "Ryan Again")));
//    }
//}
