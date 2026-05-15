package hr.algebra.view;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void init(){
        System.out.println("[lifecycle] init() - background thread ");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("[lifecycle] start() - JavaFX Application Thread");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();

        JFXDecorator decorator = new JFXDecorator(stage, root);

        decorator.setCustomMaximize(false);

        decorator.setTitle("SmartWatch App");

        Scene scene = new Scene(decorator, 1300, 800);


        String css = getClass().getResource("/CSS/MainStyle.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.setTitle("Main Page");
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
