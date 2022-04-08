package design.Controller;

import design.Utils.StringUtils;
import design.pojo.ImageLabel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 23:30
 * Description: 负责图片层的信息维护
 */
public class ImagePreviewController {
    private FlowPane imageLabelsPane;

    private TipsController tipsController;

    private ObservableList<ImageLabel> imageLabels;

    private String oldPath;

    private MenuController menuController;

    private ShowImageController showImageController;

    private Integer imageCount;

    private BigDecimal imageTotalSize;

    private ArrayList<File> presentFileList;


    public ImagePreviewController(){
        oldPath = "";
        imageLabels = FXCollections.observableArrayList();
    }
    public ImagePreviewController(MenuController menuController, ShowImageController showImageController, TipsController tipsController){
        this();
        this.menuController = menuController;
        this.showImageController = showImageController;
        this.tipsController = tipsController;
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

        imageLabelsPane.getChildren().remove(0, imageLabelsPane.getChildren().size());
        for(ImageLabel imagelabel : imageLabels){
            imageLabelsPane.getChildren().add(imagelabel);
        }
    }
    /**
     * 创建一个装载图片的标签
     * @param file 图片文件
     * @return 返回图片标签
     */
    public ImageLabel createImageLabel(File file){
        Image image = new Image("file:" + file.getPath(),80,80,true,true);
        ImageView imageView = new ImageView(image);

        ImageLabel imageLabel = new ImageLabel(tipsController);
        imageLabel.setText(file.getName());
        imageLabel.setWrapText(true);
        imageLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        imageLabel.setGraphic(imageView);
        imageLabel.setTextAlignment(TextAlignment.CENTER);
        imageLabel.setImageFileProperty(file);
        imageLabel.setContentDisplay(ContentDisplay.TOP);
        imageLabel.setPrefSize(120,120);
        imageLabel.setContextMenu(menuController.getImageControlMenu(this));


        imageLabel.setOnMouseClicked(event -> {
//            if(event.getButton().equals(MouseButton.PRIMARY)){
//                System.out.println("左键");
//                imageLabel.setScaleX(3);
//                imageLabel.setScaleY(3);
//
//            }
            if(event.getButton().equals(MouseButton.SECONDARY)){
                System.out.println("右键");
            }
            if(event.getClickCount()==2){
                showImageController.createStage(presentFileList,imageLabel.getImageFileProperty());
            }
        });
//        imageLabel.setOnMouseExited(event -> {
//            imageLabel.setScaleX(1);
//            imageLabel.setScaleY(1);
//        });

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
    public void refreshImageViews(ArrayList<ImageLabel> oldeImageLabels,ArrayList<File> newImageFiles) {
        System.out.println(imageLabels.size());
        imageLabels.removeAll(oldeImageLabels);
        System.out.println(imageLabels.size());
        imageLabelsPane.getChildren().remove(0, imageLabelsPane.getChildren().size());

        for (File newImageFile : newImageFiles) {
            imageLabels.add(createImageLabel(newImageFile));
        }
        imageLabelsPane.getChildren().addAll(imageLabels);
    }
}
