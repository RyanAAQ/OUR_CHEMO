package ng.ourChemo.utils;

import ng.ourChemo.data.models.Drug;
import ng.ourChemo.data.models.User;
import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.RegisterUserRequest;

public class Mapper {

    public static User mapToUser(RegisterUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        return user;
    }

    public static Drug mapToDrug(AddDrugRequest request) {
        Drug drug = new Drug();
        drug.setName(request.getName());
        drug.setPrice(request.getPrice());
        drug.setBrand(request.getBrand());
        return drug;
    }
}
