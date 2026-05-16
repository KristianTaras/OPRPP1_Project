package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.BaseEntity;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.repositories.Repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class RepositoryImpl<T extends BaseEntity> implements Repository<T> {

    private final String table;
    private final RowMapper<T> mapper;
    private final Connection connection;

    public RepositoryImpl(String table,
                          RowMapper<T> mapper,
                          Connection connection) {

        this.table = table;
        this.mapper = mapper;
        this.connection = connection;
    }

    //GET ALL
    public List<T> getAll(){

        List<T> entities = new LinkedList<>();
        String sql = "SELECT * FROM " + table;

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            var rs = stmt.executeQuery();

            while(rs.next()){
                entities.add(mapper.map(rs));
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            //Create custom exception
        }
        return entities;
    }

    //GET ONE BY ID
    public Optional<T> getBy(int id){

        String sql = "SELECT * FROM " + table + " WHERE id = ? ";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setObject(1, id);
            //it replaces the first ? in SQL with 2nd property, in this case ID
            //setObject works with any type int, long, string,
            //UUID (Universal Unique Identifier) - globally unique ID generated without database dependancy
            try(var rs = stmt.executeQuery()) {

                if(rs.next()){
                    return Optional.of(mapper.map(rs));
                }

            }
        }catch (SQLException ex){
            ex.printStackTrace();
            //Better logging
        }
        return Optional.empty();
    }

    //CREATE
    //Suppresses SolanQube warning about setting private fields to accessible, suppress casting warning
    @SuppressWarnings({"java:S3011","unchecked"})
    public void save(T entity) throws Exception {
        if(entity == null) return;//throw new SentEntityWasEmptyException

        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        int currentId = (int) idField.get(entity);

        if(!exists(entity, currentId)){
            createInternal(entity, idField);
        }else{
            updateInternal(entity,currentId);
        }


    }

    //More specific exception needed
    @SuppressWarnings("java:S3011")
    private void createInternal(T entity, Field idField) throws Exception {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        Field[] fields = clazz.getDeclaredFields();

        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        for(Field field : fields){
            field.setAccessible(true);
            columns.add(field.getName());
            placeholders.add("?");
        }

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                                    tableName, columns, placeholders);

        try (PreparedStatement pStmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)){
            for(int i = 0; i < fields.length; i++){
                pStmt.setObject(i+1, fields[i].get(entity)); //setObject should automatically CAST into the needed SQL data type
            }

            pStmt.executeUpdate();

            try(ResultSet rs = pStmt.getGeneratedKeys()){
                if(rs.next()){
                    Object newId = rs.getObject(1);
                    idField.set(entity, newId);
                }
            }
        }
    }

    @SuppressWarnings("java:S3011")
    private void updateInternal(T entity, int id) throws SQLException, IllegalAccessException{
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        Field[] fields = clazz.getDeclaredFields();

        StringJoiner setClause = new StringJoiner(", ");
        for(Field field : fields){
            field.setAccessible(true);
            setClause.add(field.getName() + " = ?");
        }

        String sql = String.format("UPDATE %s SET %s WHERE id = ?",
                                    tableName, setClause);

        try(PreparedStatement pStmt = connection.prepareStatement(sql)){
            int i = 0;
            for(; i < fields.length; i++){
                pStmt.setObject(i + 1, fields[i].get(entity));
            }
            pStmt.setObject(i + 1, id);
            pStmt.executeUpdate();
        }
    }

    //DELETE BY ID
    public void deleteById(int id) throws Exception {

        String tableName = getBy(id)
                .map(entity -> entity.getClass().getSimpleName().toLowerCase())
                .orElseThrow(() -> new Exception("Entity not found"));


            String sql = String.format("DELETE FROM %s WHERE id = ?",
                                        tableName);
        try(PreparedStatement pStmt = connection.prepareStatement(sql)){
            pStmt.setInt(1, id);
            pStmt.executeUpdate();

        }

    }

    //EXISTS?
    public boolean exists(T entity,int id) throws SQLException{
        //Returns 1 if it finds the entity in database
        String sql = String.format("SELECT 1 FROM %s WHERE id = ? LIMIT 1",
                                    entity.getClass().getSimpleName().toLowerCase());
        try(PreparedStatement pStmt = connection.prepareStatement(sql)){
            pStmt.setObject(1, id);
            try(ResultSet rs = pStmt.executeQuery()){
                return rs.next();
            }
        }
    }
}
