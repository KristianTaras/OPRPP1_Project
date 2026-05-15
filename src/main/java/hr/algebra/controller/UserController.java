package hr.algebra.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private VBox sideMenu;

    private boolean menuIsVisible = false;

    @FXML
    private void toggleMenu() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), sideMenu);

        if(!menuIsVisible){
            transition.setToX(0);
            menuIsVisible = true;
        }else
        {
            transition.setToX(-200);
            menuIsVisible = false;
        }
    }


    //Maybe don't need to implement, cause jfx is smart enough to understand only initialize method
    //Initialize is used to load in data, need this only in user/admin controll
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("[lifecycle] MainController.initialize() - FXML injection done");

    }
}
