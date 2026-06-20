package hr.algebra.controller;

import hr.algebra.controller.task.FilterSmartWatchTask;
import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import hr.algebra.view.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class SmartWatchGridController implements Initializable {

    @FXML private FlowPane         cardPane;
    @FXML private TextField        searchField;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private ComboBox<String> brandFilter;
    @FXML private Label            countLabel;

    private ObservableList<SmartWatch> allWatches;

    private SmartWatch selectedWatch = null;
    private VBox selectedCard = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        populateFilters();
        buildCards(allWatches);
    }

    private void loadData() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            allWatches = FXCollections.observableArrayList(uow.getSmartWatchRepository().getFullAll());
        } catch (Exception e) {
            e.printStackTrace(); //Custom
        }
    }

    private void populateFilters() {
        if (allWatches == null) return;

        allWatches.stream()
                .filter(w -> w.getCategory() != null)
                .map(w -> w.getCategory().getName())
                .distinct().sorted()
                .forEach(categoryFilter.getItems()::add);

        allWatches.stream()
                .filter(w -> w.getBrand() != null)
                .map(w -> w.getBrand().getName())
                .distinct().sorted()
                .forEach(brandFilter.getItems()::add);

        categoryFilter.setOnAction(e -> applyFilters());
        brandFilter.setOnAction(e -> applyFilters());
    }

    private void applyFilters() {
        String category = categoryFilter.getValue();
        String brand = brandFilter.getValue();

        ObservableList<SmartWatch> filtered = allWatches.stream()
                .filter( w -> category == null || (w.getCategory() != null && w.getCategory().getName().equals(category)))
                .filter(w -> brand == null || (w.getBrand() != null && w.getBrand().getName().equals(brand)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        buildCards(filtered);
    }

    @FXML
    private void smartWatchSearchField() {
        FilterSmartWatchTask task = new FilterSmartWatchTask(
                allWatches, searchField.getText()
        );

        task.setOnSucceeded(e -> {
            List<SmartWatch> filtered = task.getValue();
            buildCards(filtered);
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true); //Close thread on app closure
        thread.start();
    }

    @FXML
    private void setSearchFieldClear() {
        searchField.clear();
        categoryFilter.setValue(null);
        brandFilter.setValue(null);
        buildCards(allWatches);
    }

    public void setData(ObservableList<SmartWatch> watches) {
        this.allWatches = watches;
        buildCards(watches);
    }

    //Card building

    private void buildCards(List<SmartWatch> watches) {
        cardPane.getChildren().clear();

        if (watches == null || watches.isEmpty()) {
            Label empty = new Label("No smart watches found.");
            empty.setStyle("-fx-text-fill: #555; -fx-font-size: 14px;");
            cardPane.getChildren().add(empty);
            countLabel.setText("0 watches");
            return;
        }

        for (SmartWatch watch : watches) {
            cardPane.getChildren().add(buildCard(watch));
        }

        countLabel.setText(watches.size() + " watches");
    }

    private VBox buildCard(SmartWatch watch) {
        VBox card = new VBox(0);
        card.setPrefWidth(210);
        card.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-background-radius: 12;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 2);
                """);

        //Image area
        StackPane imageArea = new StackPane();
        imageArea.setPrefHeight(140);
        imageArea.setStyle("""
                        -fx-background-color: white;
                        -fx-background-radius: 10 10 0 0;
                        -fx-border-color: #d0d0d0;
                        -fx-border-width: 1;
                        -fx-border-radius: 10 10 0 0;
                    """);

        // Slika ili emoji fallback
        Node imageNode = loadImage(watch.getImagePath(), 100, 100);
        imageArea.getChildren().add(imageNode);

        // Category badge
        if (watch.getCategory() != null) {
            Label badge = new Label(watch.getCategory().getName().toUpperCase());
            badge.setStyle("""
                    -fx-background-color: #e94560;
                    -fx-text-fill: white;
                    -fx-font-size: 9px;
                    -fx-font-weight: bold;
                    -fx-background-radius: 4;
                    -fx-padding: 2 7 2 7;
                    """);
            StackPane.setAlignment(badge, Pos.TOP_RIGHT);
            StackPane.setMargin(badge, new Insets(10, 10, 0, 0));
            imageArea.getChildren().add(badge);
        }

        //Info area
        VBox info = new VBox(4);
        info.setPadding(new Insets(12, 14, 14, 14));

        Label name = new Label(watch.getName());
        name.setStyle("-fx-text-fill: #f0f0f0; -fx-font-size: 13px; -fx-font-weight: bold;");
        name.setWrapText(true);
        name.setMaxWidth(182);

        String brandYear = (watch.getBrand() != null ? watch.getBrand().getName() : "")
                + " · " + watch.getYearOfMaking();
        Label sub = new Label(brandYear);
        sub.setStyle("-fx-text-fill: #666; -fx-font-size: 11px;");

        Label price = new Label(String.format("€%.2f", watch.getPrice()));
        price.setStyle("-fx-text-fill: #e94560; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox.setMargin(price, new Insets(6, 0, 0, 0));

        HBox stats = new HBox(10);
        stats.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(stats, new Insets(6, 0, 0, 0));

        Label battery = new Label("🔋 " + watch.getBatteryLife() + "h");
        battery.setStyle("-fx-text-fill: #555; -fx-font-size: 10px;");

        Label ip = new Label("💧 " + watch.getIpRating());
        ip.setStyle("-fx-text-fill: #555; -fx-font-size: 10px;");

        stats.getChildren().addAll(battery, ip);
        info.getChildren().addAll(name, sub, price, stats);
        card.getChildren().addAll(imageArea, info);

        //Hover
        card.setOnMouseEntered(e -> card.setStyle("""
                -fx-background-color: #222;
                -fx-background-radius: 12;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, rgba(233,69,96,0.3), 16, 0, 0, 4);
                -fx-translate-y: -2;
                """));

        card.setOnMouseExited(e -> card.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-background-radius: 12;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 2);
                -fx-translate-y: 0;
                """));

        card.setOnMouseClicked(e -> {
            selectedWatch = watch;
            highlightSelected(card);

            if (e.getClickCount() == 2) {
                openDetail(watch); // dupli klik → otvori detail
            }
        });

        return card;
    }


    private void highlightSelected(VBox card) {
        // Reset prethodni odabrani
        if (selectedCard != null) {
            selectedCard.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-background-radius: 12;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 2);
                """);
        }
        // Označi novi
        card.setStyle("""
            -fx-background-color: #1e2a1e;
            -fx-background-radius: 12;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(39,174,96,0.4), 16, 0, 0, 4);
            -fx-border-color: #27ae60;
            -fx-border-width: 2;
            -fx-border-radius: 12;
            """);
        selectedCard = card;
    }

    public SmartWatch getSelectedWatch() {
        return selectedWatch;
    }

    // Vraća ImageView ako slika postoji, inače emoji Label
    private Node loadImage(String imagePath, double width, double height) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        if(imagePath == null || imagePath.isEmpty()){
            Label emoji = new Label("⌚");
            emoji.setStyle("-fx-font-size: 56px; ");
            return emoji;
        }

        Task<Image> imageTask = new Task<>(){
            @Override
            protected Image call(){
                URL url = App.class.getResource(imagePath);
                if (url == null) return null;
                return new Image(url.toExternalForm(), width, height, true, true);
                // true , true - > background loading and smooth loading
            }
        };

        imageTask.setOnSucceeded(e ->{
            Image img = imageTask.getValue();
            if(img != null && !img.isError()){
                imageView.setImage(img);
            } else{
                StackPane parent = (StackPane) imageView.getParent();
                if (parent != null){
                    Label emoji = new Label("⌚");
                    emoji.setStyle("-fx-font-size: 56px");
                    parent.getChildren().remove(imageView);
                    parent.getChildren().add(0, emoji);
                }
            }
        });

        Thread thread = new Thread(imageTask);
        thread.setDaemon(true);
        thread.start();

        return imageView;

    }

    private void openDetail(SmartWatch watch) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/SmartWatchDetail.fxml"));
            SmartWatchDetailController controller = new SmartWatchDetailController(watch);
            loader.setController(controller);
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}