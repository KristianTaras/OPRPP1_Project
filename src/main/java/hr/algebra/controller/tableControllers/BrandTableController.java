package hr.algebra.controller.tableControllers;

import hr.algebra.model.entities.Brand;
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

public class BrandTableController implements Initializable {

    @FXML private TableView<Brand>            brandTable;
    @FXML private TableColumn<Brand, Integer> colId;
    @FXML private TableColumn<Brand, String>  colName;
    @FXML private TableColumn<Brand, String>  colCountry;
    @FXML private TableColumn<Brand, String>  colDescription;

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
        colCountry.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getCountry()));
        colDescription.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDescription()));
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            brandTable.setItems(FXCollections.observableArrayList(
                    uow.getBrandRepository().getAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Brand getSelected() {
        return brandTable.getSelectionModel().getSelectedItem();
    }

}
