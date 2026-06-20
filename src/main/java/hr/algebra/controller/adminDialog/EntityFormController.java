package hr.algebra.controller.adminDialog;

import hr.algebra.model.entities.*;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import hr.algebra.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class EntityFormController implements Initializable {

    @FXML private Label  titleLabel;
    @FXML private VBox   fieldsContainer;
    @FXML private Label  errorLabel;

    public enum EntityType { SMARTWATCH, BRAND, CATEGORY, HEALTH_FUNCTION, OPERATING_SYSTEM }
    public enum Mode       { ADD, EDIT }

    private EntityType entityType;
    private Mode       mode;
    private Object     editEntity;
    private Runnable   onSaved;

    private final Map<String, TextField> fields = new LinkedHashMap<>();

    public void configure(EntityType type, Mode mode, Object editEntity, Runnable onSaved) {
        this.entityType = type;
        this.mode       = mode;
        this.editEntity = editEntity;
        this.onSaved    = onSaved;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize is called before configure, but is empty so it's okay
        //After that, we call configure and then buildForm
    }


    public void buildForm() {
        String title = (mode == Mode.ADD ? "ADD " : "EDIT ") + entityType.name().replace("_", " ");
        titleLabel.setText(title);

        fieldsContainer.getChildren().clear();
        fields.clear();

        switch (entityType) {
            case BRAND -> {
                addField("Name",        "name");
                addField("Country",     "country");
                addField("Description", "description");
            }
            case CATEGORY ->
                addField("Name", "name");

            case HEALTH_FUNCTION -> {
                addField("Name",        "name");
                addField("Description", "description");
            }
            case OPERATING_SYSTEM -> {
                addField("Name",      "name");
                addField("Version",   "version");
                addField("Developer", "developer");
            }
        }

        if (mode == Mode.EDIT && editEntity != null) {
            populateFields();
        }
    }

    private void addField(String label, String key) {
        VBox fieldBox = new VBox(4);

        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: #888; -fx-font-size: 11px;");

        TextField tf = new TextField();
        tf.setStyle("""
                -fx-background-color: #1a1a1a; -fx-text-fill: #eee;
                -fx-border-color: #333; -fx-border-radius: 6;
                -fx-background-radius: 6; -fx-padding: 8 12 8 12;
                """);

        fieldBox.getChildren().addAll(lbl, tf);
        fieldsContainer.getChildren().add(fieldBox);
        fields.put(key, tf);
    }

    private void populateFields() {
        switch (editEntity) {
            case Brand b -> {
                fields.get("name").setText(b.getName());
                fields.get("country").setText(b.getCountry());
                fields.get("description").setText(b.getDescription());
            }
            case Category c ->
                fields.get("name").setText(c.getName());

            case HealthFunction h -> {
                fields.get("name").setText(h.getName());
                fields.get("description").setText(h.getDescription());
            }
            case OperatingSystem o -> {
                fields.get("name").setText(o.getName());
                fields.get("version").setText(o.getVersion());
                fields.get("developer").setText(o.getDeveloper());
            }
            default -> {}
        }
    }

    @FXML
    private void handleSave() {
        if (!validate()) return;

        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            switch (entityType) {
                case BRAND -> {
                    Brand b = mode == Mode.ADD
                            ? new Brand(0,
                            get("name"), get("country"), get("description"))
                            : new Brand(((Brand) editEntity).getId(),
                            get("name"), get("country"), get("description"));
                    uow.getBrandRepository().save(b);
                }
                case CATEGORY -> {
                    Category c = mode == Mode.ADD
                            ? new Category(0, get("name"))
                            : new Category(((Category) editEntity).getId(), get("name"));
                    uow.getCategoryRepository().save(c);
                }
                case HEALTH_FUNCTION -> {
                    HealthFunction h = mode == Mode.ADD
                            ? new HealthFunction(0, get("name"), get("description"))
                            : new HealthFunction(((HealthFunction) editEntity).getId(),
                            get("name"), get("description"));
                    uow.getHealthFunctionRepository().save(h);
                }
                case OPERATING_SYSTEM -> {
                    OperatingSystem o = mode == Mode.ADD
                            ? new OperatingSystem(0,
                            get("name"), get("version"), get("developer"))
                            : new OperatingSystem(((OperatingSystem) editEntity).getId(),
                            get("name"), get("version"), get("developer"));
                    uow.getOperatingSystemRepository().save(o);
                }
            }
            uow.commit();
            AlertUtil.showInfo("Success",
                    (mode == Mode.ADD ? "Added" : "Updated") + " successfully!");
            if (onSaved != null) onSaved.run();
            handleCancel();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Save failed: " + e.getMessage());
        }
    }

    private boolean validate() {
        for (Map.Entry<String, TextField> entry : fields.entrySet()) {
            if (entry.getValue().getText().trim().isEmpty()) {
                showError(capitalize(entry.getKey()) + " is required.");
                return false;
            }
        }
        return true;
    }

    private String get(String key) {
        return fields.get(key).getText().trim();
    }

    private String capitalize(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
