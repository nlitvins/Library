package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.UserRepository;
import com.nlitvins.web_application.outbound.model.UserEntity;
import com.nlitvins.web_application.outbound.repository.jpa.UserJpaRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<User> findAll() {
        // 1. map domain to outbound (entity)
        // 2. call database (jpa) repository
        List<UserEntity> userEntities = jpaRepository.findAll();
        // 3. map outbound to domain
        return OutboundMapper.Users.toDomainList(userEntities);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = OutboundMapper.Users.toEntity(user);
        UserEntity savedUserEntity = jpaRepository.save(userEntity);
        return OutboundMapper.Users.toDomain(savedUserEntity);
    }
}
