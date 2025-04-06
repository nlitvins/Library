package com.nlitvins.web_application.outbound.repository;


import com.nlitvins.web_application.outbound.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
