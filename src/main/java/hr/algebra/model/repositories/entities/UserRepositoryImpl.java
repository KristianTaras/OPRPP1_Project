package hr.algebra.model.repositories.entities;


import hr.algebra.model.mapper.UserMapper;
import hr.algebra.model.repositories.UserRepository;
import hr.algebra.security.entity.User;

import java.sql.Connection;
import java.util.Optional;

public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl( Connection connection) {
        super("User",new UserMapper() , connection);
    }

    @Override
    public Optional<User> getByUsername(String username) throws Exception {
        return Optional.empty(); //Finish
    }
}
