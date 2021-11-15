package frontend.business;


import frontend.model.CompanyVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class CompanyBD {

    public CompanyBD(){

    }


    public void saveCompany(CompanyVO companyVO){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(companyVO, httpHeaders);
        String url = "http://localhost:8092/newcompany/";
        restTemplate.postForEntity(url,companyVO, String.class);


    }












}
