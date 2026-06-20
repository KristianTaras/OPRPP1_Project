package hr.algebra.controller.services.implementations;

import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.repositories.interfaces.UnitOfWork;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OperatingSystemServiceImpl {

    private final UnitOfWork unitOfWork;

    public OperatingSystemServiceImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void create(OperatingSystem entity) throws Exception{
        try {
            unitOfWork.getOperatingSystemRepository().save(entity);
            unitOfWork.commit();
        } catch( Exception ex){
            unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<OperatingSystem> getOne(int id) throws SQLException {
        return unitOfWork.getOperatingSystemRepository().getBy(id);
    }

    public List<OperatingSystem> getAll(){
        return unitOfWork.getOperatingSystemRepository().getAll();
    }

    public void delete(int id) throws Exception {
        unitOfWork.getOperatingSystemRepository().deleteById(id);
    }

    public boolean exists(OperatingSystem entity, int id) throws SQLException {
        return unitOfWork.getOperatingSystemRepository().exists(entity, id);
    }

}
