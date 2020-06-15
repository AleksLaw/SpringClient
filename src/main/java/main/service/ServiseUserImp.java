package main.service;

import main.model.User;
import main.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ServiseUserImp implements ServiceUser {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HttpHeaders headers;

    public static List<User> listReq = new ArrayList<>();

    @Override
    public User save(User s) {
        User user =  new User();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<User> entity = new HttpEntity<>(s,headers);
            ResponseEntity<User> exchange = restTemplate.exchange(
                    "http://localhost:8080/add",
                    HttpMethod.POST,
                    entity,
                    User.class);

user=exchange.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

return  user;

       // return userRepo.save(s);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        //    userRepo.deleteById(aLong);
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/del")
                    .queryParam("id", id);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<User[]> entity = new HttpEntity<User[]>(headers);
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
        User user =  new User();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/getByName")
                    .queryParam("name", name);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<User> entity = new HttpEntity<User>(headers);

            ResponseEntity<User> exchange = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    User.class);

            user=exchange.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  user;
    }

    public void start() {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User[]> entity2 = new HttpEntity<User[]>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange("http://localhost:8080/all", //
                HttpMethod.GET, entity2, User[].class);

        HttpStatus statusCode = response.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            User[] list = response.getBody();
            if (list != null) {
                listReq = Arrays.asList(list);
            }
        }
    }
//    @Bean
//    public HttpEntity<String> getHttpEntity() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(Objects.requireNonNull("1", "1"));
//        return new HttpEntity<>(headers);
//    }

}
