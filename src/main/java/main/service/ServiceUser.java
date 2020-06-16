package main.service;

import main.model.User;

public interface ServiceUser {

    User save(User user);

    User findById(Long id);

    Iterable<User> findAll();

    void deleteById(Long aLong);

    User findByName(String name);
}