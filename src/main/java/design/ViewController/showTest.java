package design.ViewController;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * User: 86176
 * Date: 2022/3/20
 * Time: 15:53
 * Description:
 */
public class showTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button button = new Button("开始");
        Button button1 = new Button("回去");
        Button bt1 = new Button("按钮1");
        Button bt2 = new Button("按钮2");

        HBox hBox1 = new HBox();
        hBox1.setStyle("-fx-border-color: blue;-fx-border-width: 2;-fx-background-color: green");
        hBox1.setAlignment(Pos.CENTER);
        ImageView imageView1 = new ImageView("file:JavaDesign/src/main/resources/img/apic27858.jpg");
        imageView1.setFitHeight(400);
        imageView1.setFitWidth(400);
        hBox1.getChildren().add(bt1);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setStyle("-fx-border-color: green;-fx-border-width: 2;-fx-background-color: pink");
        ImageView imageView2 = new ImageView("file:JavaDesign/src/main/resources/img/img2.jpg");
        imageView2.setFitHeight(400);
        imageView2.setFitWidth(400);
        hBox2.getChildren().add(bt2);


        StackPane stackPane = new StackPane();
        stackPane.setPrefWidth(400);
        stackPane.setPrefHeight(400);
        stackPane.setStyle("-fx-border-color: red;-fx-border-width: 2");
//        stackPane.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,null,new BorderWidths(2))));

        stackPane.getChildren().addAll(hBox1,hBox2);


        // 幻灯片效果
//        hBox2.setTranslateX(200);
        Pane pane = new Pane();
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.seconds(1));
        tt.setNode(pane);
        tt.setFromX(0);
        tt.setToX(400);
//        tt.setAutoReverse(true);
//        tt.setCycleCount(Animation.INDEFINITE);
        tt.setInterpolator(Interpolator.LINEAR);


        DisplacementMap disp = new DisplacementMap();
//        disp.setOffsetX(-0.5);
//        disp.setWrap(true);
        hBox2.setEffect(disp);

        DisplacementMap disp2 = new DisplacementMap();
        hBox1.setEffect(disp2);


        AnchorPane root = new AnchorPane();

        AnchorPane.setLeftAnchor(stackPane,100.0);
        AnchorPane.setTopAnchor(stackPane,100.0);

        AnchorPane.setLeftAnchor(button1,100.0);

        root.getChildren().addAll(stackPane,button,button1);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);
        primaryStage.show();

        button.setOnAction(event -> {
            tt.play();
        });
        pane.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                hBox2.setTranslateX(newValue.doubleValue());
                disp.setOffsetX(-newValue.doubleValue()/400);

                disp2.setOffsetX((1-newValue.doubleValue()/400));
            }
        });
        tt.setOnFinished(event -> {
//            stackPane.getChildren().remove(hBox2);
//            hBox2.setVisible(false);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
