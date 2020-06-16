package main.service;

import main.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ServiseUserImp implements ServiceUser {

    @Autowired
    private HttpHeaders headers;

    public static List<User> listReq = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public User save(User s) {
        User user = new User();
        try {
            HttpEntity<User> entity = new HttpEntity<>(s, headers);
            ResponseEntity<User> exchange = restTemplate.exchange(
                    "http://localhost:8080/add",
                    HttpMethod.POST,
                    entity,
                    User.class);
            user = exchange.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/findById")
                    .queryParam("id", id);
            HttpEntity<User> entity = new HttpEntity<>(headers);
            ResponseEntity<User> exchange = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    User.class);
            user = exchange.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() {

        HttpEntity<User[]> entity2 = new HttpEntity<>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange("http://localhost:8080/all", //
                HttpMethod.GET, entity2, User[].class);
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            User[] list = response.getBody();
            if (list != null) {
                return listReq = Arrays.asList(list);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/delete")
                    .queryParam("id", id);
            HttpEntity<User> entity = new HttpEntity<>(headers);
            restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User findByName(String name) {
        User user = new User();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/getByName")
                    .queryParam("name", name);
            HttpEntity<User> entity = new HttpEntity<>(headers);
            ResponseEntity<User> exchange = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    User.class);
            user = exchange.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }
}
