package com.app.example.repository;

import com.app.example.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company,String> {

    List<Company> findAll();

    @Query(nativeQuery = true, value = "SELECT c.* FROM Company c WHERE c.name LIKE %?1% OR c.address like %?1% OR c.nip like %?1%")
    List<Company> findByValue(String value);
}
