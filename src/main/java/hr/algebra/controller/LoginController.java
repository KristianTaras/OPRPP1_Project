package hr.algebra.controller;


import hr.algebra.controller.services.interfaces.AuthService;
import hr.algebra.controller.services.implementations.AuthServiceImpl;
import hr.algebra.model.exceptions.InvalidCredentialsException;
import hr.algebra.model.entities.Role;
import hr.algebra.model.entities.User;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import hr.algebra.view.App;
import hr.algebra.util.AlertUtil;
import hr.algebra.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() throws Exception {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        try(UnitOfWorkImpl uow = new UnitOfWorkImpl()){
            AuthService service = new AuthServiceImpl(uow);

            User loggedInUser = service.login(username, password);

            if(loggedInUser.getRole() == Role.ADMIN){
                Stage stage = (Stage) usernameField.getScene().getWindow();
                SceneUtil.loadScene(App.class.getResource("/fxml/AdminView.fxml"), stage, "Admin View");
            }
            else{
                Stage stage = (Stage) usernameField.getScene().getWindow();
                SceneUtil.loadScene(App.class.getResource("/fxml/UserView.fxml"), stage, "User View");
            }

        } catch (InvalidCredentialsException ex) {
            AlertUtil.showError("Login failed", "Wrong username or password");
        } catch(Exception ex){ //Custom ?
            AlertUtil.showError("System error", "Login error");
        }
    }

    public void loadRegistration() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        SceneUtil.loadScene(App.class.getResource("/fxml/Registration.fxml"), stage, "Registration", new RegistrationController());
    }
}
