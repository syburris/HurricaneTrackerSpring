package com.YoungMoney.services;

import com.YoungMoney.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by stevenburris on 10/24/16.
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}
