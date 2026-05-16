package hr.algebra.controller.services;

import hr.algebra.security.entity.Role;
import hr.algebra.security.entity.User;

public interface AuthService {
    void register(String username, String email,String phoneNumber, String plainPassword) throws Exception;
    User login(String username, String plainPassword) throws Exception;
}
