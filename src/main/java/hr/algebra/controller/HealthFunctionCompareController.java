package hr.algebra.controller;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.repositories.implementations.UnitOfWorkImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HealthFunctionCompareController implements Initializable {

    @FXML private StackPane dropZoneA;
    @FXML private Label     dropLabelA;
    @FXML private VBox      detailBoxA;
    @FXML private Label     nameA;
    @FXML private Label     descA;

    @FXML private StackPane dropZoneB;
    @FXML private Label     dropLabelB;
    @FXML private VBox      detailBoxB;
    @FXML private Label     nameB;
    @FXML private Label     descB;

    @FXML private FlowPane  healthPane;

    private HealthFunction selectedA;
    private HealthFunction selectedB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadHealthFunctions();
        setupDropZones();
    }

    private void loadHealthFunctions() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            uow.getHealthFunctionRepository().getAll().forEach(hf ->
                    healthPane.getChildren().add(buildDraggableTag(hf)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Label buildDraggableTag(HealthFunction hf) {
        Label tag = new Label(hf.getName());
        tag.setStyle("""
                -fx-background-color: #1e2a3a;
                -fx-text-fill: #5b9bd5;
                -fx-background-radius: 20;
                -fx-padding: 6 14 6 14;
                -fx-font-size: 12px;
                -fx-cursor: hand;
                """);

        tag.setOnDragDetected(e -> {
            Dragboard db = tag.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString("HF:" + hf.getId());
            db.setContent(content);
            e.consume();
        });

        tag.setOnMouseEntered(e -> tag.setStyle("""
                -fx-background-color: #243550;
                -fx-text-fill: #7ab8f5;
                -fx-background-radius: 20;
                -fx-padding: 6 14 6 14;
                -fx-font-size: 12px;
                -fx-cursor: hand;
                """));

        tag.setOnMouseExited(e -> tag.setStyle("""
                -fx-background-color: #1e2a3a;
                -fx-text-fill: #5b9bd5;
                -fx-background-radius: 20;
                -fx-padding: 6 14 6 14;
                -fx-font-size: 12px;
                -fx-cursor: hand;
                """));

        return tag;
    }

    private void setupDropZones() {

        dropZoneA.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY);
                dropZoneA.setStyle(dropZoneA.getStyle().replace("dashed", "solid"));
            }
            e.consume();
        });

        dropZoneA.setOnDragExited(e -> {
            dropZoneA.setStyle(dropZoneA.getStyle().replace("solid", "dashed"));
            e.consume();
        });

        dropZoneA.setOnDragDropped(e -> {
            String data = e.getDragboard().getString();
            int id = Integer.parseInt(data.split(":")[1]);
            loadIntoSlot(id, "A");
            e.setDropCompleted(true);
            e.consume();
        });



        dropZoneB.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY);
                dropZoneB.setStyle(dropZoneB.getStyle().replace("dashed", "solid"));
            }
            e.consume();
        });

        dropZoneB.setOnDragExited(e -> {
            dropZoneB.setStyle(dropZoneB.getStyle().replace("solid", "dashed"));
            e.consume();
        });

        dropZoneB.setOnDragDropped(e -> {
            String data = e.getDragboard().getString();
            int id = Integer.parseInt(data.split(":")[1]);
            loadIntoSlot(id, "B");
            e.setDropCompleted(true);
            e.consume();
        });

        detailBoxA.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) e.acceptTransferModes(TransferMode.COPY);
            e.consume();
        });
        detailBoxA.setOnDragDropped(e -> {
            int id = Integer.parseInt(e.getDragboard().getString().split(":")[1]);
            loadIntoSlot(id, "A"); // zamijeni postojeći
            e.setDropCompleted(true);
            e.consume();
        });

        detailBoxB.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) e.acceptTransferModes(TransferMode.COPY);
            e.consume();
        });
        detailBoxB.setOnDragDropped(e -> {
            int id = Integer.parseInt(e.getDragboard().getString().split(":")[1]);
            loadIntoSlot(id, "B");
            e.setDropCompleted(true);
            e.consume();
        });


    }

    private void loadIntoSlot(int id, String slot) {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {
            HealthFunction hf = uow.getHealthFunctionRepository().getBy(id).orElse(null);
            if (hf == null) return;

            if (slot.equals("A")) {
                selectedA = hf;
                nameA.setText(hf.getName());
                descA.setText(hf.getDescription() != null ? hf.getDescription() : "No description.");

                dropZoneA.setVisible(false);
                dropZoneA.setManaged(false);
                detailBoxA.setVisible(true);
                detailBoxA.setManaged(true);
            } else {
                selectedB = hf;
                nameB.setText(hf.getName());
                descB.setText(hf.getDescription() != null ? hf.getDescription() : "No description.");
                dropZoneB.setVisible(false);
                dropZoneB.setManaged(false);
                detailBoxB.setVisible(true);
                detailBoxB.setManaged(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleClearA() {
        selectedA = null;
        detailBoxA.setVisible(false);
        detailBoxA.setManaged(false);
        dropZoneA.setVisible(true);
        dropZoneA.setManaged(true);
        dropLabelA.setVisible(true);
        dropLabelA.setManaged(true);
    }

    @FXML
    private void handleClearB() {
        selectedB = null;
        detailBoxB.setVisible(false);
        detailBoxB.setManaged(false);
        dropZoneB.setVisible(true);
        dropZoneB.setManaged(true);
        dropLabelB.setVisible(true);
        dropLabelB.setManaged(true);
    }
}
