package ng.ourChemo.services;

import ng.ourChemo.data.models.User;
import ng.ourChemo.data.repositories.UserRepository;
import ng.ourChemo.data.repositories.UserRepositoryImpl;
import ng.ourChemo.dtos.requests.RegisterUserRequest;
import ng.ourChemo.dtos.requests.UserLoginRequest;
import ng.ourChemo.dtos.requests.UserLogoutRequest;
import ng.ourChemo.dtos.responses.RegisterUserResponse;
import ng.ourChemo.dtos.responses.UserLoginResponse;
import ng.ourChemo.dtos.responses.UserLogoutResponse;
import ng.ourChemo.utils.Mapper;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public RegisterUserResponse registerChemist(RegisterUserRequest request) {
        request.setUsername(request.getUsername().toLowerCase());
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null) {
            throw new IllegalArgumentException("Username " + request.getUsername() + " already exists");
        }
        Mapper.verify(request);
        userRepository.save(Mapper.mapToUser(request));
        RegisterUserResponse response = new RegisterUserResponse();
        response.setFullName(request.getFullName());
        response.setUsername(request.getUsername());
        return response;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (request == null) throw new IllegalArgumentException("Login request is null");

        if (request.getUsername() == null || request.getPassword() == null || request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Username or password is null or empty");
        }
        if (user == null) {
            throw new IllegalArgumentException("User with username " + request.getUsername() + " does not exist");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }
        userRepository.save(user);
        user.setLoggedIn(true);
        UserLoginResponse response = new UserLoginResponse();
        response.setUsername(request.getUsername());
        response.setLoggedIn(true);
        return response;
    }


    @Override
    public UserLogoutResponse logout(UserLogoutRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setLoggedIn(false);
        UserLogoutResponse response = new UserLogoutResponse();
        response.setUsername(user.getUsername());
        response.setLoggedIn(false);
        return response;
    }
}
