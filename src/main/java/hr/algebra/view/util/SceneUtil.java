package hr.algebra.view.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class SceneUtil {

    private SceneUtil() {}

    public static void loadScene(URL fxmlUrl, Stage stage, String title){
        try{
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        }catch(IOException ex){
            throw new RuntimeException("Cannot load FXML file: " + fxmlUrl, ex);
        }
    }
}
