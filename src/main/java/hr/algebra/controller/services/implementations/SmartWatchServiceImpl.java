package hr.algebra.controller.services.implementations;


import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.interfaces.UnitOfWork;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SmartWatchServiceImpl {

    private final UnitOfWork unitOfWork; //public

    //tryWithResources in controller for unitOfWork, then we send UOW to services through constructor
    public SmartWatchServiceImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    //connect to controller
    public void create(SmartWatch entity) throws Exception{
        if(entity.getPrice() < 0) throw new SQLException("Price cannot be 0 or less"); //Custom Exception
        try{
            unitOfWork.getSmartWatchRepository().save(entity);
            unitOfWork.commit();
        }catch(Exception ex){
            unitOfWork.rollback();
            throw ex;
        }

    }

    //GetOne
    public Optional<SmartWatch> getBy(int id) throws SQLException {
        return unitOfWork.getSmartWatchRepository().getBy(id);
    }

    //GetAll
    public List<SmartWatch> getAll(){
        return unitOfWork.getSmartWatchRepository().getAll();
    }

    //Delete
    public void delete(int id) throws Exception {
        unitOfWork.getSmartWatchRepository().deleteById(id);
    }

    //Exists
    public boolean exists(SmartWatch entity ,int id) throws SQLException {
        return unitOfWork.getSmartWatchRepository().exists(entity, id);
    }
}
