package com.sadov.psnr;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button modernImageButton;

    @FXML
    private Button origImageButton;

    @FXML
    private ImageView origImgView;

    @FXML
    private ImageView modImgView;

    @FXML
    void initialize() {
        origImageButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = fileChooser();
            Stage stage = (Stage) origImageButton.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                origImgView.setImage(image);
            }
        });

        modernImageButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = fileChooser();
            Stage stage = (Stage) modernImageButton.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                modImgView.setImage(image);
            }
        });
    }

    private FileChooser fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        return  fileChooser;
    }

}

