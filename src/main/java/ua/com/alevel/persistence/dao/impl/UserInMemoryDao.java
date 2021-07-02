package ua.com.alevel.persistence.dao.impl;

import org.apache.commons.lang3.ObjectUtils;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Deprecated
public class UserInMemoryDao implements UserDao {

    private final List<User> users = new ArrayList<>();

    public void create(User user) {
        user.setId(generateId());
        users.add(user);
    }

    public void update(User user) {
        User current = getUserById(user.getId());
        if (ObjectUtils.isNotEmpty(current)) {
            current.setFirstName(user.getFirstName());
            current.setLastName(user.getLastName());
        }
    }

    public void delete(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    public User findById(String id) {
        return getUserById(id);
    }

    public List<User> findAll() {
        return users;
    }

    public boolean existByEmail(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (users.stream().anyMatch(u -> u.getId().equals(id))) {
            return generateId();
        }
        return id;
    }

    private User getUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}
