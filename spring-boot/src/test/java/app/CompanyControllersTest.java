package app;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import app.controllers.CompanyControllers;
import app.entities.Company;
import app.repository.CompanyRepo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CompanyControllersTest {

    @InjectMocks
    CompanyControllers companyControllers;

    @Mock
    CompanyRepo companyRepo;

    @BeforeClass
    public static void setUp(){

    }

    @Test
    public void findAllTest(){
        //given
        Company company1 = new Company("1", "PostOffice", "London", "111111111");
        Company company2 = new Company("2", "NewYorkTimes", "NewYork", "232324345");
        Company company3 = new Company("3","Tomy's Bakery", "London", "989578495");

        List<Company> companyList = Arrays.asList(company1, company2, company3);

        Mockito.when(companyRepo.findAll()).thenReturn(companyList);

        //when
        List<Company> resultList = companyControllers.allCompanies();

        //then
        assertEquals(company1.getName(), resultList.get(0).getName());
        assertEquals(company2.getName(), resultList.get(1).getName());
        assertEquals(company3.getName(), resultList.get(2).getName());
    }

    @Test
    public void addCompanyTest(){
        //given
        Company company1 = new Company("PostOffice", "London", "111111111");
        Company newCompany = new Company("1", "PostOffice", "London", "111111111");

        Mockito.when(companyRepo.save(company1)).thenReturn(newCompany);

        //when
        Company saveCompany = companyControllers.addNewCompany(company1);

        //then
        assertEquals(saveCompany.getId(), newCompany.getId());
        assertEquals(saveCompany.getName(), newCompany.getName());
    }

    @Test
    public void deleteCompany(){
        //given
        Company company1 = new Company("1", "PostOffice", "London", "111111111");
        Company company2 = new Company("2", "NewYorkTimes", "NewYork", "232324345");
        Company company3 = new Company("3","Tomy's Bakery", "London", "989578495");

        List<Company> companyList = Arrays.asList(company1, company2, company3);
        companyRepo.deleteById(company1.getId());
        Mockito.verify(this.companyRepo, Mockito.times(1)).deleteById(company1.getId());
    }
}
