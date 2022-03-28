package design.Controller;

import design.Utils.ImageShowUtils;
import design.ViewController.ShowImageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        imageShowStage.show();
    }

    public ImageView getImageView(){
        Image image = imageList.get(fileIndex);

        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();

        double paneWidth = 1100;
        double paneHeight = 500;

        double finalWidth = Math.min(imgWidth,paneWidth);
        double finalHeight = Math.min(imgHeight,paneHeight);

        ImageView imageView = new ImageView();
        ImageShowUtils.setImageSize(imageView,imgWidth,imgHeight,paneWidth,paneHeight);
        imageView.setImage(image);

        return imageView;
    }

    public ImageView getImageView(GridPane imagePane){
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

    public Integer getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(Integer fileIndex) {
        this.fileIndex = fileIndex;
    }
}
