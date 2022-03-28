package design.ViewController;

import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * User: 86176
 * Date: 2022/3/21
 * Time: 18:46
 * Description:
 */
public class showTest2 extends Application {

    private ImageView left_button;
    private ImageView right_button;

    private double imgx_1,imgx_2,imgx_3,imgy,imgz;

    private ArrayList<ImageView> imageViewList;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = getPane(800, 400);

        AnchorPane root = new AnchorPane();
        AnchorPane.setLeftAnchor(pane,100.0);
        AnchorPane.setTopAnchor(pane,100.0);

        root.getChildren().addAll(pane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);
        primaryStage.show();

    }
    public Pane getPane(int w,int h){

        left_button = new ImageView("file:JavaDesign/src/main/resources/img/left.jpg");
        left_button.setPreserveRatio(true);
        left_button.setFitWidth(100);
        left_button.setFitHeight(100);
        right_button = new ImageView("file:JavaDesign/src/main/resources/img/right.jpg");
        right_button.setPreserveRatio(true);
        right_button.setFitHeight(100);
        right_button.setFitWidth(100);

        HBox hBox = new HBox(w-((left_button.prefWidth(-1))*2));
        hBox.getChildren().addAll(left_button,right_button);
        hBox.setAlignment(Pos.CENTER);

        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: pink");

        ImageView img1 = new ImageView("file:JavaDesign/src/main/resources/img/img1.jpg");
        img1.setFitWidth(w/1.7);
//        img1.setFitHeight(h/1.7);
        img1.setPreserveRatio(true);

        ImageView img2 = new ImageView("file:JavaDesign/src/main/resources/img/img2.jpg");
        img2.setFitWidth(w/1.7);
//        img2.setFitHeight(h/1.7);
        img2.setPreserveRatio(true);
        ImageView img3 = new ImageView("file:JavaDesign/src/main/resources/img/img3.jpg");
        img3.setFitWidth(w/1.7);
//        img3.setFitHeight(h/1.7);
        img3.setPreserveRatio(true);
//        img3.setVisible(false);

        imgx_1 = 0;
        imgx_2 = w/2 - img2.getFitWidth()/2;
        imgx_3 = w - img3.getFitWidth();

        imgy = h/2-img2.prefHeight(-1)/2;

        imgz = 150;

        img1.setTranslateX(imgx_1);
        img2.setTranslateX(imgx_2);
        img3.setTranslateX(imgx_3);

        img1.setTranslateY(imgy);
        img2.setTranslateY(imgy);
        img3.setTranslateY(imgy);

        img1.setTranslateZ(imgz);
        img2.setTranslateZ(0);
        img3.setTranslateZ(imgz);

        imageViewList = new ArrayList<>();
        imageViewList.add(img1);
        imageViewList.add(img2);
        imageViewList.add(img3);

        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(img1,img2,img3);

        SubScene subScene = new SubScene(ap,w,h,true, SceneAntialiasing.BALANCED);
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
        subScene.setCamera(perspectiveCamera);

        pane.getChildren().addAll(subScene,hBox);


        right_button.setOnMouseClicked(event -> {
            right_to_left(imageViewList);
        });
        return pane;
    }
    public void right_to_left(ArrayList<ImageView> imageViewList){
        ImageView img1 = imageViewList.get(0);
        ImageView img2 = imageViewList.get(1);
        ImageView img3 = imageViewList.get(2);
        left_to_middle_animation(img1);
        middle_to_right_animation(img2);
        right_to_left_animation(img3);

        imageViewList.clear();
        imageViewList.add(img3);
        imageViewList.add(img1);
        imageViewList.add(img2);
    }
    public void left_to_middle_animation(ImageView imageView){
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(imageView);
        tt.setFromX(imgx_1);
        tt.setFromZ(imgz);
        tt.setDuration(Duration.seconds(2));

        tt.setToX(imgx_2);
        tt.setToZ(0);
        tt.play();
    }
    public void middle_to_right_animation(ImageView imageView){
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(imageView);
        tt.setFromX(imgx_2);
        tt.setFromZ(0);
        tt.setDuration(Duration.seconds(2));

        tt.setToX(imgx_3);
        tt.setToZ(imgz);
        tt.play();
    }
    public void right_to_left_animation(ImageView imageView){
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(imageView);
        tt.setFromX(imgx_3);
        tt.setFromZ(imgz);
        tt.setDuration(Duration.seconds(2));

        tt.setToX(imgx_1);
        tt.setToZ(imgz);
        tt.play();
    }
    public void trans_animation(){


    }
}
