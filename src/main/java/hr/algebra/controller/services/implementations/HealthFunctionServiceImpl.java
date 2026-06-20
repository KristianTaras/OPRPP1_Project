package hr.algebra.controller.services.implementations;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.repositories.interfaces.UnitOfWork;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;

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

    public Optional<HealthFunction> getBy(int id) throws SQLException {
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
