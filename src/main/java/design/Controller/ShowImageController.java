package design.Controller;

import design.Utils.AlertUtils;
import design.Utils.ImageShowUtils;
import design.ViewController.ShowImageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

    private HBox imageBox;

    public ShowImageController(){
        getController();
        imageBox = showImageViewController.getImageBox();
        imageShowStage = showImageViewController.getImageShowStage();
        showImageViewController.setImageShowController(this);
    }
    public void getController() {
        try {
//            URL url = getClass().getResource("ShowImageView.fxml");
            URL url = getClass().getResource("/design/ViewController/ShowImageView.fxml");
//            URL url2 = getClass().getClassLoader().getResource("design/ViewController/ShowImageView.fxml");
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
}
