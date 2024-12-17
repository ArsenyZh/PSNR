package com.sadov.psnr;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Controller {
    private int startX;
    private int startY;
    private int width;
    private int height;


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
    private Button PSNRButton;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField textFieldHW;
    @FXML
    private TextField textFieldX;
    @FXML
    private TextField textFieldY;
    @FXML
    private Label PSNRValue;

    @FXML
    void initialize() {
        choiceBox.getItems().addAll("всё изображение", "блок на изображении");
        choiceBox.setValue("всё изображение");

        textFieldX.setVisible(false);
        textFieldY.setVisible(false);
        textFieldHW.setVisible(false);

        origImageButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = fileChooser();
            Stage stage = (Stage) origImageButton.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.exists()) {
                Image image = new Image(file.toURI().toString());
                origImgView.setImage(image);

//                getPixelPropertiesZone(image);
            }
        });

        modernImageButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = fileChooser();
            Stage stage = (Stage) modernImageButton.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.exists()) {
                Image image = new Image(file.toURI().toString());
                modImgView.setImage(image);

//                getPixelProperties(image);
            }
        });

        choiceBox.setOnAction(event -> {
            String selectedOption = choiceBox.getValue();

            if ("блок на изображении".equals(selectedOption)) {
                textFieldX.setVisible(true);
                textFieldY.setVisible(true);
                textFieldHW.setVisible(true);
            } else {
                textFieldX.setVisible(false);
                textFieldY.setVisible(false);
                textFieldHW.setVisible(false);
            }
        });

        PSNRButton.setOnAction(event -> {
            try {
                startX = Integer.parseInt(textFieldX.getText());
                startY = Integer.parseInt(textFieldY.getText());
                height = Integer.parseInt(textFieldHW.getText());
                width = Integer.parseInt(textFieldHW.getText());

                System.out.printf("X: %d, Y: %d, Height/Width: %d%n", startX, startY, height);
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите числовые значения.");
            }
            PSNRValue.setText("PSNR: 0%");
        });
    }

    private FileChooser fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));

        return  fileChooser;
    }

    private void getPixelProperties(Image image) {
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader pixelReader = image.getPixelReader();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = pixelReader.getColor(x, y);
                int red = (int) (color.getRed() * 255);
                int green = (int) (color.getGreen() * 255);
                int blue = (int) (color.getBlue() * 255);
                int brightness = (int) (color.getBrightness() * 255);

                System.out.printf("Пиксель (%d, %d): RGB(%d, %d, %d), Яркость: %d%n", x, y, red, green, blue, brightness);
            }
        }
    }

    private void getPixelPropertiesZone(Image image) {
        PixelReader pixelReader = image.getPixelReader();

        if (startX + width > image.getWidth()) width = (int) image.getWidth() - startX;
        if (startY + height > image.getHeight()) height = (int) image.getHeight() - startY;

        for (int x = startX; x < startX + width; x++) {
            for (int y = startY; y < startY + height; y++) {
                Color color = pixelReader.getColor(x, y);
                int red = (int) (color.getRed() * 255);
                int green = (int) (color.getGreen() * 255);
                int blue = (int) (color.getBlue() * 255);
                int brightness = (int) (color.getBrightness() * 255);

                System.out.printf("Пиксель (%d, %d): RGB(%d, %d, %d), Яркость: %d%n", x, y, red, green, blue, brightness);
            }
        }
    }
}

