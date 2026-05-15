package hr.algebra.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    //Maybe don't need to implement, cause jfx is smart enough to understand only initialize method
    //Initialize is used to load in data, need this only in user/admin controll
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("[lifecycle] MainController.initialize() - FXML injection done");

    }
}
