package hr.algebra.controller.tableControllers;

import hr.algebra.model.entities.HealthFunction;
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

public class HealthFunctionTableController implements Initializable {

    @FXML private TableView<HealthFunction>            healthTable;
    @FXML private TableColumn<HealthFunction, Integer> colId;
    @FXML private TableColumn<HealthFunction, String>  colName;
    @FXML private TableColumn<HealthFunction, String>  colDescription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadData();
    }

    private void setupColumns() {
        colId.setCellValueFactory(d ->
                new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colName.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getName()));
        colDescription.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDescription()));
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            healthTable.setItems(FXCollections.observableArrayList(
                    uow.getHealthFunctionRepository().getAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HealthFunction getSelected() {
        return healthTable.getSelectionModel().getSelectedItem();
    }

}
