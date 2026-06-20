package hr.algebra.controller.tableControllers;

import hr.algebra.model.entities.User;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserTableController implements Initializable {

    @FXML private TableView<User>            userTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String>  colFirstName;
    @FXML private TableColumn<User, String>  colLastName;
    @FXML private TableColumn<User, String>  colUsername;
    @FXML private TableColumn<User, String>  colEmail;
    @FXML private TableColumn<User, String>  colPhone;
    @FXML private TableColumn<User, String>  colRole;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadData();
    }

    private void setupColumns() {
        colId.setCellValueFactory(d ->
                new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colFirstName.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFirstName()));
        colLastName.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getLastName()));
        colUsername.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getUsername()));
        colEmail.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEmail()));
        colPhone.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getPhoneNumber()));
        colRole.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getRole() != null
                        ? d.getValue().getRole().name() : ""));
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            userTable.setItems(FXCollections.observableArrayList(
                    uow.getUserRepository().getAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getSelected() {
        return userTable.getSelectionModel().getSelectedItem();
    }

}
