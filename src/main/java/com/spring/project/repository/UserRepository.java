package com.spring.project.repository;

import com.spring.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmiratesID(String emiratesID);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByStaffID(String staffID);

    User findByEmail(String email);

}