package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findUserByEmailAndPassword(String email,String password);

}
