package design.ViewTest;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * User: 86176
 * Date: 2022/3/22
 * Time: 12:52
 * Description:
 */
public class showTest3 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = getPane(800, 400);

        AnchorPane root = new AnchorPane();
        AnchorPane.setTopAnchor(pane,100.0);
        AnchorPane.setLeftAnchor(pane,100.0);
        root.getChildren().addAll(pane);


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(1000);
        primaryStage.show();
    }
    public Pane getPane(int width,int height){
        ImageView left_button = new ImageView("file:JavaDesign/src/main/resources/img/left.jpg");
        left_button.setPreserveRatio(true);
        left_button.setFitHeight(100);
//        left_button.setTranslateY(height/2-left_button.getFitHeight()/2);
        ImageView right_button = new ImageView("file:JavaDesign/src/main/resources/img/right.jpg");
        right_button.setPreserveRatio(true);
        right_button.setFitHeight(100);
//        right_button.setTranslateY(height/2-right_button.getFitHeight()/2);

        ImageView img1 = new ImageView("file:JavaDesign/src/main/resources/img/img1.jpg");
        img1.setPreserveRatio(true);
        img1.setFitWidth(width/1.8);
        ImageView img2 = new ImageView("file:JavaDesign/src/main/resources/img/img2.jpg");
        img2.setPreserveRatio(true);
        img2.setFitWidth(width/1.8);
        ImageView img3 = new ImageView("file:JavaDesign/src/main/resources/img/img3.jpg");
        img3.setPreserveRatio(true);
        img3.setFitWidth(width/1.8);

        double trans_x1 = 0;
        double trans_x2 = width/2 - img2.getFitWidth()/2;
        double trans_x3 = width - img3.getFitWidth();
        double trans_y = height/2 - img2.prefHeight(-1)/2;
        double trans_z = 150;

        img1.setTranslateX(trans_x1);
        img2.setTranslateX(trans_x2);
        img3.setTranslateX(trans_x3);

        img1.setTranslateY(trans_y);
        img2.setTranslateY(trans_y);
        img3.setTranslateY(trans_y);

        img1.setTranslateZ(trans_z);
        img2.setTranslateZ(0);
        img3.setTranslateZ(trans_z);

        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add(img1);
        imageViews.add(img2);
        imageViews.add(img3);

        HBox buttonBox = new HBox(width-left_button.prefWidth(-1)*2);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(left_button,right_button);

        StackPane stackPane = new StackPane();

        AnchorPane imagePane = new AnchorPane();
        imagePane.getChildren().addAll(img1,img2,img3);

        SubScene subScene = new SubScene(imagePane,width,height,true, SceneAntialiasing.BALANCED);
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
        subScene.setCamera(perspectiveCamera);

        stackPane.getChildren().addAll(subScene,buttonBox);
        stackPane.setStyle("-fx-background-color: pink");

        right_button.setOnMouseClicked(event -> {
//            ImageShowUtils.right_to_left(imageViews,trans_x1,trans_x2,trans_x3,trans_z);
        });

        return stackPane;
    }
}
