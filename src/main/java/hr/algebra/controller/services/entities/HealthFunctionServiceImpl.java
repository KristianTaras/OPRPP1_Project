package hr.algebra.controller.services.entities;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.repositories.UnitOfWork;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class HealthFunctionServiceImpl {

    private final UnitOfWork unitOfWork;

    public HealthFunctionServiceImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void create(HealthFunction entity) throws Exception {
        try{
            unitOfWork.getHealthFunctionRepository().save(entity);
            unitOfWork.commit();
        }catch(Exception ex){
            unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<HealthFunction> getBy(int id){
        return unitOfWork.getHealthFunctionRepository().getBy(id);
    }

    public List<HealthFunction> getAll(){
        return unitOfWork.getHealthFunctionRepository().getAll();
    }

    public void delete(int id) throws Exception {
        unitOfWork.getHealthFunctionRepository().deleteById(id);
    }

    public boolean exists(HealthFunction entity, int id) throws SQLException {
        return unitOfWork.getHealthFunctionRepository().exists(entity, id);
    }
}
