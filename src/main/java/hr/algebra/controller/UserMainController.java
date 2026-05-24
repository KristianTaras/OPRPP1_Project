package hr.algebra.controller;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;
import hr.algebra.view.App;
import hr.algebra.view.util.SceneUtil;
import javafx.collections.FXCollections;
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
    @FXML private Label userLabel;
    @FXML private TextField searchField;

    private SmartWatchTableController tableController;
    private FilteredList<SmartWatch> filteredWatches;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSmartWatches();
    }

    // ── Navigacija ──────────────────────────────────────────────

    @FXML
    private void handleSmartWatches() {
        tableController = new SmartWatchTableController();
        loadView("/fxml/SmartWatchTable.fxml", tableController);
    }

    @FXML
    private void handleAbout() {
        // loadView("/fxml/About.fxml", new AboutController());
    }

    // ── Search ──────────────────────────────────────────────────

    @FXML
    private void handleSearch() {
        if (tableController == null) return;

        String query = searchField.getText().trim().toLowerCase();

        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            List<SmartWatch> all = uow.getSmartWatchRepository().getFullAll();

            List<SmartWatch> filtered = all.stream()
                    .filter(w -> query.isEmpty()
                            || w.getName().toLowerCase().contains(query)
                            || (w.getBrand() != null && w.getBrand().getName().toLowerCase().contains(query))
                            || (w.getCategory() != null && w.getCategory().getName().toLowerCase().contains(query)))
                    .toList();

            tableController.setData(filtered);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Logout ──────────────────────────────────────────────────

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
}
