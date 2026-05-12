package hr.algebra.model.services.entities;


import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.mapper.SmartWatchMapper;
import hr.algebra.model.repositories.entities.SmartWatchRepository;

import java.net.ConnectException;
import java.sql.Connection;

public class SmartWatchService  {

    private final SmartWatchRepository repository;

    public SmartWatchService(Connection connection) {
        this.repository = new SmartWatchRepository(connection);
    }

    public void create(SmartWatch entity) throws Exception{
        if(entity.getPrice() < 0) throw new Exception("Price cannot be 0 or less"); //Custom Exception

        repository.save(entity);
    }
}
