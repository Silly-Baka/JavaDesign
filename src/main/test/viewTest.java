import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 11:31
 * Description:
 */
public class viewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("file:JavaDesign/src/resources/img/directory.jpg");

        ImageView imageView = new ImageView(image);

        BorderPane borderPane = new BorderPane(imageView);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
