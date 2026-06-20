package hr.algebra.controller;

import hr.algebra.controller.adminDialog.AdminDialogHelper;
import hr.algebra.controller.adminDialog.EntityFormController;
import hr.algebra.controller.tableControllers.*;
import hr.algebra.model.entities.*;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import hr.algebra.util.XmlParseUtil;
import hr.algebra.view.App;
import hr.algebra.util.AlertUtil;
import hr.algebra.util.DatabaseUtil;
import hr.algebra.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML private StackPane    contentPane;
    @FXML private Label        statusLabel;
    @FXML private ToggleButton toggleViewButton;

    private SmartWatchTableController      currentTableController;
    private SmartWatchGridController       currentGrid;
    private BrandTableController           brandController;
    private CategoryTableController        categoryController;
    private HealthFunctionTableController  healthController;
    private OperatingSystemTableController osController;
    private UserTableController            userController;

    private boolean isTableView = false;

    private enum ActiveEntity { SMARTWATCH, BRAND, CATEGORY, HEALTH, OS, USER }
    private ActiveEntity activeEntity = ActiveEntity.SMARTWATCH;

    private static final String NO_SELECTION = "No selection";
    private static final String ERROR = "Error";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSmartWatches();
    }


    @FXML
    private void handleSmartWatches() {
        activeEntity = ActiveEntity.SMARTWATCH;
        clearEntityControllers();
        if (isTableView) loadTableView();
        else             loadGridView();
        setStatus("Smart Watches");
    }

    @FXML
    private void handleBrands() {
        activeEntity = ActiveEntity.BRAND;
        clearEntityControllers();
        brandController = new BrandTableController();
        loadView("/fxml/BrandTable.fxml", brandController);
        setStatus("Brands");
    }

    @FXML
    private void handleCategories() {
        activeEntity = ActiveEntity.CATEGORY;
        clearEntityControllers();
        categoryController = new CategoryTableController();
        loadView("/fxml/CategoryTable.fxml", categoryController);
        setStatus("Categories");
    }

    @FXML
    private void handleHealthFunctions() {
        activeEntity = ActiveEntity.HEALTH;
        clearEntityControllers();
        healthController = new HealthFunctionTableController();
        loadView("/fxml/HealthFunctionTable.fxml", healthController);
        setStatus("Health Functions");
    }

    @FXML
    private void handleOperatingSystems() {
        activeEntity = ActiveEntity.OS;
        clearEntityControllers();
        osController = new OperatingSystemTableController();
        loadView("/fxml/OperatingSystemTable.fxml", osController);
        setStatus("Operating Systems");
    }

    @FXML
    private void handleUsers() {
        activeEntity = ActiveEntity.USER;
        clearEntityControllers();
        userController = new UserTableController();
        loadView("/fxml/UserTable.fxml", userController);
        setStatus("Users");
    }

    private void clearEntityControllers() {
        currentGrid = null;
        currentTableController = null;
        brandController = null;
        categoryController = null;
        healthController = null;
        osController = null;
        userController = null;
    }

    @FXML
    private void handleToggleView() {
        isTableView = !isTableView;
        if (activeEntity == ActiveEntity.SMARTWATCH) {
            if (isTableView) {
                if (toggleViewButton != null) toggleViewButton.setText("🖼 Grid View");
                loadTableView();
            } else {
                if (toggleViewButton != null) toggleViewButton.setText("📋 Table View");
                loadGridView();
            }
        }
    }

    private void loadGridView() {
        currentGrid = new SmartWatchGridController();
        currentTableController = null;
        loadView("/fxml/SmartWatchGridView.fxml", currentGrid);
    }

    private void loadTableView() {
        currentTableController = new SmartWatchTableController();
        currentGrid = null;
        loadView("/fxml/SmartWatchTableView.fxml", currentTableController);
    }



    @FXML
    private void handleAdd() {
        switch (activeEntity) {
            case SMARTWATCH -> openAddSmartWatch();
            case BRAND      -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.BRAND,
                    EntityFormController.Mode.ADD, null, this::refreshCurrentView);
            case CATEGORY   -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.CATEGORY,
                    EntityFormController.Mode.ADD, null, this::refreshCurrentView);
            case HEALTH     -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.HEALTH_FUNCTION,
                    EntityFormController.Mode.ADD, null, this::refreshCurrentView);
            case OS         -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.OPERATING_SYSTEM,
                    EntityFormController.Mode.ADD, null, this::refreshCurrentView);
            default         -> AlertUtil.showWarning("Info", "Cannot add Users manually.");
        }
    }

    @FXML
    private void handleEdit() {
        Object selected = getSelected();
        if (selected == null) {
            AlertUtil.showWarning(NO_SELECTION, "Please select an item to edit.");
            return;
        }

        switch (activeEntity) {
            case SMARTWATCH -> openEditSmartWatch((SmartWatch) selected);
            case BRAND      -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.BRAND,
                    EntityFormController.Mode.EDIT, selected, this::refreshCurrentView);
            case CATEGORY   -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.CATEGORY,
                    EntityFormController.Mode.EDIT, selected, this::refreshCurrentView);
            case HEALTH     -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.HEALTH_FUNCTION,
                    EntityFormController.Mode.EDIT, selected, this::refreshCurrentView);
            case OS         -> AdminDialogHelper.openForm(
                    EntityFormController.EntityType.OPERATING_SYSTEM,
                    EntityFormController.Mode.EDIT, selected, this::refreshCurrentView);
            default         -> AlertUtil.showWarning("Info", "Edit not available for this entity.");
        }
    }

    @FXML
    private void handleDelete() {
        Object selected = getSelected();
        if (selected == null) {
            AlertUtil.showWarning(NO_SELECTION, "Please select an item to delete.");
            return;
        }

        String name = getEntityName(selected);
        if (AlertUtil.showConfirmationAndWait("Delete", "Delete: " + name + "?")) return;

        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            switch (activeEntity) {
                case SMARTWATCH -> {
                    SmartWatch sw = (SmartWatch) selected;
                    deleteImage(sw.getImagePath());
                    uow.getSmartWatchRepository().deleteById(sw.getId());
                }
                case BRAND    -> uow.getBrandRepository().deleteById(((Brand) selected).getId());
                case CATEGORY -> uow.getCategoryRepository().deleteById(((Category) selected).getId());
                case HEALTH   -> uow.getHealthFunctionRepository().deleteById(((HealthFunction) selected).getId());
                case OS       -> uow.getOperatingSystemRepository().deleteById(((OperatingSystem) selected).getId());
                case USER     -> uow.getUserRepository().deleteById(((User) selected).getId());
            }
            uow.commit();
            setStatus("Deleted: " + name);
            refreshCurrentView();
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError(ERROR, "Failed to delete: " + e.getMessage());
        }
    }

    @FXML
    private void handleExportXml() {
        if (activeEntity != ActiveEntity.SMARTWATCH) {
            AlertUtil.showWarning("Info", "XML export is only available for Smart Watches.");
            return;
        }
        SmartWatch selected = getSmartWatchSelected();
        if (selected == null) {
            AlertUtil.showWarning(NO_SELECTION, "Please select a smart watch to export.");
            return;
        }
        try {
            SmartWatch fullWatch = null;
            try(UnitOfWorkImpl uow = new UnitOfWorkImpl()){
                fullWatch = uow.getSmartWatchRepository().getFullById(selected.getId());
            }

            if (fullWatch == null) {
                AlertUtil.showError("Export failed", "Could not load watch data.");
                return;
            }

            String desktop = System.getProperty("user.home") + "/Desktop";
            String fileName = "SmartWatch_" + fullWatch.getId() + "_" + fullWatch.getName().replaceAll("\\s+", "_") + ".xml";
            String path = desktop + "/" + fileName;

            XmlParseUtil.saveSmartWatch(fullWatch, path);
            setStatus("✔ Exported: " + fullWatch.getName());
            AlertUtil.showInfo("Export successful", "Saved to:\n" + path);


        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Export failed", e.getMessage());
        }
    }

    private Object getSelected() {
        return switch (activeEntity) {
            case SMARTWATCH -> getSmartWatchSelected();
            case BRAND      -> brandController    != null ? brandController.getSelected()    : null;
            case CATEGORY   -> categoryController != null ? categoryController.getSelected() : null;
            case HEALTH     -> healthController   != null ? healthController.getSelected()   : null;
            case OS         -> osController       != null ? osController.getSelected()       : null;
            case USER       -> userController     != null ? userController.getSelected()     : null;
        };
    }

    private SmartWatch getSmartWatchSelected() {
        if (currentGrid != null)            return currentGrid.getSelectedWatch();
        if (currentTableController != null) return currentTableController.getSelectedWatch();
        return null;
    }

    private String getEntityName(Object entity) {
        return switch (entity) {
            case SmartWatch sw     -> sw.getName();
            case Brand b           -> b.getName();
            case Category c        -> c.getName();
            case HealthFunction h  -> h.getName();
            case OperatingSystem o -> o.getName();
            case User u            -> u.getUsername();
            default                -> entity.toString();
        };
    }

    private void refreshCurrentView() {
        switch (activeEntity) {
            case SMARTWATCH -> handleSmartWatches();
            case BRAND      -> handleBrands();
            case CATEGORY   -> handleCategories();
            case HEALTH     -> handleHealthFunctions();
            case OS         -> handleOperatingSystems();
            case USER       -> handleUsers();
        }
    }

    private void openAddSmartWatch() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/AddSmartWatchView.fxml"));
            AddSmartWatchController controller = new AddSmartWatchController();
            controller.setOnSaved(this::refreshCurrentView);
            loader.setController(controller);
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add Smart Watch");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showError(ERROR, "Could not open add form");
        }
    }

    private void openEditSmartWatch(SmartWatch watch){
        try{
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/AddSmartWatchView.fxml"));
            AddSmartWatchController controller = new AddSmartWatchController();
            controller.setEditMode(watch);
            controller.setOnSaved(this::refreshCurrentView);
            loader.setController(controller);
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Edit Smart Watch");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
        } catch(Exception ex){
            ex.printStackTrace();
            AlertUtil.showError(ERROR,"Could not open edit form");
        }
    }



    private void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) return;
        try {
            URL resource = App.class.getResource(imagePath);
            if (resource != null) Files.deleteIfExists(Path.of(resource.toURI()));
            Files.deleteIfExists(Path.of("src/main/resources" + imagePath));
        } catch (Exception e) {
            System.out.println("Could not delete image: " + e.getMessage());
        }
    }

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

    @FXML
    private void handleDropDb() {
        if (AlertUtil.showConfirmationAndWait("Drop database",
                "Are you sure you want to delete the database?")) return;
        try {
            DatabaseUtil.dropSchema();
            setStatus("Database deleted");
            handleSmartWatches();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showError(ERROR, "Drop failed: " + ex.getMessage());
        }
    }

    @FXML
    private void handleResetDb() {
        if (AlertUtil.showConfirmationAndWait("Reset database",
                "Are you sure you want to reset the database?")) return;
        try {
            DatabaseUtil.resetSchema();
            setStatus("Database reset and filled");
            handleSmartWatches();
        } catch (Exception ex) {
            AlertUtil.showError(ERROR, "Reset failed: " + ex.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        SceneUtil.loadScene(App.class.getResource("/fxml/Login.fxml"),
                stage, "Login", new LoginController());
    }

    private void setStatus(String message) {
        if (statusLabel != null) statusLabel.setText(message);
    }
}
