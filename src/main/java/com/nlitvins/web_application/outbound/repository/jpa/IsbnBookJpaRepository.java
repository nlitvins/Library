package com.nlitvins.web_application.outbound.repository.jpa;

import com.nlitvins.web_application.outbound.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsbnBookJpaRepository extends JpaRepository<BookEntity, Integer> {
}
