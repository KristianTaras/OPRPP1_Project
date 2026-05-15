package hr.algebra.controller;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button signUp;

    @FXML
    private Button menu;

    @FXML
    private AnchorPane ContentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadFXMLIntoContent("/fxml/MediainMain.fxml");
    }

    private void loadFXMLIntoContent(String fxmlPath) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            ContentPane.getChildren().setAll(root);

            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }


    @FXML
    private void openSignupPopup(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Sign up");
            loginStage.setScene(new Scene(root));

            loginStage.initModality(Modality.APPLICATION_MODAL); //Disables clicking on main while signup is active

            Stage mainStage = (Stage) signUp.getScene().getWindow();
            loginStage.initOwner(mainStage);

            loginStage.showAndWait();

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }



}
