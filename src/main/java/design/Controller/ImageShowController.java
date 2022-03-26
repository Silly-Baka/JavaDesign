package design.Controller;

import design.Utils.ImageShowUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * User: 86176
 * Date: 2022/3/20
 * Time: 14:13
 * Description:  负责幻灯片操作以及展示图片
 */
public class ImageShowController {

    private final ImageView left_button;
    private final ImageView right_button;
    private Integer fileIndex;

    private Stage imageShowStage;

    private ArrayList<Image> imageList;

    private GridPane imagePane;

    public ImageShowController(){
        left_button = new ImageView("file:JavaDesign/src/main/resources/img/left.jpg");
        left_button.setPreserveRatio(true);
        left_button.setFitHeight(100);
//        left_button.setVisible(false);
        right_button = new ImageView("file:JavaDesign/src/main/resources/img/right.jpg");
        right_button.setPreserveRatio(true);
        right_button.setFitHeight(100);
//        right_button.setVisible(false);
        imageShowStage = new Stage();
        imageShowStage.setHeight(800);
        imageShowStage.setWidth(1400);

        imagePane = new GridPane();
        imagePane.setAlignment(Pos.CENTER);
        imagePane.setStyle("-fx-background-color: pink");
    }
    /**
     * 创建一个展示幻灯片的新窗口
     * @param presentFileList 当前目录的图片文件列表
     * @param file 选择的图片文件 用于获取索引
     */
    public void createStage(ArrayList<File> presentFileList,File file){

        imageList = ImageShowUtils.createImageList(presentFileList);
        // 获取选中的图片所在的索引
        fileIndex = presentFileList.indexOf(file);

        ImageView presentImage = getImageView();

        AnchorPane root = new AnchorPane();

        AnchorPane.setTopAnchor(left_button,imageShowStage.getHeight()/2-left_button.prefHeight(-1)/2);
        AnchorPane.setTopAnchor(right_button,imageShowStage.getHeight()/2-right_button.prefHeight(-1)/2);
        AnchorPane.setLeftAnchor(right_button,imageShowStage.getWidth()-right_button.prefWidth(-1));

        HBox imageBox = new HBox();
        imageBox.setStyle("-fx-background-color: green");
        imageBox.setAlignment(Pos.CENTER);
        imageBox.getChildren().add(presentImage);

        AnchorPane.setLeftAnchor(imagePane,left_button.prefWidth(-1));
//        AnchorPane.setTopAnchor(imagePane,50.0);
        imagePane.setPrefHeight(imageShowStage.getHeight()-100);
        imagePane.setPrefWidth(imageShowStage.getWidth()-left_button.prefWidth(-1)*2);
        imagePane.getChildren().add(imageBox);

        root.getChildren().addAll(left_button,right_button,imagePane);

        Scene scene = new Scene(root);

        imageShowStage.setScene(scene);
        imageShowStage.show();


        right_button.setOnMouseClicked(event -> {
            fileIndex+=1;
            imageBox.getChildren().clear();
            ImageView newImage = getImageView();
            imageBox.getChildren().add(newImage);
        });
        left_button.setOnMouseClicked(event -> {
            fileIndex-=1;
            imageBox.getChildren().clear();
            ImageView newImage = getImageView();
            imageBox.getChildren().add(newImage);
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

    public ImageView getImageView(){
        Image image = imageList.get(fileIndex);

        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();

        double paneWidth = imagePane.getPrefWidth();
        double paneHeight = imagePane.getPrefHeight();

        double finalWidth = Math.min(imgWidth,paneWidth);
        double finalHeight = Math.min(imgHeight,paneHeight);

        ImageView imageView = new ImageView();
        ImageShowUtils.setImageSize(imageView,imgWidth,imgHeight,paneWidth,paneHeight);
        imageView.setImage(image);

        return imageView;
    }

}
