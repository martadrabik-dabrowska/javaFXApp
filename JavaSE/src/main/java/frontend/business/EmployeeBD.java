package frontend.business;


import frontend.model.CompanyVO;
import frontend.model.EmployeeVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class EmployeeBD {

    public void saveEmployee(EmployeeVO employeeVO){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(employeeVO, httpHeaders);
        String url = "http://localhost:8092/newemployee";
        restTemplate.postForEntity(url,employeeVO, String.class);
    }
    public void remove(EmployeeVO selectedEmployee){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8092/deleteemployee/{id}";
        restTemplate.delete(url, selectedEmployee.getId());
    }
}
