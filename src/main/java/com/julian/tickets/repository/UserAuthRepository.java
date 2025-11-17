package com.julian.tickets.repository;

import com.julian.tickets.model.UserAuth;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthRepository extends CrudRepository<UserAuth, String> {

}
