package hr.algebra.model.services.entities;


import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.mapper.SmartWatchMapper;
import hr.algebra.model.repositories.entities.SmartWatchRepository;
import hr.algebra.model.repositories.entities.UnitOfWork;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SmartWatchService  {

    private final UnitOfWork _unitOfWork; //public

    //tryWithResources u controlleru za unitOfWork, pa saljemo UOW u service kroz constructor
    public SmartWatchService(UnitOfWork _unitOfWork) {
        this._unitOfWork = _unitOfWork;
    }

    //connect to controller
    public void create(SmartWatch entity) throws Exception{
        if(entity.getPrice() < 0) throw new Exception("Price cannot be 0 or less"); //Custom Exception
        try{
            _unitOfWork.getSmartWatchRepository().save(entity);
            _unitOfWork.commit();
        }catch(Exception ex){
            _unitOfWork.rollback();
            throw ex;
        }

    }

    //GetOne
    public Optional<SmartWatch> getBy(int id){
        return _unitOfWork.getSmartWatchRepository().getBy(id);
    }

    //GetAll
    public List<SmartWatch> getAll(){
        return _unitOfWork.getSmartWatchRepository().getAll();
    }

    //Delete
    public void delete(int id) throws Exception {
        _unitOfWork.getSmartWatchRepository().deleteById(id);
    }

    //Exists
    public boolean exists(SmartWatch entity ,int id) throws SQLException {
        return _unitOfWork.getSmartWatchRepository().exists(entity, id);
    }
}
