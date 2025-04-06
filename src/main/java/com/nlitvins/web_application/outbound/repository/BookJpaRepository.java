package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.outbound.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Integer> {

}
