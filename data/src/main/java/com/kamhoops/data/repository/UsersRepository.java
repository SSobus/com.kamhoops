package com.kamhoops.data.repository;

import com.kamhoops.data.domain.Users;
import com.kamhoops.data.repository.base.BaseJpaRepository;

public interface UsersRepository extends BaseJpaRepository<Users> {

    public Users findByEmail(String email);

}
