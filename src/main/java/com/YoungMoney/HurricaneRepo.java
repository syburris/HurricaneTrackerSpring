package com.YoungMoney;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by stevenburris on 10/21/16.
 */
public interface HurricaneRepo extends CrudRepository<Hurricane, Integer> {
    List<Hurricane> findByCategory(Hurricane.Category category);
    List<Hurricane> findByLocation(String location);
    List<Hurricane> findByName(String name);
    List<Hurricane> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);

    Hurricane findFirstByLocation(String location);
    int countByCategory(Hurricane.Category category);
}