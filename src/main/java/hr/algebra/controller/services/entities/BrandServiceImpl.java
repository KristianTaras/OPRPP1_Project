package hr.algebra.controller.services.entities;

import hr.algebra.model.entities.Brand;
import hr.algebra.model.repositories.UnitOfWork;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BrandServiceImpl {
    private final UnitOfWork unitOfWork;

    public BrandServiceImpl(UnitOfWorkImpl unitOfWork){
        this.unitOfWork = unitOfWork;
    }

    public void create(Brand entity) throws Exception {
        try{
            unitOfWork.getBrandRepository().save(entity);
            unitOfWork.commit();

        } catch (Exception ex) {
            unitOfWork.rollback();
            throw ex;
        }
    }

    public Optional<Brand> getBy(int id){
        return unitOfWork.getBrandRepository().getBy(id);
    }

    public List<Brand> getAll(){
        return unitOfWork.getBrandRepository().getAll();
    }

    public void delete(int id) throws Exception {
        unitOfWork.getBrandRepository().deleteById(id);
    }

    public boolean exists(Brand entity, int id) throws SQLException {
        return unitOfWork.getBrandRepository().exists(entity, id);
    }
}
