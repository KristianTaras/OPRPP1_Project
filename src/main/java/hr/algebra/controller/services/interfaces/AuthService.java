package hr.algebra.controller.services.interfaces;

import hr.algebra.model.entities.User;

public interface AuthService {
    void register(String firstName, String lastName, String email, String phoneNumber, String username, String plainPassword) throws Exception;
    User login(String username, String plainPassword) throws Exception;
}
