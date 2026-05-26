package hr.algebra.controller;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;
import hr.algebra.view.App;
import hr.algebra.view.util.SceneUtil;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserMainController implements Initializable {

    @FXML private StackPane contentPane;
    @FXML private Label statusLabel;
    @FXML private TextField searchField;

    private SmartWatchGridController currentGrid;
    private FilteredList<SmartWatch> filteredWatches;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSmartWatches();
    }

    // ── Navigacija ──────────────────────────────────────────────

    @FXML
    private void handleSmartWatches() {
        SmartWatchGridController grid = new SmartWatchGridController();
        loadView("/fxml/SmartWatchGrid.fxml", grid);
        setStatus("Showing: Smart Watches");
    }

    @FXML
    private void handleAbout() {
        // loadView("/fxml/About.fxml", new AboutController());
    }

    // ── Search ──────────────────────────────────────────────────

    @FXML
    private void handleSearch() {
        if (currentGrid == null) return;

        String query = searchField.getText().trim().toLowerCase();

        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            List<SmartWatch> all = uow.getSmartWatchRepository().getFullAll();

            List<SmartWatch> filtered = all.stream()
                    .filter(w -> query.isEmpty()
                            || w.getName().toLowerCase().contains(query)
                            || (w.getBrand() != null && w.getBrand().getName().toLowerCase().contains(query))
                            || (w.getCategory() != null && w.getCategory().getName().toLowerCase().contains(query)))
                    .toList();

            currentGrid.setData(filtered);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBrands() {
        currentGrid = null;
        setStatus("Showing: Brands");
    }

    @FXML
    private void handleCategories() {
        currentGrid = null;
        setStatus("Showing: Categories");
    }

    @FXML
    private void handleUsers() {
        currentGrid = null;
        setStatus("Showing: Users");
    }

    // ── Logout ──────────────────────────────────────────────────

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        SceneUtil.loadScene(
                App.class.getResource("/fxml/Login.fxml"),
                stage,
                "Login",
                new LoginController()
        );
    }

    // ── Helper ──────────────────────────────────────────────────

    private void loadView(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            loader.setController(controller);
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatus(String message) {
        if (statusLabel != null) statusLabel.setText(message);
    }
}
