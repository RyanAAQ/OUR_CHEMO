package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private int count;
    private List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        user.setId(++count);
        users.add(user);
        return user;
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public void delete(User user) {
        users.remove(user);
        count--;
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public User findById(long id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    @Override
    public int size() {
        return users.size();
    }
}
