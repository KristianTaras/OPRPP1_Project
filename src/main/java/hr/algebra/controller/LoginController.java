package hr.algebra.controller;


import hr.algebra.controller.services.AuthService;
import hr.algebra.model.exceptions.InvalidCredentialsException;
import hr.algebra.model.repositories.UnitOfWork;
import hr.algebra.security.entity.Role;
import hr.algebra.security.entity.User;
import hr.algebra.view.App;
import hr.algebra.view.util.AlertUtil;
import hr.algebra.view.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLOutput;

public class LoginController {

    private final AuthService service;

    public LoginController(AuthService service) {
        this.service = service;
    }

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() throws Exception {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();


        try{
            User loggedInUser = service.login(username, password);

            if(loggedInUser.getRole() == Role.ADMIN){
                System.out.println("Loading admin view!");
            }
            else{
                System.out.println("Loading user view!");
            }

        } catch (InvalidCredentialsException ex) {
            throw new RuntimeException(ex.getMessage()); //Label error.setText(ex);
        } catch(Exception ex){
            throw new Exception("System error!"); //Label error.setText(ex);
        }
    }
}
