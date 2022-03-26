package design.ViewTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 15:09
 * Description:
 */
public class demoTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        File file = new File("JavaDesign/src/main/resources/img/directory.jpg");
        System.out.println(file.exists());

        Button button = new Button("按钮");

        Image image = new Image("file:"+file.getPath(),100,100,true,true);
        ImageView imageView = new ImageView(image);

        Label imageLable = new Label();
        imageLable.setText(file.getName());
        imageLable.setGraphic(imageView);
        imageLable.setContentDisplay(ContentDisplay.TOP);
        imageLable.setTextAlignment(TextAlignment.CENTER);
        imageLable.setGraphicTextGap(50);

        Tooltip tooltip = new Tooltip();
        tooltip.setText("这是一个提示框");

        tooltip.setContentDisplay(ContentDisplay.BOTTOM);
        imageLable.setTooltip(tooltip);

        MenuItem menuItem1 = new MenuItem("按钮1");
        MenuItem menuItem2 = new MenuItem("按钮2");
        ContextMenu contextMenu = new ContextMenu(menuItem1,menuItem2);

        imageLable.setContextMenu(contextMenu);

        Label label2 = new Label("第二个label");
        label2.setContextMenu(contextMenu);

        AnchorPane an = new AnchorPane();

        AnchorPane.setLeftAnchor(button,500.0);
        AnchorPane.setTopAnchor(button,500.0);
        AnchorPane.setLeftAnchor(imageLable,100.0);
        AnchorPane.setTopAnchor(imageLable,100.0);
        an.getChildren().addAll(imageLable,label2,button);

        Scene scene = new Scene(an);

        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();


        button.setOnMouseClicked(event -> {
            Button bt = new Button("新添加的按钮");
            AnchorPane.setLeftAnchor(bt,300.0);
            AnchorPane.setTopAnchor(bt,200.0);
            an.getChildren().add(bt);
        });
        imageLable.setOnMouseClicked(event -> {
            imageLable.setStyle("-fx-border-color: blue");
        });
        imageLable.setOnMouseExited(event -> {
            imageLable.setStyle("-fx-border-color: white");
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
