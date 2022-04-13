package design.viewController;

import design.controller.ShowImageController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * User: 86176
 * Date: 2022/3/28
 * Time: 15:45
 * Description: 幻灯片界面的视图控制类
 */
public class ShowImageViewController {
    @FXML
    private ImageView left_button;
    @FXML
    private ImageView right_button;
    @FXML
    private GridPane imagePane;
    @FXML
    private Button slideShowButton;
    @FXML
    private Button slideShowStopButton;

    private StackPane stackPane;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;

//    @FXML
    private HBox imageBox;

    private HBox tempImageBox;

    private ShowImageController showImageController;

    private Stage imageShowStage;

    public ShowImageViewController(){
        imageShowStage = new Stage();
        imageShowStage.setWidth(1200);
        imageShowStage.setHeight(600);
        imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
//        imageBox.setStyle("-fx-background-color: yellow");

        tempImageBox = new HBox();
        tempImageBox.setAlignment(Pos.CENTER);
//        tempImageBox.setStyle("-fx-background-color: pink");
        stackPane = new StackPane();
        stackPane.getChildren().addAll(tempImageBox,imageBox);

    }
    public void initialize(){
        // 初始化图片切换按钮
        left_button.setImage(new Image(getClass().getResource("/img/left.jpg").toString()));
        right_button.setImage(new Image(getClass().getResource("/img/right.jpg").toString()));
        AnchorPane.setTopAnchor(left_button,imageShowStage.getHeight()/2-left_button.prefHeight(-1)/2);
        AnchorPane.setTopAnchor(right_button,imageShowStage.getHeight()/2-right_button.prefHeight(-1)/2);

        AnchorPane.setLeftAnchor(right_button,imageShowStage.getWidth()-right_button.prefWidth(-1));
        AnchorPane.setLeftAnchor(imagePane,left_button.prefWidth(-1));

        imagePane.getChildren().add(stackPane);

        Image image1=new Image(getClass().getResource("/img/+.jpg").toString());
        ImageView imageView1=new ImageView();
        imageView1.setImage(image1);
        imageView1.setFitHeight(20);
        imageView1.setFitWidth(20);
        b1.setGraphic(imageView1);
        b1.setContentDisplay(ContentDisplay.RIGHT);

        Image image2=new Image(getClass().getResource("/img/-.jpg").toString());
        ImageView imageView2=new ImageView();
        imageView2.setImage(image2);
        imageView2.setFitHeight(20);
        imageView2.setFitWidth(20);
        b2.setGraphic(imageView2);
        b2.setContentDisplay(ContentDisplay.RIGHT);

        b1.setOnMouseClicked(event -> {
            ImageView imageView=new ImageView();
            showImageController.enlarge(imagePane,imageView);

        } );
        b2.setOnMouseClicked(event -> {
            ImageView imageView=new ImageView();
            showImageController.reduce(imagePane,imageView);
        });


        right_button.setOnMouseClicked(event -> {
            showImageController.setFileIndex(showImageController.getFileIndex()+1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });
        left_button.setOnMouseClicked(event -> {
            showImageController.setFileIndex(showImageController.getFileIndex()-1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });

        b4.setOnMouseClicked(event ->{
            showImageController.setFileIndex(showImageController.getFileIndex()+1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });
        b3.setOnMouseClicked(event ->{
            showImageController.setFileIndex(showImageController.getFileIndex()-1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });

        imageShowStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imagePane.setPrefWidth(newValue.doubleValue()-left_button.prefWidth(-1)*2);
                AnchorPane.setLeftAnchor(right_button,newValue.doubleValue()-right_button.prefWidth(-1));
                AnchorPane.setLeftAnchor(imagePane,left_button.prefWidth(-1));
                refreshImageBox();
            }
        });
        imageShowStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imagePane.setPrefHeight(newValue.doubleValue()-100);
                AnchorPane.setTopAnchor(left_button,newValue.doubleValue()/2-left_button.prefHeight(-1)/2);
                AnchorPane.setTopAnchor(right_button,newValue.doubleValue()/2-right_button.prefHeight(-1)/2);
                refreshImageBox();
            }
        });

        // 设置幻灯片播放按钮的点击事件
        slideShowButton.setOnAction(event -> {
            slideshowAction(imagePane);
        });
        // 设置幻灯片暂停按钮的暂停事件
        slideShowStopButton.setOnAction(event -> {
            slideshowStopAction();
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

    public HBox getTempImageBox() {
        return tempImageBox;
    }

    /**
     * 更新ImageBox中的图片
     */
    public void refreshImageBox(){
        imageBox.getChildren().clear();
        ImageView newImage = showImageController.getImageView(imagePane);
        imageBox.getChildren().add(newImage);
    }
    /**
     * 幻灯片播放功能
     */
    public void slideshowAction(GridPane imagePane){
        showImageController.slideshowAction(imagePane);
    }

    /**
     * 幻灯片暂停功能
     */
    public void slideshowStopAction(){
        showImageController.slideshowStopAction();
    }
}
