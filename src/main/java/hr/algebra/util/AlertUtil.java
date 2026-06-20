package hr.algebra.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AlertUtil {

    private AlertUtil(){}

    public static void showInfo(String title, String message) { show(Alert.AlertType.INFORMATION, title, message); }

    public static void showError(String title, String message) { show(Alert.AlertType.ERROR, title, message); }

    public static void showWarning(String title, String message) { show(Alert.AlertType.WARNING, title, message); }

    public static boolean showConfirmationAndWait(String title, String message) {
       Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
       confirmation.setTitle(title);
       confirmation.setHeaderText(null);
       confirmation.setContentText(message);
       return confirmation.showAndWait()
               .filter(btn -> btn == ButtonType.OK)
               .isEmpty();

    }

    public static void show(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
