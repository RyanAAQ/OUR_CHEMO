package ng.ourChemo.utils;

import ng.ourChemo.data.models.User;
import ng.ourChemo.dtos.requests.RegisterUserRequest;

public class Mapper {

    public static User mapToUser(RegisterUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        return user;
    }
}
