package com.nlitvins.web_application.outbound.repository.fake;


import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepositoryFake implements UserRepository {

    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return users.values().stream().map(user -> user.toBuilder().build()).toList();
    }

    @Override
    public User findById(int id) {
        User user = users.get(id);
        return user != null ? user.toBuilder().build() : null;
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user.toBuilder().build();
    }

    // TODO: fix
    @Override
    public User findByUserName(String userName) {
        throw new NotImplementedException();
    }

    public void clear() {
        users.clear();
    }

}
