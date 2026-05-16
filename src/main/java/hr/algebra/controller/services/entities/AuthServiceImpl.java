package hr.algebra.controller.services.entities;

import hr.algebra.controller.services.AuthService;
import hr.algebra.model.exceptions.InvalidCredentialsException;
import hr.algebra.model.exceptions.UsernameAlreadyExistsException;
import hr.algebra.model.repositories.UnitOfWork;
import hr.algebra.security.entity.Role;
import hr.algebra.security.entity.User;
import hr.algebra.security.passwordHashCheck.BCryptService;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final UnitOfWork unitOfWork;

    public AuthServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void register(String username, String email,String phoneNumber, String plainPassword) throws Exception {

        if(username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Username cannot be empty");
        if(plainPassword == null || plainPassword.length() < 8) throw new IllegalArgumentException("Password must be atleast 8 characters long");

        try{
            Optional<User> existingUser = unitOfWork.getUserRepository().getByUsername(username);
            if(existingUser.isPresent()){
                throw new UsernameAlreadyExistsException("Username " + username + " is taken");
            }

            String hashedPassword = BCryptService.hashPassword(plainPassword);

            User user = new User(username.trim(), email.trim(),phoneNumber, hashedPassword, Role.USER);

            unitOfWork.getUserRepository().save(user);
            unitOfWork.commit();
        } catch(Exception ex){
            unitOfWork.rollback();
            throw ex;
        }
    }

    @Override
    public User login(String username, String plainPassword) throws Exception {
        User user = unitOfWork.getUserRepository().getByUsername(username.trim())
                .orElseThrow(() -> new InvalidCredentialsException("Wrong username or password"));

        if(!BCryptService.checkPassword(plainPassword, user.getPasswordHash())){
            throw new InvalidCredentialsException("Wrong username or password");
        }

        return user;
    }
}
