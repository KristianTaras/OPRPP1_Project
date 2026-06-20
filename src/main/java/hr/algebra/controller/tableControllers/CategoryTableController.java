package hr.algebra.controller.tableControllers;

import hr.algebra.model.entities.Category;
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

public class CategoryTableController implements Initializable {

    @FXML private TableView<Category>            categoryTable;
    @FXML private TableColumn<Category, Integer> colId;
    @FXML private TableColumn<Category, String>  colName;

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
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            categoryTable.setItems(FXCollections.observableArrayList(
                    uow.getCategoryRepository().getAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category getSelected() {
        return categoryTable.getSelectionModel().getSelectedItem();
    }

}
