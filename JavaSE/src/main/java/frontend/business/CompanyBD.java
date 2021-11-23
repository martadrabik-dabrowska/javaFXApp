package frontend.business;


import frontend.model.CompanyVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class CompanyBD {

    public CompanyBD(){

    }

    public ObservableList<CompanyVO> getCompaniesBySearch(String searchValue){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        String url = "http://localhost:8092/companies/{searchValue}";
        ResponseEntity<CompanyVO[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CompanyVO[].class, searchValue);
        return FXCollections.observableArrayList(responseEntity.getBody());
    }

    public void saveCompany(CompanyVO companyVO){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(companyVO, headers);
        String url = "http://localhost:8092/newcompany";

       restTemplate.postForEntity(url,companyVO,String.class);
    }

    public void remove(CompanyVO selectedCompany){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8092/deletecompany/{id}";
        restTemplate.delete(url, selectedCompany.getId());
    }

    public void updateCompany(CompanyVO selectedCompany){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(selectedCompany, headers);

        String url = "http://localhost:8092/updateCompany/";
        restTemplate.postForEntity(url,selectedCompany,CompanyVO.class);
    }
















}
