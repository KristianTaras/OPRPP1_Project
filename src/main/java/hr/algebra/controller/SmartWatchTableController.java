package hr.algebra.controller;

import hr.algebra.controller.services.SmartWatchService;
import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class SmartWatchTableController implements Initializable {

    @FXML private TableView<SmartWatch>            watchTable;
    @FXML private TableColumn<SmartWatch, String>  colName;
    @FXML private TableColumn<SmartWatch, String>  colBrand;
    @FXML private TableColumn<SmartWatch, Integer> colYear;
    @FXML private TableColumn<SmartWatch, Double>  colPrice;
    @FXML private TableColumn<SmartWatch, Double>  colScreenSize;
    @FXML private TableColumn<SmartWatch, Integer> colBattery;
    @FXML private TableColumn<SmartWatch, String>  colIpRating;
    @FXML private TableColumn<SmartWatch, String>  colOS;
    @FXML private TableColumn<SmartWatch, String>  colCategory;
    @FXML private TableColumn<SmartWatch, String>  colHealth;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadData();
    }

    private void setupColumns() {
        colName.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getName()));

        colBrand.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getBrand() != null
                        ? d.getValue().getBrand().getName() : ""));

        colYear.setCellValueFactory(d ->
                new SimpleObjectProperty<>(d.getValue().getYearOfMaking()));

        colPrice.setCellValueFactory(d ->
                new SimpleObjectProperty<>(d.getValue().getPrice()));

        colScreenSize.setCellValueFactory(d ->
                new SimpleObjectProperty<>(d.getValue().getScreenSize()));

        colBattery.setCellValueFactory(d ->
                new SimpleObjectProperty<>(d.getValue().getBatteryLife()));

        colIpRating.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIpRating())));

        colOS.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getOperatingSystem() != null
                        ? d.getValue().getOperatingSystem().getName() : ""));

        colCategory.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getCategory() != null
                        ? d.getValue().getCategory().getName() : ""));

        colHealth.setCellValueFactory(d -> {
            Set<HealthFunction> fns = d.getValue().getHealthFunctions();
            String val = fns != null
                    ? fns.stream().map(HealthFunction::getName).collect(Collectors.joining(", "))
                    : "";
            return new SimpleStringProperty(val);
        });
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            List<SmartWatch> watches = uow.getSmartWatchRepository().getFullAll();
            watchTable.setItems(FXCollections.observableArrayList(watches));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(List<SmartWatch> watches) {
        watchTable.setItems(FXCollections.observableArrayList(watches));
    }

    // Vraća odabrani sat — koristi AdminMainController za edit/delete
    public SmartWatch getSelectedWatch() {
        return watchTable.getSelectionModel().getSelectedItem();
    }
}
