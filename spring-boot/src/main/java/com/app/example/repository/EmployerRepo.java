package com.app.example.repository;

import com.app.example.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployerRepo extends JpaRepository<Employee,String> {

    List<Employee> findAll();

    @Query(nativeQuery = true, value = "SELECT  c.* FROM Employer c WHERE c.company = ?1")
    List<Employee> findByCompany(String company);

    @Query(nativeQuery = true, value = "SELECT  c.* FROM Employer c WHERE c.company = ?1 and (c.firstname LIKE %?2% OR c.lastname like %?2% OR c.position like %?2% OR c.email like %?2%)")
    List<Employee> findByCompanyAndSearchValue(String company, String searchValue);

}
