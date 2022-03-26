package design.Controller;

import design.Utils.StringUtils;
import design.pojo.ImageLabel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 23:30
 * Description: 负责图片层的信息维护以及创建
 */
public class ImageController {

    private ObservableList<ImageLabel> imageLabels;
    // 用于展示图片的组件
    private FlowPane imageLabelsPane;

    private String oldPath;

    private MenuController menuController;

    private ImageShowController imageShowController;

    private Integer imageCount;

    private BigDecimal imageTotalSize;

    private ArrayList<File> presentFileList;

    public ImageController(){
        /// 逻辑还需修改
        imageLabelsPane = new FlowPane(Orientation.HORIZONTAL);
        oldPath = "";
        imageLabels = FXCollections.observableArrayList();
    }
    public ImageController(MenuController menuController,ImageShowController imageShowController){
        this();
        this.menuController = menuController;
        this.imageShowController = imageShowController;
    }
    /**
     * 读取选中目录下的所有图片文件 并存放在数组中  同时获取图片数量和总大小
     * @param directoryFile 从TreeController中传入 选中目录的文件
     */
    public void createImageViews(File directoryFile){
        // 清空图片列表
        imageLabels.clear();
        imageCount = 0;
        long size = 0;
        // 排除目录相同的情况 和 文件为空的情况
        if(directoryFile == null || oldPath.equals(directoryFile.getPath())){
            return;
        }
        oldPath = directoryFile.getPath();
        File[] files = directoryFile.listFiles();
        if(files != null){
            presentFileList = new ArrayList<>();
            for (File file : files) {
                if(StringUtils.isImageFile(file)){
                    imageLabels.add(createImageLabel(file));
                    imageCount++;
                    size+=file.length();
                    presentFileList.add(file);
                }
            }
        }
        // 将图片总大小格式化为 MB
        imageTotalSize = BigDecimal.valueOf(size).divide(BigDecimal.valueOf(1024*1024),4,RoundingMode.HALF_EVEN);

        ///  逻辑还需修改
        imageLabelsPane = new FlowPane(Orientation.HORIZONTAL);
        imageLabelsPane.getChildren().addAll(imageLabels);
    }

    public FlowPane getImageViewsPane() {
        return imageLabelsPane;
    }

    /**
     * 创建一个装载图片的标签
     * @param file 图片文件
     * @return 返回图片标签
     */
    public ImageLabel createImageLabel(File file){
        Image image = new Image("file:" + file.getPath(),80,80,true,true);
        ImageView imageView = new ImageView(image);

        ImageLabel imageLabel = new ImageLabel();
        imageLabel.setText(file.getName());
        imageLabel.setWrapText(true);
        imageLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        imageLabel.setGraphic(imageView);
        imageLabel.setTextAlignment(TextAlignment.CENTER);
        imageLabel.setImageFileProperty(file);
        imageLabel.setContentDisplay(ContentDisplay.TOP);
        imageLabel.setPrefSize(120,120);
        imageLabel.setContextMenu(menuController.getImageControlMenu());


        imageLabel.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                System.out.println("左键");
                imageLabel.setScaleX(3);
                imageLabel.setScaleY(3);

            }
            if(event.getButton().equals(MouseButton.SECONDARY)){
                System.out.println("右键");
            }
            if(event.getClickCount()==2){
                imageShowController.createStage(presentFileList,imageLabel.getImageFileProperty());
            }
        });
        imageLabel.setOnMouseExited(event -> {
            imageLabel.setScaleX(1);
            imageLabel.setScaleY(1);
        });
        return imageLabel;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public BigDecimal getImageTotalSize() {
        return imageTotalSize;
    }
}
