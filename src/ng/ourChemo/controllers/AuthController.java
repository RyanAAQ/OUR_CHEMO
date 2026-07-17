package ng.ourChemo.controllers;

import ng.ourChemo.dtos.requests.RegisterUserRequest;
import ng.ourChemo.dtos.requests.UserLoginRequest;
import ng.ourChemo.dtos.requests.UserLogoutRequest;
import ng.ourChemo.dtos.responses.RegisterUserResponse;
import ng.ourChemo.dtos.responses.UserLoginResponse;
import ng.ourChemo.dtos.responses.UserLogoutResponse;
import ng.ourChemo.services.AuthService;
import ng.ourChemo.services.AuthServiceImpl;

public class AuthController {

    private final AuthService authService = new AuthServiceImpl();

    public RegisterUserResponse registerChemist(RegisterUserRequest request) {
        return authService.registerChemist(request);
    }

    public UserLoginResponse loginChemist(UserLoginRequest request) {
        return authService.login(request);
    }

    public UserLogoutResponse logoutChemist(UserLogoutRequest request) {
        return authService.logout(request);
    }
}
