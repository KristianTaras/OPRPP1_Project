package hr.algebra.view;

import hr.algebra.controller.LoginController;
import hr.algebra.view.util.SceneUtil;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void init(){
        System.out.println("[lifecycle] init() - background thread ");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("[lifecycle] start() - JavaFX Application Thread");

        try{
            SceneUtil.loadScene(App.class.getResource("/fxml/login.fxml"), stage, "SmartWatch app", new LoginController());
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Loading login stage failed!");
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
