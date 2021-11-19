package app.controllers;


import app.entities.Company;
import app.repository.CompanyRepo;
import app.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CompanyControllers {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/companies")
    List<Company> allCompanies (){
        return companyRepo.findAll();
    }

    @GetMapping("/companies/{value}")
    List<Company> findByValue(@PathVariable String value){
        return  companyRepo.findByValue(value);
    }

    @PostMapping("/newcompany")
    Company newEmployee(@RequestBody Company newCompany){
        logger.log(Level.INFO, "Added a new company {0}", newCompany.getName());
        return companyRepo.save(newCompany);
    }
    @DeleteMapping("/deletecompany/{id}")
    void deleteCompany(@PathVariable String id){
        logger.log(Level.INFO, "Removed company id = {0}", id);
        companyRepo.deleteById(id);
    }


}