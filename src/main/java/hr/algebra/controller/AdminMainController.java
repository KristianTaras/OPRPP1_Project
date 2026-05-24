package hr.algebra.controller;


import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;
import hr.algebra.view.App;
import hr.algebra.view.util.AlertUtil;
import hr.algebra.view.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    @FXML private StackPane contentPane;
    @FXML private Label statusLabel;
    @FXML private Label adminLabel;

    private SmartWatchTableController currentTableController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSmartWatches();
    }

    // ── Navigacija ──────────────────────────────────────────────

    @FXML
    private void handleSmartWatches() {
        currentTableController = new SmartWatchTableController();
        loadView("/fxml/SmartWatchTable.fxml", currentTableController);
        setStatus("Showing: Smart Watches");
    }

    @FXML
    private void handleBrands() {
        currentTableController = null;
        setStatus("Showing: Brands");
    }

    @FXML
    private void handleCategories() {
        currentTableController = null;
        setStatus("Showing: Categories");
    }

    @FXML
    private void handleUsers() {
        currentTableController = null;
        setStatus("Showing: Users");
    }

    // ── CRUD ────────────────────────────────────────────────────

    @FXML
    private void handleAdd() {
        setStatus("Add — not yet implemented");
    }

    @FXML
    private void handleEdit() {
        if (currentTableController == null) return;
        SmartWatch selected = currentTableController.getSelectedWatch();
        if (selected == null) {
            AlertUtil.showWarning("No selection", "Please select a smart watch to edit.");
            return;
        }
        setStatus("Edit: " + selected.getName() + " — not yet implemented");
    }

    @FXML
    private void handleDelete() {
        if (currentTableController == null) return;
        SmartWatch selected = currentTableController.getSelectedWatch();
        if (selected == null) {
            AlertUtil.showWarning("No selection", "Please select a smart watch to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete");
        confirm.setHeaderText("Delete: " + selected.getName());
        confirm.setContentText("Are you sure?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
                uow.getSmartWatchRepository().deleteById(selected.getId());
                uow.commit();
                setStatus("Deleted: " + selected.getName());
                handleSmartWatches(); // refresh
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.showError("Error", "Failed to delete: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleExportXml() {
        if (currentTableController == null) return;
        SmartWatch selected = currentTableController.getSelectedWatch();
        if (selected == null) {
            AlertUtil.showWarning("No selection", "Please select a smart watch to export.");
            return;
        }

        try {
            String path = "src/main/resources/XMLFiles/SmartWatch_" + selected.getId() + ".xml";
            //XMLParser.saveToXml(selected, path);
            setStatus("Exported: " + selected.getName() + " → " + path);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Export failed", e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        SceneUtil.loadScene(
                App.class.getResource("/fxml/login.fxml"),
                stage,
                "Login",
                new LoginController()
        );
    }

    // ── Helpers ─────────────────────────────────────────────────

    private void loadView(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            loader.setController(controller);
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
            setStatus("Failed to load view: " + e.getMessage());
        }
    }

    private void setStatus(String message) {
        if (statusLabel != null) statusLabel.setText(message);
    }
}
