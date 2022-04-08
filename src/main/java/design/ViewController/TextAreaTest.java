package design.ViewController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * User: 86176
 * Date: 2022/4/8
 * Time: 15:49
 * Description:
 */
public class TextAreaTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

        HBox hBox1 = new HBox();
        Label label1 = new Label("请输入重命名后的名字前缀：");
        TextField textField1 = new TextField();
        hBox1.getChildren().addAll(label1,textField1);
        HBox hBox2 = new HBox();
        Label label2 = new Label("请输入重命名后的名字起始编号：");
        TextField textField2 = new TextField();
        hBox2.getChildren().addAll(label2,textField2);

        vBox.getChildren().addAll(hBox1,hBox2);
        Scene scene = new Scene(vBox);
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
