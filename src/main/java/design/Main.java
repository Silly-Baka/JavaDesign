package design;

import design.viewController.ImagePreviewViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;

/**
 * User: 86176
 * Date: 2022/3/28
 * Time: 11:48
 * Description:
 */
public class Main extends Application {

    public static Window mainStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("viewController/ImagePreview.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load();
        ImagePreviewViewController ImagePreviewViewController = fxmlLoader.getController();
        ImagePreviewViewController.setPrimaryStage(primaryStage);
        ImagePreviewViewController.getImageController().setViewController(ImagePreviewViewController);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setHeight(770);
        primaryStage.setWidth(1200);
        primaryStage.getIcons().add(new Image(getClass().getResource("/img/stage1.png").toString()));
        primaryStage.setTitle("狂拽炫酷叼究极图片管理程序");
        primaryStage.show();


    }
}
