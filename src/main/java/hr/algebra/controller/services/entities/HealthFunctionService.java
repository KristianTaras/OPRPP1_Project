package hr.algebra.controller.services.entities;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.repositories.entities.UnitOfWork;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class HealthFunctionService {

    private final UnitOfWork _unitOfWork;

    public HealthFunctionService(UnitOfWork _unitOfWork) {
        this._unitOfWork = _unitOfWork;
    }

    public void create(HealthFunction entity) throws Exception {
        try{
            _unitOfWork.getHealthFunctionRepository().save(entity);
            _unitOfWork.commit();
        }catch(Exception ex){
            _unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<HealthFunction> getBy(int id){
        return _unitOfWork.getHealthFunctionRepository().getBy(id);
    }

    public List<HealthFunction> getAll(){
        return _unitOfWork.getHealthFunctionRepository().getAll();
    }

    public void delete(int id) throws Exception {
        _unitOfWork.getHealthFunctionRepository().deleteById(id);
    }

    public boolean exists(HealthFunction entity, int id) throws SQLException {
        return _unitOfWork.getHealthFunctionRepository().exists(entity, id);
    }
}
