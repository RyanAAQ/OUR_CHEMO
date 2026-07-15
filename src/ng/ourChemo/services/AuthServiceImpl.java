package ng.ourChemo.services;

import ng.ourChemo.data.repositories.UserRepository;
import ng.ourChemo.data.repositories.UserRepositoryImpl;
import ng.ourChemo.dtos.requests.RegisterUserRequest;
import ng.ourChemo.dtos.responses.RegisterUserResponses;
import ng.ourChemo.utils.Mapper;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public RegisterUserResponses registerChemist(RegisterUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException("Username " + request.getUsername() + " already exists");
        }
        userRepository.save(Mapper.mapToUser(request));
        return null;
    }

    @Override
    public String login(String username, String password) {
        return "";
    }

    @Override
    public String logout(String username) {
        return "";
    }
}
