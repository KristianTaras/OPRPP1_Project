package hr.algebra.model.services.entities;

import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.repositories.entities.UnitOfWork;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OperatingSystemService {

    private final UnitOfWork _unitOfWork;

    public OperatingSystemService(UnitOfWork _unitOfWork) {
        this._unitOfWork = _unitOfWork;
    }

    public void Create(OperatingSystem entity) throws Exception{
        try {
            _unitOfWork.getOperatingSystemRepository().save(entity);
            _unitOfWork.commit();
        } catch( Exception ex){
            _unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<OperatingSystem> getOne(int id){
        return _unitOfWork.getOperatingSystemRepository().getBy(id);
    }

    public List<OperatingSystem> getAll(){
        return _unitOfWork.getOperatingSystemRepository().getAll();
    }

    public void delete(int id) throws Exception {
        _unitOfWork.getOperatingSystemRepository().deleteById(id);
    }

    public boolean exists(OperatingSystem entity, int id) throws SQLException {
        return _unitOfWork.getOperatingSystemRepository().exists(entity, id);
    }

}
