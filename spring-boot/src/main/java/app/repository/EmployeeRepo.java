package app.repository;

import app.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,String> {

    List<Employee> findAll();

    @Query(nativeQuery = true, value = "SELECT  c.* FROM employee c WHERE c.company = ?1")
    List<Employee> findByCompany(String company);

    @Query(nativeQuery = true, value = "SELECT  c.* FROM employee c WHERE c.company = ?1 and (c.firstName LIKE %?2% OR c.lastName like %?2% OR c.position like %?2% OR c.email like %?2%)")
    List<Employee> findByCompanyAndSearchValue(String company, String searchValue);

}
