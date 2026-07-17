package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static int count;
    private static List<User> users = new ArrayList<>();

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
        count = 0;
    }

    @Override
    public User findById(long id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        for (var user : users) {
            if (user.getUsername().equals(username.toLowerCase())) return user;
        }
        return null;
    }

    @Override
    public int size() {
        return users.size();
    }
}
