package com.YoungMoney.services;

import com.YoungMoney.entities.Hurricane;
import com.YoungMoney.entities.Like;
import com.YoungMoney.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by stevenburris on 10/28/16.
 */
public interface LikeRepository extends CrudRepository<Like, Integer> {
    Like findFirstByUserAndHurricane(User user, Hurricane hurricane);
    List<Like> findByUser(User user);
}
