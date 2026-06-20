package hr.algebra.controller.services.implementations;

import hr.algebra.model.entities.Category;
import hr.algebra.model.repositories.interfaces.UnitOfWork;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl {

    private final UnitOfWork unitOfWork;

    public CategoryServiceImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void create(Category entity) throws Exception {
        try{
            unitOfWork.getCategoryRepository().save(entity);
            unitOfWork.commit();
        } catch(Exception ex){
            unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<Category> getBy(int id) throws SQLException {
        return unitOfWork.getCategoryRepository().getBy(id);
    }

    public List<Category> getAll(){
        return unitOfWork.getCategoryRepository().getAll();
    }

    public void delete(int id) throws Exception {
        unitOfWork.getCategoryRepository().deleteById(id);
    }

    public boolean exists(Category entity, int id) throws SQLException {
        return unitOfWork.getCategoryRepository().exists(entity, id);
    }
}
