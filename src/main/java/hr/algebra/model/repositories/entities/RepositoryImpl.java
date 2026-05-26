package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.BaseEntity;
import hr.algebra.model.interfaces.Column;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.interfaces.Transient;
import hr.algebra.model.repositories.Repository;
import hr.algebra.view.util.DatabaseUtil;
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

    public RepositoryImpl(Connection connection, String table,
                          RowMapper<T> mapper){
        this.table = table;
        this.mapper = mapper;
        this.connection = connection;

    }

    //GET ALL
    public List<T> getAll(){

        List<T> entities = new LinkedList<>();
        String sql = String.format("SELECT * FROM [%s]",
                table);

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery())   {

            while(resultSet.next()){
                entities.add(mapper.map(resultSet));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return entities;
    }

    //GET ONE BY ID
    public Optional<T> getBy(int id){

        String sql = String.format("SELECT * FROM [%s] WHERE id = ?",
                                table);

        try(PreparedStatement ps = this.connection.prepareStatement(sql)){

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapper.map(rs));
                }
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<T> getByName(String name) {
        String sql = String.format("SELECT * FROM [%s] WHERE name = ?",
                table);

        try(PreparedStatement ps = this.connection.prepareStatement(sql)){

            ps.setString(1, name);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapper.map(rs));
                }
            }

        } catch(Exception ex){
            ex.printStackTrace();
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
        String tableName = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();

        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        List<Field> fieldsToInsert = new LinkedList<>();

        for(Field field : fields){
            field.setAccessible(true);

            if(field.getName().equalsIgnoreCase("id")){
                continue;
            }
            if(field.isAnnotationPresent(Transient.class)){
                continue;
            }
            fieldsToInsert.add(field);
            columns.add(getColumnName(field));
            placeholders.add("?");
        }

        String sql = String.format("INSERT INTO [%s] (%s) VALUES (%s)",
                                    tableName, columns, placeholders);

        try (PreparedStatement ps = this.connection.prepareStatement(sql,
             Statement.RETURN_GENERATED_KEYS)){
            for(int i = 0; i < fieldsToInsert.size(); i++){
                Field field = fieldsToInsert.get(i);
                Object value = field.get(entity);

                if(value == null){
                    ps.setObject(i+1, Types.VARCHAR);
                }
                else if(value instanceof Enum<?>) {
                    ps.setString(i + 1, ((Enum<?>) value).name());
                }
                else{
                    ps.setObject(i + 1, value);
                }
            }

            System.out.println("SQL: " + sql);
            System.out.println("Class: " + clazz.getSimpleName());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    int newId = rs.getInt(1);
                    idField.set(entity, newId);
                }
            }
        }
    }

    @SuppressWarnings("java:S3011")
    private void updateInternal(T entity, int id) throws SQLException, IllegalAccessException{
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();

        List<Field> fieldsToInsert = new LinkedList<>();

        StringJoiner setClause = new StringJoiner(", ");
        for(Field field : fields){
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase("id")) continue;
            fieldsToInsert.add(field);
            setClause.add(getColumnName(field) +" = ?");
        }

        String sql = String.format("UPDATE [%s] SET %s WHERE id = ?",
                                    tableName, setClause);

        try(PreparedStatement ps = this.connection.prepareStatement(sql)){
            int i = 0;
            for(; i < fieldsToInsert.size(); i++){
                Field field = fieldsToInsert.get(i);
                Object value = field.get(entity);

                if (value == null) {
                    ps.setNull(i + 1, Types.VARCHAR);
                } else if (value instanceof Enum<?>) {
                    ps.setString(i + 1, ((Enum<?>) value).name());
                } else {
                    ps.setObject(i + 1, value);
                }

            }
            ps.setObject(i + 1, id);
            ps.executeUpdate();
        }
    }

    //DELETE BY ID
    public void deleteById(int id) throws Exception {

        String tableName = getBy(id)
                .map(entity -> entity.getClass().getSimpleName())
                .orElseThrow(() -> new Exception("Entity not found"));

        String sql = String.format("DELETE FROM [%s] WHERE id = ?",
                                    tableName);

        try(PreparedStatement ps = this.connection.prepareStatement(sql)){

            ps.setInt(1, id);
            ps.executeUpdate();

        }

    }

    //EXISTS?
    public boolean exists(T entity,int id) throws SQLException{
        //Returns 1 if it finds the entity in database
        String sql = String.format("SELECT TOP 1 1 FROM [%s] WHERE id = ?",
                                    entity.getClass().getSimpleName());
        try(PreparedStatement ps = this.connection.prepareStatement(sql)){
            ps.setObject(1, id);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }


    private String getColumnName(Field field) {
        Column col = field.getAnnotation(Column.class);
        return col != null ? col.name() : field.getName();
    }
}
