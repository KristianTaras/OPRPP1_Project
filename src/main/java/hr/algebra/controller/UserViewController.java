package hr.algebra.controller;

import hr.algebra.view.App;
import hr.algebra.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML private StackPane contentPane;
    @FXML private Label statusLabel;
    @FXML private TextField searchField;

    private SmartWatchGridController currentGrid;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSmartWatches();
    }

    // ── Navigacija ──────────────────────────────────────────────

    @FXML
    private void handleSmartWatches() {
        currentGrid = new SmartWatchGridController();
        loadView("/fxml/SmartWatchGridView.fxml", currentGrid);
        setStatus("Showing: Smart Watches");

    }

    @FXML
    private void handleCompare() {
        HealthFunctionCompareController controller = new HealthFunctionCompareController();
        loadView("/fxml/HealthFunctionCompare.fxml", controller);
    }

    @FXML
    private void handleAbout() {
        // loadView("/fxml/About.fxml", new AboutController());
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
