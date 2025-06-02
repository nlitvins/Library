package com.nlitvins.web_application.outbound.repository.jpa;


import com.nlitvins.web_application.outbound.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserName(String userName);

    List<UserEntity> findByIdIn(Collection<Integer> userIds);
}
