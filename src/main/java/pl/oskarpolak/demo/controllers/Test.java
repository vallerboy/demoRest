package pl.oskarpolak.demo.controllers;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();


        for (int i = 0; i < 100; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("user", UUID.randomUUID().toString().substring(0, 6));
            map.add("password", UUID.randomUUID().toString().substring(0, 8));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);


            restTemplate.postForEntity("http://168.63.66.225:8080/register", request, String.class);
            System.out.println("Mamy userów: " + i);
        }
    }

    @Data
    public static class User {
        private String user;
        private String password;
    }
}
