package com.spring.project.repository;

import com.spring.project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    Employee findByEmiratesID(String emiratesID);

}