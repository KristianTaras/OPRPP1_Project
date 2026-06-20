package hr.algebra.view;

import hr.algebra.controller.LoginController;
import hr.algebra.model.exceptions.ApplicationException;
import hr.algebra.util.SceneUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;


public class App extends Application {

    @Override
    public void init(){
        System.out.println("[lifecycle] init() - background thread ");
    }

    @Override
    public void start(Stage stage){
        System.out.println("[lifecycle] start() - JavaFX Application Thread");

        Image icon = new Image(Objects.requireNonNull(App.class.getResourceAsStream("/images/app_icon.png")));
        stage.getIcons().add(icon);

        try{
            SceneUtil.loadScene(App.class.getResource("/fxml/Login.fxml"), stage, "SmartWatch app", new LoginController());
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            throw new ApplicationException("Loading login stage failed!");
        }
    }

    @Override
    public void stop() {
        System.out.println("[Lifecycle] stop() - aplikacija se zatvara");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
