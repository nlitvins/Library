package com.nlitvins.web_application.outbound.repository.jpa;


import com.nlitvins.web_application.outbound.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
}
