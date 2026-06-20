package hr.algebra.controller.adminDialog;

import hr.algebra.view.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminDialogHelper {

    private AdminDialogHelper(){}

    public static void openForm(EntityFormController.EntityType type,
                                EntityFormController.Mode mode,
                                Object editEntity,
                                Runnable onSaved) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("/fxml/EntityFormDialog.fxml"));
            EntityFormController controller = new EntityFormController();
            loader.setController(controller);
            Parent root = loader.load();

            controller.configure(type, mode, editEntity, onSaved);
            controller.buildForm();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle((mode == EntityFormController.Mode.ADD ? "Add " : "Edit ")
                    + type.name().replace("_", " "));
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
