package hr.algebra.controller.services.entities;


import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.entities.UnitOfWork;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SmartWatchService  {

    private final UnitOfWork unitOfWork; //public

    //tryWithResources u controller-u za unitOfWork, pa saljemo UOW u service kroz constructor
    public SmartWatchService(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    //connect to controller
    public void create(SmartWatch entity) throws Exception{
        if(entity.getPrice() < 0) throw new Exception("Price cannot be 0 or less"); //Custom Exception
        try{
            unitOfWork.getSmartWatchRepository().save(entity);
            unitOfWork.commit();
        }catch(Exception ex){
            unitOfWork.rollback();
            throw ex;
        }

    }

    //GetOne
    public Optional<SmartWatch> getBy(int id){
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
