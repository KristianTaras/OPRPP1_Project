package hr.algebra.model.services.entities;

import hr.algebra.model.entities.Brand;
import hr.algebra.model.repositories.entities.UnitOfWork;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BrandService {
    private final UnitOfWork _unitOfWork;

    public BrandService(UnitOfWork _unitOfWork){
        this._unitOfWork = _unitOfWork;
    }

    public void create(Brand entity) throws Exception {
        try{
            _unitOfWork.getBrandRepository().save(entity);
            _unitOfWork.commit();

        } catch (Exception ex) {
            _unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<Brand> getBy(int id){
        return _unitOfWork.getBrandRepository().getBy(id);
    }

    public List<Brand> getAll(){
        return _unitOfWork.getBrandRepository().getAll();
    }

    public void delete(int id) throws Exception {
        _unitOfWork.getBrandRepository().deleteById(id);
    }

    public boolean exists(Brand entity, int id) throws SQLException {
        return _unitOfWork.getBrandRepository().exists(entity, id);
    }
}
