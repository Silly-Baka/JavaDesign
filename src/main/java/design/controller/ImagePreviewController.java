package design.controller;

import design.Utils.AlertUtils;
import design.Utils.StringUtils;
import design.model.ImageLabel;
import design.viewController.ImagePreviewViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 23:30
 * Description: 负责图片层的信息维护
 */
public class ImagePreviewController {
    private FlowPane imageLabelsPane;

    private ObservableList<ImageLabel> imageLabels;

    private String oldPath;

    private ShowImageController showImageController;

    private Integer imageCount;

    private BigDecimal imageTotalSize;

    private ArrayList<File> presentFileList;

    private Stage primaryStage;

    private ImagePreviewViewController imagePreviewViewController;


    public ImagePreviewController(){
        oldPath = "";
        imageLabels = FXCollections.observableArrayList();
    }
    public ImagePreviewController(ShowImageController showImageController){
        this();
        this.showImageController = showImageController;
    }
    /**
     * 读取选中目录下的所有图片文件 并存放在数组中  同时获取图片数量和总大小
     * @param directoryFile 从TreeController中传入 选中目录的文件
     */
    long size;
    public void createImageViews(File directoryFile,FlowPane imageLabelsPane){
        this.imageLabelsPane = imageLabelsPane;
        // 清空图片列表
        imageLabels.clear();

        imageCount = 0;
        size = 0;
        // 排除目录相同的情况 和 文件为空的情况
        if(directoryFile == null || oldPath.equals(directoryFile.getPath())){
            return;
        }
        oldPath = directoryFile.getPath();
        File[] files = directoryFile.listFiles();
        if(files != null){
            presentFileList = new ArrayList<>();
            for (File file : files) {
                if(StringUtils.isImageFile(file.getName())){
                    imageLabels.add(createImageLabel(file));
                    imageCount++;
                    size+=file.length();
                    presentFileList.add(file);
                }
            }
        }
        // 将图片总大小格式化为 MB
        imageTotalSize = BigDecimal.valueOf(size).divide(BigDecimal.valueOf(1024*1024),4,RoundingMode.HALF_EVEN);

        imageLabelsPane.getChildren().remove(0, imageLabelsPane.getChildren().size());
        for(ImageLabel imagelabel : imageLabels){
            imageLabelsPane.getChildren().add(imagelabel);
        }
    }
    /**
     * 创建一个装载图片的标签并设置其属性
     * @param file 图片文件
     * @return 返回图片标签
     */
    public ImageLabel createImageLabel(File file){
        Image image = new Image("file:" + file.getPath(),120,80,true,true);
        ImageView imageView = new ImageView(image);

        ImageLabel imageLabel = new ImageLabel();
        imageLabel.setAlignment(Pos.CENTER);
        imageLabel.setText(file.getName());
        imageLabel.setWrapText(true);
        imageLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        imageLabel.setGraphic(imageView);
        imageLabel.setTextAlignment(TextAlignment.CENTER);
        imageLabel.setImageFileProperty(file);
        imageLabel.setContentDisplay(ContentDisplay.TOP);
        imageLabel.setPrefSize(160,140);
        imageLabel.setContextMenu(MenuController.getImageControlMenu(this,imagePreviewViewController));
        long length = file.length();
        double kbSize = BigDecimal.valueOf(length).divide(BigDecimal.valueOf(1024),3,RoundingMode.HALF_EVEN).doubleValue();
        imageLabel.setTooltip(new Tooltip("图片大小："+kbSize+"kb    "+
                                            "图片名字："+file.getName()));


        imageLabel.setOnMouseClicked(event -> {
//            if(event.getButton().equals(MouseButton.PRIMARY)){
//                System.out.println("左键");
//                imageLabel.setScaleX(3);
//                imageLabel.setScaleY(3);
//
//            }
            if(event.getButton().equals(MouseButton.SECONDARY)){
//                System.out.println("右键");
            }
            if(event.getClickCount()==2){
                showImageController.createStage(presentFileList,imageLabel.getImageFileProperty());
            }
        });
        imageLabel.addPictureNodeListener();
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

    public String getOldPath() {
        return oldPath;
    }

    /**
     * 粘贴功能的更新图片缩略图视图层
     * @param file 粘贴的新图片文件
     */
    public void refreshImageViews(File file) {
        imageLabels.add(createImageLabel(file));
        imageCount++;
        size+=file.length();
        imageTotalSize = BigDecimal.valueOf(size).divide(BigDecimal.valueOf(1024*1024),4,RoundingMode.HALF_EVEN);
        imageLabelsPane.getChildren().remove(0, imageLabelsPane.getChildren().size());
        for(ImageLabel imagelabel : imageLabels){
            imageLabelsPane.getChildren().add(imagelabel);
        }
    }

    /**
     * 重命名功能的更新缩略图视图层
     * @param oldeImageLabels 旧的图片数组
     * @param newImageFiles 重命名后的图片文件数组
     */
    public void refreshImageViews(ArrayList<ImageLabel> oldeImageLabels, List<File> newImageFiles) {
//        System.out.println(imageLabels.size());
        imageLabels.removeAll(oldeImageLabels);
//        System.out.println(imageLabels.size());
        imageLabelsPane.getChildren().remove(0, imageLabelsPane.getChildren().size());

        for (File newImageFile : newImageFiles) {
            imageLabels.add(createImageLabel(newImageFile));
        }
        imageLabelsPane.getChildren().addAll(imageLabels);
    }

    public void setSlideShowButton(Button slideShowButton) {
        slideShowButton.setOnAction(event -> {
            if(presentFileList!=null && presentFileList.size()>0){
                showImageController.createStage(presentFileList,presentFileList.get(0));
            }else {
                AlertUtils.showAlert(Alert.AlertType.WARNING,"幻灯片播放失败！请在有图片的目录重新点击","",primaryStage);
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setViewController(ImagePreviewViewController imagePreviewViewController){
        this.imagePreviewViewController = imagePreviewViewController;
    }
}
