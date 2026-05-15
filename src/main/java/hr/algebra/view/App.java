package hr.algebra.view;

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
        SceneUtil.loadScene(App.class.getResource("/fxml/Login.fxml"), stage, "Main Page");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("[Lifecycle] stop() - aplikacija se zatvara");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
