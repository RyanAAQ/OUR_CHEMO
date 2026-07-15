package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.User;

public interface UserRepository {

    User save(User user);
    void delete(User user);
    void deleteAll();
    long count();
    User findById(long id);
    User findByUsername(String username);
    int size();
}
