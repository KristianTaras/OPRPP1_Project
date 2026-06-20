package hr.algebra.controller;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.entities.SmartWatch;
import hr.algebra.view.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SmartWatchDetailController implements Initializable {

    private final SmartWatch watch;

    @FXML private StackPane headerPane;
    @FXML private Label     watchEmoji;
    @FXML private Label     watchName;
    @FXML private Label     watchBrand;
    @FXML private Label     watchPrice;
    @FXML private Label     watchScreen;
    @FXML private Label     watchBattery;
    @FXML private Label     watchIp;
    @FXML private Label     watchOS;
    @FXML private Label     watchOsTypes;
    @FXML private Label     categoryBadge;
    @FXML private Label     brandName;
    @FXML private Label     brandCountry;
    @FXML private Label     brandDescription;
    @FXML private FlowPane  healthPane;
    @FXML private VBox      descriptionBox;
    @FXML private Label     descriptionTitle;
    @FXML private Label     descriptionText;

    private Label selectedTag = null;

    private static final String NORMAL_TAG_STYLE = """
        -fx-background-color: #1e2a3a;
        -fx-text-fill: #5b9bd5;
        -fx-background-radius: 20;
        -fx-padding: 5 12 5 12;
        -fx-font-size: 11px;
        -fx-cursor: hand;
        """;

    private static final String HOVER_TAG_STYLE = """
        -fx-background-color: #243550;
        -fx-text-fill: #7ab8f5;
        -fx-background-radius: 20;
        -fx-padding: 5 12 5 12;
        -fx-font-size: 11px;
        -fx-cursor: hand;
        """;

    private static final String SELECTED_TAG_STYLE = """
        -fx-background-color: #2a4a2a;
        -fx-text-fill: #6fcf6f;
        -fx-background-radius: 20;
        -fx-padding: 5 12 5 12;
        -fx-font-size: 11px;
        -fx-cursor: hand;
        """;



    public SmartWatchDetailController(SmartWatch watch) {
        this.watch = watch;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populate();
    }

    private void populate() {
        // Slika u headeru
        loadHeaderImage(watch.getImagePath());

        watchName.setText(watch.getName());

        String brandYear = (watch.getBrand() != null ? watch.getBrand().getName() : "Unknown")
                + " · " + watch.getYearOfMaking();
        watchBrand.setText(brandYear);

        watchPrice.setText(String.format("€%.2f", watch.getPrice()));
        watchScreen.setText(watch.getScreenSize() + " in");
        watchBattery.setText(watch.getBatteryLife() + " h");
        watchIp.setText(watch.getIpRating() != null ? watch.getIpRating() : "—");

        if (watch.getOperatingSystem() != null) {
            watchOS.setText(watch.getOperatingSystem().getName()
                    + " " + watch.getOperatingSystem().getVersion());
        }

        if (watch.getSmartWatchOperatingSystem() != null && !watch.getSmartWatchOperatingSystem().isEmpty()) {
            String types = watch.getSmartWatchOperatingSystem().stream()
                    .map(os -> os.getName())
                    .collect(Collectors.joining(", "));
            watchOsTypes.setText(types);
        }

        if (watch.getCategory() != null) {
            categoryBadge.setText(watch.getCategory().getName().toUpperCase());
        }

        if (watch.getBrand() != null) {
            brandName.setText(watch.getBrand().getName());
            brandCountry.setText(watch.getBrand().getCountry());
            brandDescription.setText(watch.getBrand().getDescription());
        }

        if (watch.getHealthFunctions() != null) {
            for (HealthFunction hf : watch.getHealthFunctions()) {
                healthPane.getChildren().add(buildHealthTag(hf));
            }
        }
    }

    private void loadHeaderImage(String imagePath) {

        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                InputStream is = App.class.getResourceAsStream(imagePath);
                if (is != null) {
                    Image img = new Image(is);
                    ImageView iv = new ImageView(img);
                    iv.setFitWidth(150);
                    iv.setFitHeight(150);
                    iv.setPreserveRatio(true);
                    watchEmoji.setVisible(false);
                    watchEmoji.setManaged(false);
                    headerPane.getChildren().add(0, iv);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace(); //Custom
            }
        }
        watchEmoji.setVisible(true);
    }

    private Label buildHealthTag(HealthFunction hf) {
        Label tag = new Label(hf.getName());
        tag.setStyle(NORMAL_TAG_STYLE);
        tag.setCursor(Cursor.HAND);

        tag.setOnMouseClicked(e -> {
            if (selectedTag == tag) {
                tag.setStyle(NORMAL_TAG_STYLE);
                selectedTag = null;
                hideDescription();
            } else {
                if (selectedTag != null) selectedTag.setStyle(NORMAL_TAG_STYLE);
                tag.setStyle(SELECTED_TAG_STYLE);
                selectedTag = tag;
                showDescription(hf.getName(), hf.getDescription());
            }
        });

        tag.setOnMouseEntered(e -> { if (selectedTag != tag) tag.setStyle(HOVER_TAG_STYLE); });
        tag.setOnMouseExited(e ->  { if (selectedTag != tag) tag.setStyle(NORMAL_TAG_STYLE); });

        return tag;
    }

    private void showDescription(String name, String description) {
        descriptionTitle.setText(name);
        descriptionText.setText(description != null && !description.isEmpty()
                ? description : "No description available.");
        descriptionBox.setVisible(true);
        descriptionBox.setManaged(true);
    }

    private void hideDescription() {
        descriptionBox.setVisible(false);
        descriptionBox.setManaged(false);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) watchName.getScene().getWindow();
        stage.close();
    }
}