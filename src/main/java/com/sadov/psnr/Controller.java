package com.sadov.psnr;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
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
    Image image1;
    Image image2;


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
                image1 = new Image(file.toURI().toString());
                origImgView.setImage(image1);

//                getPixelPropertiesZone(image);
            }
        });

        modernImageButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = fileChooser();
            Stage stage = (Stage) modernImageButton.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.exists()) {
                image2 = new Image(file.toURI().toString());
                modImgView.setImage(image2);

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
            double psnr = 0;

            if(textFieldY.isVisible()){
                psnr = getPixelPropertiesZone(image1,image2);
            }else {
                psnr = getPixelProperties(image1,image2);
            }

            DecimalFormat df = new DecimalFormat("#.00");
            String formattedValue = df.format(psnr);

            PSNRValue.setText("PSNR: " + formattedValue);
        });
    }

    private FileChooser fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));

        return  fileChooser;
    }

    private double getPixelProperties(Image image, Image image1) {
        PixelReader pixelReader = image.getPixelReader();
        PixelReader pixelReader1 = image1.getPixelReader();
        height =(int) image.getHeight();
        width = (int) image1.getWidth();

        double mse = 0.0;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color1 = pixelReader.getColor(x, y);
                Color color2 = pixelReader1.getColor(x, y);
                mse+=getMSE(color1,color2);

            }
        }
        return 10 * Math.log10((255 * 255) / mse);
    }

    private double getPixelPropertiesZone(Image image, Image image1) {
        PixelReader pixelReader = image.getPixelReader();
        PixelReader pixelReader1 = image1.getPixelReader();

        if (startX + width > image.getWidth()) width = (int) image.getWidth() - startX;
        if (startY + height > image.getHeight()) height = (int) image.getHeight() - startY;

        double mse = 0.0;

        for (int x = startX; x < startX + width; x++) {
            for (int y = startY; y < startY + height; y++) {
                Color color1 = pixelReader.getColor(x, y);
                Color color2 = pixelReader1.getColor(x, y);
                mse+=getMSE(color1,color2);

            }
        }
        return 10 * Math.log10((255 * 255) / mse);

    }
    private  double getMSE(Color color1, Color color){
        double mse = 0.0;




        int originalRed = (int) (color.getRed() * 255);
        int originalGreen = (int) (color.getGreen() * 255);
        int originalBlue = (int) (color.getBlue() * 255);

        int modifiedRed = (int) (color1.getRed() * 255);
        int modifiedGreen = (int) (color1.getGreen() * 255);
        int modifiedBlue = (int) (color1.getBlue() * 255);

        // Вычисляем разницу для каждого канала
        double diffRed = originalRed - modifiedRed;
        double diffGreen = originalGreen - modifiedGreen;
        double diffBlue = originalBlue - modifiedBlue;

        // Суммируем квадраты разниц
        mse += (diffRed * diffRed + diffGreen * diffGreen + diffBlue * diffBlue) / 3.0;  // Среднее по канала}

// Вычисляем среднее значение MSE
        mse /= (width * height);

                // Вычисляем PSNR
                double psnr = 10 * Math.log10((255 * 255) / mse);
                return mse;

    }
}


