package hr.algebra.model.repositories;

import hr.algebra.security.entity.User;

import java.util.Optional;

public interface UserRepository extends Repository<User> {
    Optional<User> getByUsername(String username) throws Exception;
}
