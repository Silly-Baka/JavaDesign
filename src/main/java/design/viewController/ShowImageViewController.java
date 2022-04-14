package design.viewController;

import design.Utils.ImageShowUtils;
import design.controller.ShowImageController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    private ImageView slideShowButton;
    @FXML
    private ScrollPane imageScrollPane;
    @FXML
    private ImageView slideShowStopButton;

    private final StackPane stackPane;

    @FXML
    private ImageView enlargeButton;
    @FXML
    private ImageView reduceButton;
    @FXML
    private ImageView beforeButton;
    @FXML
    private ImageView nextButton;
    @FXML
    private ImageView recoverButton;
    @FXML
    private FlowPane actionPane;
//    @FXML
    private final HBox imageBox;

    private final HBox tempImageBox;

    private ShowImageController showImageController;

    private final Stage imageShowStage;

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
        AnchorPane.setTopAnchor(left_button,imageShowStage.getHeight()/2-left_button.prefHeight(-1));
        AnchorPane.setTopAnchor(right_button,imageShowStage.getHeight()/2-right_button.prefHeight(-1));
        AnchorPane.setLeftAnchor(right_button,imageShowStage.getWidth()-right_button.prefWidth(-1)-12);

//        imagePane.getChildren().add(stackPane);
        imageScrollPane.setContent(stackPane);
        imageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        imageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        imageScrollPane.setPannable(true);

        enlargeButton.setImage(new Image(getClass().getResource("/img/enlarge.jpg").toString()));
        reduceButton.setImage(new Image(getClass().getResource("/img/reduce.jpg").toString()));
        nextButton.setImage(new Image(getClass().getResource("/img/left.jpg").toString()));
        beforeButton.setImage(new Image(getClass().getResource("/img/right.jpg").toString()));
        slideShowButton.setImage(new Image(getClass().getResource("/img/play.jpg").toString()));
        slideShowStopButton.setImage(new Image(getClass().getResource("/img/pause.jpg").toString()));
        recoverButton.setImage(new Image(getClass().getResource("/img/recover.png").toString()));

        enlargeButton.setOnMouseClicked(event -> {
            ImageView imageView = (ImageView) imageBox.getChildren().get(0);
            showImageController.enlarge(imageView);
        } );
        enlargeButton.setOnMouseMoved(event -> {
            enlargeButton.setCursor(Cursor.HAND);
        });
        reduceButton.setOnMouseClicked(event -> {
            ImageView imageView = (ImageView) imageBox.getChildren().get(0);
            showImageController.reduce(imageView);
        });
        reduceButton.setOnMouseMoved(event -> {
            reduceButton.setCursor(Cursor.HAND);
        });
        recoverButton.setOnMouseMoved(event -> {
            recoverButton.setCursor(Cursor.HAND);
        });
        right_button.setOnMouseClicked(event -> {
            showImageController.setFileIndex(showImageController.getFileIndex()+1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });
        right_button.setOnMouseMoved(event -> {
            right_button.setCursor(Cursor.HAND);
        });
        left_button.setOnMouseClicked(event -> {
            showImageController.setFileIndex(showImageController.getFileIndex()-1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });
        left_button.setOnMouseMoved(event -> {
            left_button.setCursor(Cursor.HAND);
        });
        nextButton.setOnMouseClicked(event ->{
            showImageController.setFileIndex(showImageController.getFileIndex()+1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });
        nextButton.setOnMouseMoved(event -> {
            nextButton.setCursor(Cursor.HAND);
        });
        beforeButton.setOnMouseClicked(event ->{
            showImageController.setFileIndex(showImageController.getFileIndex()-1);
            showImageController.setImageShowStageTitle();
            refreshImageBox();
        });
        beforeButton.setOnMouseMoved(event -> {
            beforeButton.setCursor(Cursor.HAND);
        });
        recoverButton.setOnMouseClicked(event -> {
            ImageView imageView = (ImageView) imageBox.getChildren().get(0);
            ImageShowUtils.setImageSize(imageView,imageView.getFitWidth(),imageView.getFitHeight(),imagePane.getWidth(),imagePane.getHeight());
        });
        imageShowStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imagePane.setPrefWidth(newValue.doubleValue()-left_button.prefWidth(-1)*2);
                actionPane.setPrefWidth(newValue.doubleValue()-left_button.prefWidth(-1)*2);
                AnchorPane.setLeftAnchor(right_button,newValue.doubleValue()-right_button.prefWidth(-1)-12);
                AnchorPane.setLeftAnchor(imagePane,left_button.prefWidth(-1));
                AnchorPane.setLeftAnchor(actionPane,left_button.prefWidth(-1));
                refreshImageBox();
            }
        });
        imageShowStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imagePane.setPrefHeight(newValue.doubleValue()-150);
                actionPane.setPrefHeight((imagePane.getPrefHeight()-1)/6);
                AnchorPane.setTopAnchor(left_button,newValue.doubleValue()/2-left_button.prefHeight(-1));
                AnchorPane.setTopAnchor(right_button,newValue.doubleValue()/2-right_button.prefHeight(-1));
                AnchorPane.setTopAnchor(actionPane,imagePane.prefHeight(-1));
                refreshImageBox();

            }
        });

        // 设置幻灯片播放按钮的点击事件
        slideShowButton.setOnMouseClicked(event -> {
            slideshowAction(imagePane);
        });
        slideShowButton.setOnMouseMoved(event -> {
            slideShowButton.setCursor(Cursor.HAND);
        });
        // 设置幻灯片暂停按钮的暂停事件
        slideShowStopButton.setOnMouseClicked(event -> {
            slideshowStopAction();
        });
        slideShowStopButton.setOnMouseMoved(event -> {
            slideShowStopButton.setCursor(Cursor.HAND);
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
