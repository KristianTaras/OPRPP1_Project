package hr.algebra.model.repositories.implementations;


import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.repositories.interfaces.UserRepository;
import hr.algebra.model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

    private final RowMapper<User> mapper;
    private final Connection connection;

    public UserRepositoryImpl(Connection connection, String table, RowMapper<User> mapper){
        super(connection, table, mapper);
        this.mapper = mapper;
        this.connection = connection;
    }

    @Override
    public Optional<User> getByUsername(String username) {

            String sql = "SELECT * FROM [user] WHERE username = ?";

            try(PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setString(1, username);

                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return Optional.of(this.mapper.map(rs));
                    } else{
                        System.out.println("getByUsername nothing found!");
                    }
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }

            return Optional.empty();

    }
}
