package com.example.springthymeleafupload.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    //SQL, HQL
    @Query("select u from User u where u.username=:username")
    public User getUserByUsername(@Param("username") String username);

    @Query("select u from User u where u.email=:email")
    public User getUserByEmail(@Param("email") String email);
}
