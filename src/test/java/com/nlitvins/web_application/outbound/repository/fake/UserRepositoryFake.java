package com.nlitvins.web_application.outbound.repository.fake;


import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepositoryFake implements UserRepository {

    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User findById(int id) {
        return users.get(id);
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findByUserName(String userNme) {
        return users.get(userNme);
    }

    public void clear() {
        users.clear();
    }

}
