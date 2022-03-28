package design.ViewController;

import design.Controller.ShowImageController;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * User: 86176
 * Date: 2022/3/28
 * Time: 15:45
 * Description:
 */
public class ShowImageViewController {
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView left_button;
    @FXML
    private ImageView right_button;
    @FXML
    private GridPane imagePane;
//    @FXML
    private HBox imageBox;

    private ArrayList<Image> imageList;

    private ShowImageController showImageController;

    private Stage imageShowStage;

    public ShowImageViewController(){
        imageShowStage = new Stage();
        imageShowStage.setWidth(1200);
        imageShowStage.setHeight(600);
        imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: yellow");
    }
    public void initialize(){
        left_button.setImage(new Image("file:JavaDesign/src/main/resources/img/left.jpg"));
        right_button.setImage(new Image("file:JavaDesign/src/main/resources/img/right.jpg"));
        AnchorPane.setTopAnchor(left_button,imageShowStage.getHeight()/2-left_button.prefHeight(-1)/2);
        AnchorPane.setTopAnchor(right_button,imageShowStage.getHeight()/2-right_button.prefHeight(-1)/2);

        AnchorPane.setLeftAnchor(right_button,imageShowStage.getWidth()-right_button.prefWidth(-1));
        AnchorPane.setLeftAnchor(imagePane,left_button.prefWidth(-1));
//        imagePane.setPrefHeight(imageShowStage.getHeight()-100);
//        imagePane.setPrefWidth(imageShowStage.getWidth()-left_button.prefWidth(-1)*2);
        imagePane.getChildren().add(imageBox);
        imagePane.setStyle("-fx-background-color: green");

        right_button.setOnMouseClicked(event -> {
            showImageController.setFileIndex(showImageController.getFileIndex()+1);
            imageBox.getChildren().clear();
            ImageView newImage = showImageController.getImageView(imagePane);
            imageBox.getChildren().add(newImage);

            System.out.println("向右按钮");
        });
        left_button.setOnMouseClicked(event -> {
            showImageController.setFileIndex(showImageController.getFileIndex()-1);
            imageBox.getChildren().clear();
            ImageView newImage = showImageController.getImageView(imagePane);
            imageBox.getChildren().add(newImage);
            System.out.println("向左按钮");
        });
        imageShowStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imagePane.setPrefWidth(imageShowStage.getWidth()-left_button.prefWidth(-1)*2);
//                ImageShowUtils.
            }
        });
        imageShowStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imagePane.setPrefHeight(imageShowStage.getHeight()-100);
            }
        });
    }

    public HBox getImageBox() {
        return imageBox;
    }
    public Stage getImageShowStage(){
        return imageShowStage;
    }

    public void setImageShowController(ShowImageController showImageController) {
        this.showImageController = showImageController;
    }
}
