package hr.algebra.controller;

import hr.algebra.controller.services.interfaces.AuthService;
import hr.algebra.controller.services.implementations.AuthServiceImpl;
import hr.algebra.model.exceptions.UsernameAlreadyExistsException;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import hr.algebra.util.AlertUtil;
import hr.algebra.util.SceneUtil;
import hr.algebra.view.App;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;


    @FXML
    private void handleRegistration(){

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneNumberField.getText();
        String password = passwordField.getText();

        try(UnitOfWorkImpl uow = new UnitOfWorkImpl()){
            AuthService service = new AuthServiceImpl(uow);
            service.register(firstName, lastName, email, phoneNumber, username, password);

            AlertUtil.showInfo("Success", "Registration successful!");

            try{
                Stage stage = (Stage) usernameField.getScene().getWindow();
                SceneUtil.loadScene(App.class.getResource("/fxml/Login.fxml"), stage, "Login", new LoginController());
            } catch(Exception ex){
                throw new RuntimeException();
            }


        } catch(UsernameAlreadyExistsException ex){
            AlertUtil.showError("System error", "Username already exists");
        } catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            AlertUtil.showWarning("Warning","Invalid data");
        } catch(Exception ex){
            AlertUtil.showError("System error", "Error during registration");
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        usernameField.clear();
        passwordField.clear();
    }


}
