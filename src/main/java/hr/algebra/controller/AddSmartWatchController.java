package hr.algebra.controller;

import hr.algebra.model.entities.*;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import hr.algebra.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSmartWatchController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField yearField;
    @FXML private TextField screenSizeField;
    @FXML private TextField batteryField;
    @FXML private TextField ipRatingField;
    @FXML private TextField priceField;
    @FXML private TextField imagePathField;
    @FXML private Label     errorLabel;

    @FXML private StackPane brandDropZone;
    @FXML private Label     brandDropLabel;
    @FXML private StackPane categoryDropZone;
    @FXML private Label     categoryDropLabel;
    @FXML private StackPane osDropZone;
    @FXML private Label     osDropLabel;
    @FXML private VBox      healthDropZone;
    @FXML private Label     healthDropLabel;

    @FXML private FlowPane  brandsPane;
    @FXML private FlowPane  categoriesPane;
    @FXML private FlowPane  osPane;
    @FXML private FlowPane  healthPane;


    private SmartWatch      editWatch = null;
    private Brand           selectedBrand;
    private Category        selectedCategory;
    private OperatingSystem selectedOs;
    private final List<HealthFunction> selectedHealthFunctions = new ArrayList<>();

    private Runnable onSaved;

    private static final String ERROR = "Error";

    public void setOnSaved(Runnable onSaved){ this.onSaved = onSaved; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAvailableEntities();
        setupDropZones();
        if(editWatch != null) populateForEdit();
    }

    private void loadAvailableEntities() {
        try(UnitOfWorkImpl uow = new UnitOfWorkImpl()){

            uow.getBrandRepository().getAll().forEach(brand ->
                    brandsPane.getChildren().add(buildDraggableTag(
                            brand.getName(), "BRAND:" + brand.getId(), "#1a2a3a", "#5b9bd5"
                    )));

            uow.getCategoryRepository().getAll().forEach(cat ->
                    categoriesPane.getChildren().add(buildDraggableTag(
                            cat.getName(), "CATEGORY:" + cat.getId(), "#1a2a1a", "#6fcf6f"
                    )));

            uow.getOperatingSystemRepository().getAll().forEach(os ->
                    osPane.getChildren().add(buildDraggableTag(
                            os.getName(), "OS:" + os.getId(), "#2a1a2a", "#bb6fcf"
                    )));

            uow.getHealthFunctionRepository().getAll().forEach(hf ->
                    healthPane.getChildren().add(buildDraggableTag(
                            hf.getName(), "HF:" + hf.getId(), "#2a1a1a", "#e94560"
                    )));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Label buildDraggableTag(String text, String dragData, String bgColor, String textColor) {

        Label tag = new Label(text);
        tag.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-background-radius: 20;
                -fx-padding: 5 12 5 12;
                -fx-font-size: 11px;
                -fx-cursor: hand;
                """,bgColor, textColor));

        tag.setOnDragDetected(e -> {
            Dragboard db = tag.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(dragData);
            db.setContent(content);
            e.consume();
        });

        tag.setOnMouseEntered(e -> tag.setStyle(String.format("""
                -fx-background-color: derive(%s, 20%%);
                -fx-text-fill: %s;
                -fx-background-radius: 20;
                -fx-padding: 5 12 5 12;
                -fx-font-size: 11px;
                -fx-cursor: hand;
                """, bgColor, textColor)));

        tag.setOnMouseExited(e -> tag.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-background-radius: 20;
                -fx-padding: 5 12 5 12;
                -fx-font-size: 11px;
                -fx-cursor: hand;
                """, bgColor, textColor)));

        return tag;
    }

    private void setupDropZones() {
        setupSingleDropZone(brandDropZone, brandDropLabel, "BRAND");
        setupSingleDropZone(categoryDropZone, categoryDropLabel, "CATEGORY");
        setupSingleDropZone(osDropZone, osDropLabel, "OS");
        setupHealthDropZone();
    }

    private void setupSingleDropZone(StackPane zone, Label label, String prefix) {
        zone.setOnDragOver(e -> {
            if (e.getDragboard().hasString()
                    && e.getDragboard().getString().startsWith(prefix)) {
                e.acceptTransferModes(TransferMode.COPY);
                zone.setStyle(zone.getStyle()
                        .replace("#333", "#e94560")); // highlight border
            }
            e.consume();
        });

        zone.setOnDragExited(e -> {
            zone.setStyle(zone.getStyle().replace("#e94560", "#333"));
            e.consume();
        });

        zone.setOnDragDropped(e -> {
            String data = e.getDragboard().getString();
            int id = Integer.parseInt(data.split(":")[1]);
            handleEntityDropped(prefix, id, label, zone);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    private void setupHealthDropZone() {
        healthDropZone.setOnDragOver(e -> {
            if (e.getDragboard().hasString()
                    && e.getDragboard().getString().startsWith("HF")) {
                e.acceptTransferModes(TransferMode.COPY);
                healthDropZone.setStyle(healthDropZone.getStyle()
                        .replace("#333", "#e94560"));
            }
            e.consume();
        });

        healthDropZone.setOnDragExited(e -> {
            healthDropZone.setStyle(healthDropZone.getStyle()
                    .replace("#e94560", "#333"));
            e.consume();
        });

        healthDropZone.setOnDragDropped(e -> {
            String data = e.getDragboard().getString();
            int id = Integer.parseInt(data.split(":")[1]);
            addHealthFunctionToZone(id);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    private void handleEntityDropped(String type, int id, Label label, StackPane zone) {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            switch (type) {
                case "BRAND" -> {
                    selectedBrand = uow.getBrandRepository().getBy(id).orElse(null);
                    if (selectedBrand != null) updateZoneLabel(label, zone, selectedBrand.getName(), "#5b9bd5");
                }
                case "CATEGORY" -> {
                    selectedCategory = uow.getCategoryRepository().getBy(id).orElse(null);
                    if (selectedCategory != null) updateZoneLabel(label, zone, selectedCategory.getName(), "#6fcf6f");
                }
                case "OS" -> {
                    selectedOs = uow.getOperatingSystemRepository().getBy(id).orElse(null);
                    if (selectedOs != null) updateZoneLabel(label, zone, selectedOs.getName(), "#bb6fcf");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHealthFunctionToZone(int id) {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            HealthFunction hf = uow.getHealthFunctionRepository().getBy(id).orElse(null);
            if (hf == null) return;

            //Check duplicate
            boolean alreadyAdded = selectedHealthFunctions.stream()
                    .anyMatch(h -> h.getId() == id);
            if (alreadyAdded) return;

            selectedHealthFunctions.add(hf);

            healthDropLabel.setVisible(false);
            healthDropLabel.setManaged(false);

            //Tag with X button
            Label tag = new Label(hf.getName() + "  ✕");
            tag.setStyle("""
                    -fx-background-color: #2a1a1a;
                    -fx-text-fill: #e94560;
                    -fx-background-radius: 20;
                    -fx-padding: 5 12 5 12;
                    -fx-font-size: 11px;
                    -fx-cursor: hand;
                    """);

            //Remove health function on X click
            tag.setOnMouseClicked(e -> {
                selectedHealthFunctions.remove(hf);
                healthDropZone.getChildren().remove(tag);
                if (selectedHealthFunctions.isEmpty()) {
                    healthDropLabel.setVisible(true);
                    healthDropLabel.setManaged(true);
                }
            });

            healthDropZone.getChildren().add(tag);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateZoneLabel(Label label, StackPane zone, String name, String color) {
        label.setText("✔ " + name);
        label.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        zone.setStyle(zone.getStyle()
                .replace("dashed", "solid")
                .replace("#333", color));
    }

    @FXML
    private void handleBrowseImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Watch Image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"
                )
        );

        Stage stage = (Stage) nameField.getScene().getWindow();
        File file = chooser.showOpenDialog(stage);

        if (file != null) {
            try {
                //Removes any extension and adds .png
                String baseName = file.getName().replaceAll("\\.[^.]+$", "");
                String destinationName = baseName + ".png";

                Path destinationRuntime = Path.of("src/main/resources/images/" + destinationName);

                BufferedImage bufferedImage = ImageIO.read(file);

                if (bufferedImage == null) {
                    AlertUtil.showError(ERROR, "Unsupported image format: " + file.getName());
                    return;
                }

                ImageIO.write(bufferedImage, "PNG", destinationRuntime.toFile());

                Path destinationTarget = Path.of("target/classes/images/" + destinationName);
                Files.createDirectories(destinationTarget.getParent());
                Files.copy(destinationRuntime, destinationTarget, StandardCopyOption.REPLACE_EXISTING);

                imagePathField.setText("/images/" + destinationName);
                AlertUtil.showInfo("Success", "Image saved as: " + destinationName);

            } catch (Exception e) {
                AlertUtil.showError(ERROR, "Could not process image: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleDeleteImage() {
        String imagePath = imagePathField.getText().trim();

        if (imagePath.isEmpty()) {
            AlertUtil.showWarning("No image", "No image path specified.");
            return;
        }

        if (!AlertUtil.showConfirmationAndWait("Delete image",
                "Delete image: " + imagePath + "?")) return;

        try {
            // Delete from main/resources path
            Path sourcePath = Path.of("src/main/resources" + imagePath);
            Files.deleteIfExists(sourcePath);

            // Delete from target/classes path
            Path targetPath = Path.of("target/classes" + imagePath);
            Files.deleteIfExists(targetPath);

            // Clear the field
            imagePathField.clear();
            AlertUtil.showInfo("Deleted", "Image deleted successfully.");

        } catch (Exception e) {
            AlertUtil.showError("Error", "Could not delete image: " + e.getMessage());
        }
    }


    public void setEditMode(SmartWatch watch){
        this.editWatch = watch;
    }



    private void populateForEdit() {
        nameField.setText(editWatch.getName());
        yearField.setText(String.valueOf(editWatch.getYearOfMaking()));
        screenSizeField.setText(String.valueOf(editWatch.getScreenSize()));
        batteryField.setText(String.valueOf(editWatch.getBatteryLife()));
        ipRatingField.setText(String.valueOf(editWatch.getIpRating()));
        priceField.setText(String.valueOf(editWatch.getPrice()));
        imagePathField.setText(String.valueOf(editWatch.getImagePath()));

        selectedBrand = editWatch.getBrand();
        selectedCategory = editWatch.getCategory();
        selectedOs = editWatch.getOperatingSystem();

        if(selectedBrand != null)
            updateZoneLabel(brandDropLabel, brandDropZone, selectedBrand.getName(), "#5b9bd5");
        if(selectedCategory != null)
            updateZoneLabel(categoryDropLabel, categoryDropZone, selectedCategory.getName(), "#6fcf6f");
        if(selectedOs != null)
            updateZoneLabel(osDropLabel, osDropZone, selectedOs.getName(), "#bb6fcf");

        if(editWatch.getHealthFunctions() != null &&
            !editWatch.getHealthFunctions().isEmpty()) {
            healthDropLabel.setVisible(false);
            healthDropLabel.setManaged(false);

            for(HealthFunction hf : editWatch.getHealthFunctions()){
                selectedHealthFunctions.add(hf);

                Label tag = new Label(hf.getName() + " X");
                tag.setStyle("""
                    -fx-background-color: #2a1a1a;
                    -fx-text-fill: #e94560;
                    -fx-background-radius: 20;
                    -fx-padding: 5 12 5 12;
                    -fx-font-size: 11px;
                    -fx-cursor: hand;
                    """);

                tag.setOnMouseClicked(e -> {
                    selectedHealthFunctions.remove(hf);
                    healthDropZone.getChildren().remove(tag);
                    if(selectedHealthFunctions.isEmpty()){
                        healthDropLabel.setVisible(true);
                        healthDropZone.setManaged(true);
                    }
                });

                healthDropZone.getChildren().add(tag);
            }
        }

    }

    @FXML
    private void handleSave() {
        if (!validate()) return;

        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            int id = editWatch != null ? editWatch.getId() : 0;

            SmartWatch watch = new SmartWatch(
                    id,
                    nameField.getText().trim(),
                    Integer.parseInt(yearField.getText().trim()),
                    Double.parseDouble(screenSizeField.getText().trim()),
                    Integer.parseInt(batteryField.getText().trim()),
                    ipRatingField.getText().trim(),
                    Double.parseDouble(priceField.getText().trim()),
                    imagePathField.getText().trim(),
                    selectedBrand,
                    selectedCategory,
                    selectedOs
            );

            uow.getSmartWatchRepository().save(watch);

            if (editWatch != null) {
                uow.getSmartWatchRepository().deleteSmartWatchHealthFunctions(watch.getId());
            }

            for (HealthFunction hf : selectedHealthFunctions) {
                SmartWatchHealthFunction swhf = new SmartWatchHealthFunction(
                        0, watch.getId(), hf.getId());
                uow.getSmartWatchHealthFunctionRepository().save(swhf);
            }

            uow.commit();
            AlertUtil.showInfo("Success", editWatch == null
                    ? "Smart watch added!" : "Smart watch updated!");
            if (onSaved != null) onSaved.run();
            handleCancel();

        } catch (Exception e) {
            AlertUtil.showError(ERROR,"Save failed: " + e.getMessage());
        }
    }

    private boolean validate() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Name is required.");
            return false;
        }
        if (selectedBrand == null) {
            showError("Please drag a Brand.");
            return false;
        }
        if (selectedCategory == null) {
            showError("Please drag a Category.");
            return false;
        }
        if (selectedOs == null) {
            showError("Please drag an Operating System.");
            return false;
        }
        try {
            Integer.parseInt(yearField.getText().trim());
            Double.parseDouble(screenSizeField.getText().trim());
            Integer.parseInt(batteryField.getText().trim());
            Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Year, Screen Size, Battery and Price must be valid numbers.");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

}
