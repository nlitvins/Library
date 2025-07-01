package com.nlitvins.web_application.outbound.repository.fake;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class JwtRepositoryFake implements JwtRepository {

    private final HashMap<String, Integer> tokenToUserIdMap = new HashMap<>();
    private final HashMap<User, String> userToTokenMap = new HashMap<>();

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
}
