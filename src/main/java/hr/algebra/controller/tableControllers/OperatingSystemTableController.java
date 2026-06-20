package hr.algebra.controller.tableControllers;

import hr.algebra.model.entities.OperatingSystem;
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

public class OperatingSystemTableController implements Initializable {

    @FXML
    private TableView<OperatingSystem> osTable;
    @FXML
    private TableColumn<OperatingSystem, Integer> colId;
    @FXML
    private TableColumn<OperatingSystem, String> colName;
    @FXML
    private TableColumn<OperatingSystem, String> colVersion;
    @FXML
    private TableColumn<OperatingSystem, String> colDeveloper;

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
        colVersion.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getVersion()));
        colDeveloper.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDeveloper()));
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            osTable.setItems(FXCollections.observableArrayList(
                    uow.getOperatingSystemRepository().getAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OperatingSystem getSelected() {
        return osTable.getSelectionModel().getSelectedItem();
    }
}
