package com.dattp.testbackenddev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.testbackenddev.entity.User;

public interface UserRepository extends JpaRepository<User,String>{   
}