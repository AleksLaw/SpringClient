package main.repository;


import main.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Long> {
    User findByName(String name);
    Optional<User> findById(Long id);
}
