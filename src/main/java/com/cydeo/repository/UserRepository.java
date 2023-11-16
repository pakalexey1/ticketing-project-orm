package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String username);

//    @Transactional  // moved to the implementation class level
    void deleteByUserName(String username);
}
