package hr.algebra.controller;

import hr.algebra.controller.services.AuthService;
import hr.algebra.model.exceptions.UsernameAlreadyExistsException;
import hr.algebra.view.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {

    private final AuthService service;

    public RegistrationController(AuthService service) {
        this.service = service;
    }

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleRegistration() throws Exception{
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneNumberField.getText();
        String password = passwordField.getText();

        try{
            service.register(username, email, phoneNumber, password);

            AlertUtil.showError("Success", "Registration successful!");

            clearFields();

        } catch(UsernameAlreadyExistsException ex){
            AlertUtil.showError("System error", "Username already exists");
        } catch(IllegalArgumentException ex){
            AlertUtil.showWarning("Warning","Invalid data");
        } catch(Exception ex){
            AlertUtil.showError("System error", "Error during registration");
        }




    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        passwordField.clear();
    }


}
