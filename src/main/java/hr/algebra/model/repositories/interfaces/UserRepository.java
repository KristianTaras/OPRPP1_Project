package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.entities.User;

import java.util.Optional;

public interface UserRepository extends Repository<User> {
    Optional<User> getByUsername(String username) throws Exception;
}
