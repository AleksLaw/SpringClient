package main.controller;

import main.model.Role;
import main.model.User;
import main.service.ServiceUser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class UserControllerRest {
    @Autowired
    private ServiceUser serviceUser;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/add")
//    public User createUser(@RequestParam("name") String name,
//                           @RequestParam(value = "password", required = false) String password,
//                           @RequestParam("lastName") String lastName,
//                           @RequestParam("age") String age,
//                           @RequestParam("email") String email,
//                           @RequestParam("role") String userRoles) {
//
//        int ageInt = Integer.parseInt(age);
//        HashSet<Role> roles = (HashSet<Role>) getRoles(userRoles);
//        User user = new User(name, passwordEncoder.encode(password), lastName, ageInt, email, roles);
//
//
//
//
//
//        HttpHeaders headers = new HttpHeaders();
//        byte[] encodedAuth = Base64.encodeBase64("1:1".getBytes(Charset.forName("US-ASCII")));
//        String authHeader = "Basic " + new String(encodedAuth);
//        headers.set("Authorization", authHeader);
//        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
//        headers.set("Access-Control-Allow-Origin","*");
//        headers.set("Vary","Origin");
//
//        // Request to return JSON format
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //  headers.set("my_other_key", "my_other_value");
//        // RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        // HttpEntity<Employee[]> entity = new HttpEntity<Employee[]>(headers);
//        HttpEntity<User[]> entity2 = new HttpEntity<User[]>(headers);
//
//
//        // Send request with GET method, and Headers.
//        ResponseEntity<User[]> response = restTemplate.exchange("http://localhost:8080/add", //
//                HttpMethod.POST, entity2, User[].class);
//
//        HttpStatus statusCode = response.getStatusCode();
//
//        // Status Code: 200
//        if (statusCode == HttpStatus.OK) {
//            // Response Body Data
//            User[] list  = response.getBody();
//
//            if (list != null) {
// user =list[0];
//            }
//        }
//
//        return user;
//    }
    @PostMapping("/add")
    public User createUser(@RequestParam("name") String name,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("age") String age,
                           @RequestParam("email") String email,
                           @RequestParam("role") String userRoles) {
        int ageInt = Integer.parseInt(age);
        HashSet<Role> roles = (HashSet<Role>) getRoles(userRoles);
        User user = new User(name, passwordEncoder.encode(password), lastName, ageInt, email, roles);
        return   serviceUser.save(user);
    }

    @PostMapping("/edit")
    public User update(@RequestParam("name") String name,
                       @RequestParam(value = "password", required = false) String password,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("age") String age,
                       @RequestParam("email") String email,
                       @RequestParam("role") String userRoles,
                       @RequestParam("id") String idd) {
        Long id = Long.parseLong(idd);
        int ageInt = Integer.parseInt(age);
        HashSet<Role> roles = (HashSet<Role>) getRoles(userRoles);
        User byName = serviceUser.findById(id).get();
        if (!password.equals("")) {
            byName.setPassword(passwordEncoder.encode(password));
        }
        byName.setId(id);
        byName.setName(name);
        byName.setLastName(lastName);
        byName.setAge(ageInt);
        byName.setEmail(email);
        byName.setUserRoles(roles);
        serviceUser.save(byName);
        return byName;
    }


    @PostMapping("/delete")
    public HttpStatus delete(@RequestParam("id") String idd) {
        Long id = Long.parseLong(idd);
        serviceUser.deleteById(id);
        return HttpStatus.OK;
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") String idd) {
        Long id = Long.parseLong(idd);
        User byId = serviceUser.findById(id).get();
        return byId;
    }

    private Set<Role> getRoles(@RequestParam("role") String role) {
        Set<Role> userRoles = new HashSet<>();
        String[] split = role.split(",");
        for (String s : split) {
            userRoles.add(Role.valueOf(s));
        }
        return userRoles;
    }
//    @Bean
//    public HttpHeaders headers() {
//        HttpHeaders headers = new HttpHeaders();
//        byte[] encodedAuth = Base64.encodeBase64("1:1".getBytes(Charset.forName("US-ASCII")));
//        String authHeader = "Basic " + new String(encodedAuth);
//        headers.set("Authorization", authHeader);
//        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return headers;
//    }
}
