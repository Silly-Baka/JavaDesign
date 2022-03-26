package design.ViewTest;

import design.Controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 11:31
 * Description:
 */
public class MainTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController mainController = new MainController(primaryStage);
        AnchorPane root = mainController.getPresentPane();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
