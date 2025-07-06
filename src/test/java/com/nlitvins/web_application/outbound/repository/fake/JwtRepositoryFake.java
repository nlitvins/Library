package com.nlitvins.web_application.outbound.repository.fake;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JwtRepositoryFake implements JwtRepository {

    private final Map<String, Integer> tokenToUserIdMap = new HashMap<>();
    private final Map<User, String> userToTokenMap = new HashMap<>();

    @Override
    public String getToken(User user) {
        return userToTokenMap.get(user);
    }

    @Override
    public String getUserName(String token) {
        throw new NotImplementedException();
    }

    @Override
    public String getRole(String token) {
        throw new NotImplementedException();
    }

    @Override
    public Integer getUserId(String token) {
        return tokenToUserIdMap.get(token);
    }

    @Override
    public boolean isValidToken(String token) {
        throw new NotImplementedException();
    }

    public String save(User user) {
        String token = "testToken" + user.getId();

        if (userToTokenMap.putIfAbsent(user, token) != null) {
            throw new RuntimeException("User already exists");
        }
        if (tokenToUserIdMap.putIfAbsent(token, user.getId()) != null) {
            throw new RuntimeException("Token already exists");
        }
        return token;
    }

    public void clear() {
        userToTokenMap.clear();
        tokenToUserIdMap.clear();
    }
}
