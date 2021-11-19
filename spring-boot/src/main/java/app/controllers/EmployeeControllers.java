package app.controllers;


import app.entities.Employee;
import app.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class EmployeeControllers {

    protected final Logger log = Logger.getLogger(getClass().getName());


    @Autowired
    private EmployeeRepo repo;


    @GetMapping("/employees")
    List<Employee> all(){
        return repo.findAll();
    }

    @GetMapping("/employees/{company}")
    List<Employee> findByCompany(@PathVariable String company) {

        return repo.findByCompany(company);
    }

    @GetMapping("/employees/{company}/{searchValue}")
    List<Employee> findByCompanyAndSearchValue(@PathVariable String company, @PathVariable String searchValue ) {

        return repo.findByCompanyAndSearchValue(company,searchValue);
    }

    @PostMapping("/newemployee")
    Employee newEmployee(@RequestBody Employee newEmployee) {

        log.log(Level.INFO,"Added emplyee {0} ", String.format("%s %s", newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getPosition(), newEmployee.getEmail()));
        return repo.save(newEmployee);
    }

    @DeleteMapping("/deleteemployee/{id}")
    void deleteEmployee(@PathVariable String id) {

        repo.deleteById(id);
        log.log(Level.INFO,"Removed a emplyee  id = {0}", id);
    }
}
