module com.sadov.psnr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sadov.psnr to javafx.fxml;
    exports com.sadov.psnr;
}