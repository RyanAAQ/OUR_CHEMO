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

    private final UserRepository userRepository;

    public AuthServiceImpl() {
        this(new UserRepositoryImpl());
    }

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterUserResponse registerChemist(RegisterUserRequest request) {
        if (request.getFullName() == null || request.getFullName().isEmpty())
            throw new IllegalArgumentException("Full name is required");
        if (request.getUsername() == null || request.getUsername().isEmpty())
            throw new IllegalArgumentException("Username is required");
        if (request.getPassword() == null || request.getPassword().isEmpty())
            throw new IllegalArgumentException("Password is required");

        request.setUsername(request.getUsername().toLowerCase());

        if (userRepository.findByUsername(request.getUsername()) != null)
            throw new IllegalArgumentException("Username " + request.getUsername() + " already exists");

        userRepository.save(Mapper.mapToUser(request));

        RegisterUserResponse response = new RegisterUserResponse();
        response.setUsername(request.getUsername());
        response.setFullName(request.getFullName());
        return response;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty())
            throw new IllegalArgumentException("Username is required");
        if (request.getPassword() == null || request.getPassword().isEmpty())
            throw new IllegalArgumentException("Password is required");

        String username = request.getUsername().toLowerCase();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("User with username " + username + " does not exist");
        if (!user.getPassword().equals(request.getPassword()))
            throw new IllegalArgumentException("Incorrect password");

        user.setLoggedIn(true);

        UserLoginResponse response = new UserLoginResponse();
        response.setUsername(username);
        response.setLoggedIn(true);
        return response;
    }

    @Override
    public UserLogoutResponse logout(UserLogoutRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty())
            throw new IllegalArgumentException("Username is required");

        String username = request.getUsername().toLowerCase();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("User not found");

        user.setLoggedIn(false);

        UserLogoutResponse response = new UserLogoutResponse();
        response.setUsername(user.getUsername());
        response.setLoggedIn(false);
        return response;
    }
}
