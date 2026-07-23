package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private int nextId;
    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getId() == 0) {
            user.setId(++nextId);
            users.add(user);
            return user;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                return user;
            }
        }

        user.setId(++nextId);
        users.add(user);
        return user;
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            return;
        }
        users.removeIf(stored -> stored.getId() == user.getId());
    }

    @Override
    public void deleteAll() {
        users.clear();
        nextId = 0;
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
        if (username == null || username.isEmpty()) {
            return null;
        }
        String normalized = username.trim().toLowerCase();
        for (var user : users) {
            if (user.getUsername() != null && user.getUsername().trim().toLowerCase().equals(normalized)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return users.size();
    }
}
