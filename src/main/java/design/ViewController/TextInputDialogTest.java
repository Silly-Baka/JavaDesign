package design.ViewController;

import design.pojo.RenameProperty;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * User: 86176
 * Date: 2022/4/8
 * Time: 16:30
 * Description:
 */
public class TextInputDialogTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Dialog<RenameProperty> dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES,ButtonType.NO);

        TextField prefix = new TextField();
        prefix.setPromptText("新名字的前缀");

        TextField startNum = new TextField();
        startNum.setPromptText("输入编号起始");

        TextField numBits = new TextField();
        numBits.setPromptText("编号的位数");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("名字前缀："),0,0);
        gridPane.add(prefix,0,1);
        gridPane.add(new Label("编号起始："),1,0);
        gridPane.add(startNum,1,1);
        gridPane.add(new Label("编号位数："),2,0);
        gridPane.add(numBits,2,1);


        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(new Callback<ButtonType, RenameProperty>() {
            @Override
            public RenameProperty call(ButtonType param) {
                if(param == ButtonType.YES){
                    return new RenameProperty(prefix.getText(),Integer.valueOf(startNum.getText()),Integer.valueOf(numBits.getText()));
                }
                return null;
            }
        });

        Optional<RenameProperty> renameProperty = dialog.showAndWait();
        renameProperty.ifPresent(rename -> {
            System.out.println(rename);
        });

        Pane pane = new Pane();

        Scene scene = new Scene(pane);
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
