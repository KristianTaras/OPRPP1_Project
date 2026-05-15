package hr.algebra.controller;


import hr.algebra.view.App;
import hr.algebra.view.util.AlertUtil;
import hr.algebra.view.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin(){
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        //Temporary login
        if("admin".equals(username) && "admin".equals(password)){
            Stage stage = (Stage) usernameField.getScene().getWindow();
            SceneUtil.loadScene(App.class.getResource("/fxml/Admin.fxml"), stage, "Admin view");

        }else{
            AlertUtil.showError("Pogresna prijava", "Pogresno korisnicko ime ili lozinka");
        }

    }
}
