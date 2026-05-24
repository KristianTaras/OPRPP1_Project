package hr.algebra.controller;

import hr.algebra.controller.services.AuthService;
import hr.algebra.controller.services.entities.AuthServiceImpl;
import hr.algebra.model.exceptions.UsernameAlreadyExistsException;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;
import hr.algebra.view.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegistrationController {


    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;


    @FXML
    private void handleRegistration() throws Exception{

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

            clearFields();

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
