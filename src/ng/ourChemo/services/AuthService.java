package ng.ourChemo.services;

import ng.ourChemo.dtos.requests.RegisterUserRequest;
import ng.ourChemo.dtos.responses.RegisterUserResponses;

public interface AuthService {

    String login(String username, String password);
    String logout(String username);
    RegisterUserResponses registerChemist(RegisterUserRequest request);
}
