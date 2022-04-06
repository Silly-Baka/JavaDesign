package design.ViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;

/**
 * User: 86176
 * Date: 2022/3/28
 * Time: 11:48
 * Description:
 */
public class fxmlTest extends Application {

    public static Window mainStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("ImagePreview.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load();
        ImagePreviewViewController ImagePreviewViewController = fxmlLoader.getController();
        ImagePreviewViewController.setPrimaryStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);
        primaryStage.show();
    }
}
