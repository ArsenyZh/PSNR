package com.sadov.psnr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    void initialize() {
        origImageButton.setOnAction(actionEvent -> {

        });

        modernImageButton.setOnAction(actionEvent -> {

        });
    }

}

