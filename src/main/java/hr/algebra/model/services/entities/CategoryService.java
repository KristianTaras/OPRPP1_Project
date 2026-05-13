package hr.algebra.model.services.entities;

import hr.algebra.model.entities.Category;
import hr.algebra.model.repositories.entities.UnitOfWork;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoryService {

    private final UnitOfWork _unitOfWork;

    public CategoryService(UnitOfWork _unitOfWork) {
        this._unitOfWork = _unitOfWork;
    }

    public void create(Category entity) throws Exception {
        try{
            _unitOfWork.getCategoryRepository().save(entity);
            _unitOfWork.commit();
        } catch(Exception ex){
            _unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<Category> getBy(int id){
        return _unitOfWork.getCategoryRepository().getBy(id);
    }

    public List<Category> getAll(){
        return _unitOfWork.getCategoryRepository().getAll();
    }

    public void delete(int id) throws Exception {
        _unitOfWork.getCategoryRepository().deleteById(id);
    }

    public boolean exists(Category entity, int id) throws SQLException {
        return _unitOfWork.getCategoryRepository().exists(entity, id);
    }
}
