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

    public static Drug mapToDrug(AddDrugRequest  request) {
        Drug drug = new Drug();
        drug.setId(request.getId());
        drug.setPrice(request.getPrice());
        drug.setName(request.getName());
        drug.setBrand(request.getBrand());
        return drug;

    }
    public static void verify(RegisterUserRequest request){
        if(request.getFullName() == null || request.getFullName().isEmpty()) {
            throw  new IllegalArgumentException("Full name is required");
        }
        if(request.getUsername() == null || request.getUsername().isEmpty()) {
        throw  new IllegalArgumentException("Username is required");
        }
        if(request.getPassword() == null || request.getPassword().isEmpty()) {
            throw  new IllegalArgumentException("Password is required");
        }
    }
}
