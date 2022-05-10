package design.controller;

import design.Utils.AlertUtils;
import design.Utils.ImageShowUtils;
import design.Main;
import design.viewController.ShowImageViewController;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * User: 86176
 * Date: 2022/3/20
 * Time: 14:13
 * Description:  负责幻灯片操作以及展示图片
 */
public class ShowImageController {
    private Integer fileIndex;

    private Stage imageShowStage;

    private ArrayList<Image> imageList;

    private ArrayList<File> presentFileList;

    private AnchorPane root;

    private ShowImageViewController showImageViewController;

    private final HBox imageBox;

    private final HBox tempImageBox;

    private static ImageView oldImageView;

    private static ImageView nextImageView;

    private final TranslateTransition tt;

    private final DisplacementMap disp1;

    private final DisplacementMap disp2;

    public ShowImageController(){
        getViewController();
        imageBox = showImageViewController.getImageBox();
        imageShowStage = showImageViewController.getImageShowStage();
        showImageViewController.setImageShowController(this);
        tempImageBox = showImageViewController.getTempImageBox();
        tt = new TranslateTransition();
        disp1 = new DisplacementMap();
        imageBox.setEffect(disp1);
        disp2 = new DisplacementMap();
        tempImageBox.setEffect(disp2);
    }
    public void getViewController() {
        try {
            URL url = getClass().getResource("/design/viewController/ShowImageView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            root = fxmlLoader.load();
            showImageViewController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建一个展示幻灯片的新窗口
     * @param presentFileList 当前目录的图片文件列表
     * @param file 选择的图片文件 用于获取索引
     */
    public void createStage(ArrayList<File> presentFileList,File file){
        this.presentFileList = presentFileList;
        imageBox.getChildren().clear();
        tempImageBox.getChildren().clear();
        imageList = ImageShowUtils.createImageList(presentFileList);
        // 获取选中的图片所在的索引
        fileIndex = presentFileList.indexOf(file);

        ImageView presentImage = getImageView();

        imageBox.getChildren().add(presentImage);

        if(imageShowStage.getScene()==null){
            Scene scene = new Scene(root);
            imageShowStage.setScene(scene);
        }
        imageShowStage.setTitle(file.getName());
        imageShowStage.show();
    }

    /**
     * 用于切换图片功能
     * 获取 fileIndex所标记的图片并包装成ImageView  同时自适应更改宽高
     * @return 返回新图片
     */
    public ImageView getImageView(){
        Image image = imageList.get(fileIndex);
        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();

        double paneWidth = 1000;
        double paneHeight = 500;

        ImageView imageView = new ImageView();
        ImageShowUtils.setImageSize(imageView,imgWidth,imgHeight,paneWidth,paneHeight);
        imageView.setImage(image);

        return imageView;
    }

    /**
     * 用于切换图片功能
     * 获取 fileIndex所标记的图片并包装成ImageView  同时自适应更改宽高
     * @param imagePane 用于获取图片布局的大小
     * @return 返回新图片
     */
    public ImageView getImageView(GridPane imagePane){
        Image image = imageList.get(fileIndex);

        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();

        double paneWidth = imagePane.getPrefWidth();
        double paneHeight = imagePane.getPrefHeight();

        ImageView imageView = new ImageView();
        ImageShowUtils.setImageSize(imageView,imgWidth,imgHeight,paneWidth,paneHeight);
        imageView.setImage(image);

        return imageView;
    }

    public ImageView getNextImageView(GridPane imagePane){
        Image image = imageList.get(fileIndex+1);

        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();

        double paneWidth = imagePane.getPrefWidth();
        double paneHeight = imagePane.getPrefHeight();

        ImageView imageView = new ImageView();
        ImageShowUtils.setImageSize(imageView,imgWidth,imgHeight,paneWidth,paneHeight);
        imageView.setImage(image);

        return imageView;
    }

    /**
     * @return 返回当前图片索引
     */
    public Integer getFileIndex() {
        return fileIndex;
    }

    /**
     * 更改图片索引
     * @param fileIndex 新的图片索引
     */
    public void setFileIndex(Integer fileIndex) {
        // 到达最后一张 无法再下一张
        if(fileIndex >= imageList.size()){
            AlertUtils.showAlert(Alert.AlertType.WARNING,"图片已到达最后一张！无法再切换下一张！","",imageShowStage);
        }else if (fileIndex < 0){
            // 第一张无法再前一张
            AlertUtils.showAlert(Alert.AlertType.WARNING,"图片已是第一张！无法再切换上一张！","",imageShowStage);
        }else {
            this.fileIndex = fileIndex;
        }
    }
    public void setImageShowStageTitle(){
        imageShowStage.setTitle(presentFileList.get(fileIndex).getName());
    }

    /**
     * 幻灯片播放功能
     */
    public void slideshowAction(GridPane imagePane){
        Pane pane = new Pane();
        tt.setDuration(Duration.seconds(1));
        tt.setNode(pane);
        tt.setFromX(imagePane.getPrefWidth());
        tt.setToX(-10);
        tt.setInterpolator(Interpolator.LINEAR);

    //        tempImageBox.setStyle("-fx-background-color: pink");
        // 如果未到达最后一张 则获取下一张图片 否则暂停
        if(fileIndex < presentFileList.size()-1){
            nextImageView = refreshImageAndPlay(imagePane);
        }else{
            // 幻灯片结束
            AlertUtils.AnimationShowAlert(Alert.AlertType.CONFIRMATION,"幻灯片播放已完成！","",imageShowStage);
        }
        pane.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                disp2.setOffsetX(-newValue.doubleValue()/imagePane.getWidth());
                disp1.setOffsetX(1-newValue.doubleValue()/imagePane.getWidth());
            }
        });
        tt.setOnFinished(event -> {
            oldImageView = nextImageView;
            disp1.setOffsetX(0);
            imageBox.getChildren().clear();
            imageBox.getChildren().add(oldImageView);
            fileIndex++;
            setImageShowStageTitle();
            // 如果幻灯片未到最后一张 则暂停一秒后继续播放
            if(fileIndex < presentFileList.size()-1){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nextImageView = refreshImageAndPlay(imagePane);
            }else{
                // 幻灯片结束
                AlertUtils.AnimationShowAlert(Alert.AlertType.CONFIRMATION,"幻灯片播放已完成！","",imageShowStage);
            }
        });
        // 设置幻灯片界面关闭的监听器
        imageShowStage.setOnCloseRequest(event -> {
            // 如果动画仍然在继续  则关闭幻灯片窗口就停止该动画
            if(tt.getStatus()== Animation.Status.RUNNING){
                tt.stop();
                disp1.setOffsetX(0);
                tempImageBox.getChildren().clear();
                AlertUtils.AnimationShowAlert(Alert.AlertType.CONFIRMATION,"幻灯片播放已手动停止！","", Main.mainStage);
            }
        });
    }

    /**
     * 暂停当前的幻灯片 并把图片恢复到原位
     */
    public void slideshowStopAction(){
        // 如果动画仍然在继续  则停止该动画
        if(tt.getStatus()== Animation.Status.RUNNING){
            tt.stop();
            disp1.setOffsetX(0);
            tempImageBox.getChildren().clear();
            AlertUtils.AnimationShowAlert(Alert.AlertType.CONFIRMATION,"幻灯片播放已手动停止！","", imageShowStage);
        }
    }

    /**
     * 刷新下一张照片同时再次开始幻灯片动画
     * @param imagePane 当前图片布局
     * @return 返回下一张照片
     */
    public ImageView refreshImageAndPlay(GridPane imagePane){
        tempImageBox.getChildren().clear();
        ImageView nextImageView = getNextImageView(imagePane);
        tempImageBox.getChildren().add(nextImageView);
        tt.play();
        return nextImageView;
    }

    /**
     * 缩放操作
     * @param imageView 传入的图片组件
     */
    //放大
    public void enlarge(ImageView imageView){
        double factor=1.2;
        imageView.setFitWidth(imageView.getFitWidth()*factor);
        imageView.setFitHeight(imageView.getFitHeight()*factor);

    }
    //缩小
    public void reduce(ImageView imageView ){
        double factor=1/1.2;
        imageView.setFitWidth(imageView.getFitWidth()*factor);
        imageView.setFitHeight(imageView.getFitHeight()*factor);
    }
}
