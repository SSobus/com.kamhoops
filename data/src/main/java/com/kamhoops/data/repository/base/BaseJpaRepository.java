package com.kamhoops.data.repository.base;


import com.kamhoops.data.domain.base.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Base Jpa Repository
 * <p/>
 * All repositories should extend this interface to provide common Jpa/QueryDSL functionality against
 * repositories with an auto-generated ID of type Long
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends AbstractEntity> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {
    public List<T> findByActiveTrue();

    public List<T> findByDeletedFalse();

    public T findByDeletedFalseAndId(Long id);
}
