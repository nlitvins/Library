package com.nlitvins.web_application.outbound.repository.jpa;

import com.nlitvins.web_application.outbound.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Integer> {

    List<BookEntity> findByIdIn(Collection<Integer> bookIds);
}
