package com.kamhoops.data.repository;

import com.kamhoops.data.domain.UserAccount;
import com.kamhoops.data.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountRepository extends BaseJpaRepository<UserAccount> {

    public UserAccount findByEmail(String email);

    public UserAccount findByUsername(String username);

    @Query(value = "select e from com.kamhoops.data.domain.UserAccount e where e.deleted = false and e.id = ?1")
    public UserAccount findByIdAndNotDeleted(Long id);

}
