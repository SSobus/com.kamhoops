package com.kamhoops.data.repository;

import com.kamhoops.data.domain.News;
import com.kamhoops.data.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsRepository extends BaseJpaRepository<News> {
    Page<News> findAllByActiveTrue(Pageable pageable);
}
