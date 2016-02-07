package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by ashraf on 2/7/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
